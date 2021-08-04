package ru.job4j.dream.servlet;

import ru.job4j.dream.Post;
import ru.job4j.dream.Store;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;

public class PostServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("UTF-8");
        Store.instOf().save(new Post(Integer.parseInt(req.getParameter("id")), req.getParameter("name"),
                "empty description", LocalDate.now()));
        resp.sendRedirect(req.getContextPath() + "/posts.jsp");
    }
}
