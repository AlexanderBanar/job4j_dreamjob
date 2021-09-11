package ru.job4j.dream.servlet;

import ru.job4j.dream.PsqlStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class TodayCandidatesServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("candidates", PsqlStore.instOf().findTodayCandidates());
        req.getRequestDispatcher("/candidate/todayCandidates.jsp").forward(req, resp);
    }
}
