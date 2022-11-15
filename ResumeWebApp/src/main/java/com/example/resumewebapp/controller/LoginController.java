package com.example.resumewebapp.controller;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.example.resumewebapp.util.ControllerUtil;
import dao.impl.UserDaoImpl;
import dao.inter.UserDaoInter;
import entity.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "LoginController", value = "/login")
public class LoginController extends HttpServlet {

    private UserDaoInter userDao = new UserDaoImpl();
    private BCrypt.Verifyer verifier = BCrypt.verifyer();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        request.getRequestDispatcher("login.jsp").forward(request, response);
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        try {
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            User user = userDao.findByEmail(email);

            if(user == null) throw new IllegalArgumentException("User does not exist.");

            BCrypt.Result rs = verifier.verify(password.toCharArray(), user.getPassword().toCharArray());
            if(!rs.verified) throw new IllegalArgumentException("Password is incorrect.");

            request.getSession().setAttribute("loggedInUser", user);
            response.sendRedirect("users");
        } catch (Exception e) {
            ControllerUtil.errorPage(response, e);
        }
    }
}