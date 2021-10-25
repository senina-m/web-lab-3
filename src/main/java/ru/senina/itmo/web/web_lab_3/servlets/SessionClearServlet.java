package ru.senina.itmo.web.web_lab_3.servlets;

import lombok.extern.java.Log;
import ru.senina.itmo.web.web_lab_3.dao.AttemptsList;

import javax.inject.Inject;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.logging.Level;

@Log
@WebServlet("/controller/sessionClear")
public class SessionClearServlet extends HttpServlet {
    private @Inject
    AttemptsList attemptsList;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        processRequest(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) {
        log.log(Level.WARNING, "Got request to session clear area!");
        try {
            attemptsList.clearList();
            response.setStatus(200);
        } catch (Exception e) {
            log.log(Level.WARNING,"Something wrong in session clear servlet");
        }
    }
}
