package ru.job4j.dream.servlet;

import ru.job4j.dream.Post;
import ru.job4j.dream.Store;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.concurrent.atomic.AtomicInteger;

public class PostServlet extends HttpServlet {
    private static AtomicInteger postId = new AtomicInteger(4);
    private static Store store = Store.instOf();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        save(new Post(0, req.getParameter("name"), "empty description", LocalDate.now()));
        resp.sendRedirect(req.getContextPath() + "/posts.jsp");
    }

    private void save(Post post) {
        post.setId(postId.incrementAndGet());
        store.put(post);
    }
}
