package ru.job4j.dream.servlet;

import ru.job4j.dream.Candidate;
import ru.job4j.dream.City;
import ru.job4j.dream.PsqlStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class CandidateServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("UTF-8");
        PsqlStore.instOf().saveCan(new Candidate(Integer.parseInt(req.getParameter("id")), req.getParameter("name"),
                parseCity(req.getParameter("chosenCity"))));
        resp.sendRedirect(req.getContextPath() + "/candidate/candidates.do");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("candidates", PsqlStore.instOf().findAllCandidates());
        req.getRequestDispatcher("candidates.jsp").forward(req, resp);
    }

    private int parseCity(String city) {
        int result = 1;
        List<City> cities = (List<City>) PsqlStore.instOf().findAllCities();
        for (int i = 0; i < cities.size(); i++) {
            if (city.equals(cities.get(i).getName())) {
                result = cities.get(i).getId();
            }
        }
        return result;
    }
}
