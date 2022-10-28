package com.example.resumewebapp.filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletResponse;

@WebFilter(filterName = "JSPFileFilter", urlPatterns = {"*.jsp"})
public class JspFilter implements Filter {

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) {
        HttpServletResponse res = (HttpServletResponse) response;
        try {
            res.sendRedirect("error?msg=not found");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
