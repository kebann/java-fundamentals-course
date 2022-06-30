package com.bobocode;

import com.bobocode.session.SessionManager;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Optional;

@WebServlet("/v2/evening")
public class EveningServletV2 extends HttpServlet {

    private static final String NAME_QUERY_PARAM = "name";
    private static final String DEFAULT_NAME = "buddy";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try (var writer = resp.getWriter()) {
            var queryParamValue = Optional.ofNullable(req.getParameter(NAME_QUERY_PARAM));
            SessionManager sessionManager = (SessionManager) getServletContext().getAttribute("sessionManager");

            String name = queryParamValue
                    .or(() -> sessionManager.getAttribute(req, NAME_QUERY_PARAM))
                    .orElse(DEFAULT_NAME);

            if (queryParamValue.isPresent()) {
                sessionManager.setAttribute(req, resp, NAME_QUERY_PARAM, name);
            }

            writer.printf("Good evening, %s", name);
        }
    }
}
