package ru.job4j.dream.servlet;

import ru.job4j.dream.PsqlStore;
import ru.job4j.dream.Store;
import ru.job4j.dream.User;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

public class AuthServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter pw = resp.getWriter();
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        pw.println(email);
        pw.println(password);
        User userSearchedByEmail = PsqlStore.instOf().findByEmail(email);
        if (userSearchedByEmail != null) {
            if (userSearchedByEmail.getPassword().equals(password)) {
                HttpSession sc = req.getSession();
                User user = new User();
                user.setEmail(email);
                user.setPassword(password);
                sc.setAttribute("user", user);
                resp.sendRedirect(req.getContextPath() + "/posts.do");
            } else {
                req.setAttribute("error", "Не верный email или пароль");
                req.getRequestDispatcher("login.jsp").forward(req, resp);
            }
        } else {
            pw.print("User is null");
        }
    }
}
