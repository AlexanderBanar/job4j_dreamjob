package ru.job4j.dream.servlet;

import ru.job4j.dream.Candidate;
import ru.job4j.dream.Store;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

public class CandidateServlet extends HttpServlet {
    private static final AtomicInteger POST_ID = new AtomicInteger(6);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("UTF-8");
        save(new Candidate(0, req.getParameter("name")));
        resp.sendRedirect(req.getContextPath() + "/candidate/candidates.jsp");
    }

    private void save(Candidate candidate) {
        candidate.setId(POST_ID.incrementAndGet());
        Store.instOf().put(candidate);
    }
}
