package com.example.resumewebapp.controller;

import dao.impl.UserDaoImpl;
import dao.inter.UserDaoInter;
import entity.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "UsersController", value = "/users")
public class UsersController extends HttpServlet {
    private UserDaoInter userDao = new UserDaoImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        UserDaoInter userDao = new UserDaoImpl();

        String name = request.getParameter("name");
        String surname = request.getParameter("surname");
        String nationalityIdStr = request.getParameter("nid");
        Integer nationalityId = null;
        if(nationalityIdStr != null && !nationalityIdStr.trim().isEmpty())
            nationalityId = Integer.parseInt(nationalityIdStr);
        List<User> users = userDao.getAllUsers(name, surname, nationalityId);

        request.setAttribute("users", users);
        request.getRequestDispatcher("users.jsp").forward(request, response);
    }
}