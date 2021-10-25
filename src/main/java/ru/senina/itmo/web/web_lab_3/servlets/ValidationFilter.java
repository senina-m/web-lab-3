package ru.senina.itmo.web.web_lab_3.servlets;

import lombok.extern.java.Log;
import ru.senina.itmo.web.web_lab_3.entities.Coordinates;
import ru.senina.itmo.web.web_lab_3.exceptions.CoordinatesOutOfBoundsException;
import ru.senina.itmo.web.web_lab_3.validators.CoordinatesValidator;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Enumeration;
import java.util.logging.Level;

/**
 * Takes all requests.
 * If there is some new coordinates -- validate them.
 */
@Log
@WebFilter("/*")
@Dependent
public class ValidationFilter implements Filter {
    private @Inject
    CoordinatesValidator validator;

    @Override
    public void init(FilterConfig fConfig) {
        log.log(Level.FINE, "validation filter started");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        log.log(Level.FINE, "Got a new " + request.toString());
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String path = httpRequest.getRequestURI();

        if (path.endsWith(".css") || path.endsWith(".js") || path.endsWith(".png")) {
            chain.doFilter(request, response);
            return;
        }

        if ("POST".equals(httpRequest.getMethod())) {
            log.log(Level.FINE, "Post request came to Validation Filter");
        } else {
            printAttributesNames(request);
            try {
                double x = Double.parseDouble(request.getParameter("x"));
                double y = Double.parseDouble(request.getParameter("y"));
                double r = Double.parseDouble(request.getParameter("r"));
                log.log(Level.FINE, "Coordinates values are x: " + x + ", y: " + y + ", r: " + r);

                Coordinates coordinates = new Coordinates(x, y, r);
                validator.validate(coordinates);
                request.setAttribute("coordinates", coordinates);
                log.log(Level.WARNING, "Coordinates from session " + request.getAttribute("coordinates").toString());
            } catch (NullPointerException | NumberFormatException | CoordinatesOutOfBoundsException e) {
                log.log(Level.WARNING, "Error detected by validation filter. Message:" + e.getMessage());
            }
        }
        chain.doFilter(request, response);
    }

    private void printAttributesNames(ServletRequest request) {
        System.out.print("Parameters: ");
        Enumeration<String> names = request.getParameterNames();
        while (names.hasMoreElements()) {
            String parameterName = names.nextElement();
            System.out.print("'" + parameterName + "'='" + request.getParameter(parameterName) + "'; ");
        }
        System.out.println();
    }
}