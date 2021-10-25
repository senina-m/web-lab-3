package ru.senina.itmo.web.web_lab_3.servlets;

import lombok.extern.java.Log;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;
import java.util.logging.Level;

/**
 * Redirect to checker if there is some new coordinates.
 * If there is no data in request and session redirect to starting plot-form page.
 */
@Log
@WebServlet("/controller")
public class ControllerServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.log(Level.FINE, "Start process get request in ControllerServlet");
        if (Optional.ofNullable(request.getParameter("session")).orElse("").equals("clear")) {
            log.log(Level.WARNING, "Redirected to clear session servlet!");
            getServletContext().getRequestDispatcher("/controller/sessionClear").forward(request, response);
        } else {
            if (request.getAttribute("coordinates") == null) {
                log.log(Level.WARNING, "Wrong arguments in controller servlet");
                System.out.println("Wrong arguments in controller servlet");
                System.out.println(request.getAttribute("coordinates"));
                getServletContext().getRequestDispatcher("/plot.jsp").forward(request, response);
            } else {
                log.log(Level.FINE, "Coordinates in application context are correct. Redirect to checker");
                getServletContext().getRequestDispatcher("/controller/check").forward(request, response);
            }
        }
    }
}


