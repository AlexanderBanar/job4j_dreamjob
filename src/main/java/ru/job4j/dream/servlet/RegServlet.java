package ru.job4j.dream.servlet;

import ru.job4j.dream.PsqlStore;
import ru.job4j.dream.User;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RegServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("UTF-8");
        User user = new User();
        if (PsqlStore.instOf().findByEmail(req.getParameter("email")) != null) {
            req.setAttribute("registration", user);
        } else {
            user.setEmail(req.getParameter("email"));
            user.setPassword(req.getParameter("password"));
            PsqlStore.instOf().saveUser(user);
        }
        resp.sendRedirect(req.getContextPath() + "/auth.do");
    }
}
