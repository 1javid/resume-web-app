package com.example.resumewebapp.controller;

import java.io.*;

import dao.impl.UserDaoImpl;
import dao.inter.UserDaoInter;
import entity.User;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet(name = "UserDetailController", value = "/userdetail")
public class UserDetailController extends HttpServlet {
    private UserDaoInter userDao = new UserDaoImpl();
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        int id = Integer.valueOf(request.getParameter("id"));
        String action = request.getParameter("action");
        if(action.equals("update")) {

            String name = request.getParameter("name");
            String surname = request.getParameter("surname");

            User user = userDao.getById(id);
            user.setName(name);
            user.setSurname(surname);
            userDao.updateUser(user);
        } else if(action.equals("delete")) {
            userDao.deleteUser(id);
        }
        response.sendRedirect("users");
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            String userStr = request.getParameter("id");
            if (userStr == null || userStr.trim().isEmpty()) {
                throw new IllegalArgumentException("ID is not specified.");
            }
            Integer userId = Integer.parseInt(userStr);
            UserDaoInter userDao = new UserDaoImpl();
            User u = userDao.getById(userId);
            if (u == null) {
                throw new IllegalArgumentException("User with this ID is not found.");
            }

            System.out.println(u.getName());
            request.setAttribute("user", u);
            request.getRequestDispatcher("userdetail.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("error?msg=" + e.getMessage());
        }
    }
}