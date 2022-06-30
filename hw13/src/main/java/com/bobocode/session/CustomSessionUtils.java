package com.bobocode.session;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.experimental.UtilityClass;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@UtilityClass
public class CustomSessionUtils {

    private final String CUSTOM_SESSION_ID = "CUSTOM_SESSION_ID";
    private final ConcurrentMap<String, Map<String, String>> SESSION_MAP = new ConcurrentHashMap<>();

    /**
     * Find an attribute value in the session if such session and attribute name exist.
     *
     * @param req  incoming HTTP request
     * @param name session attribute name to look up
     * @return an {@code Optional} containing the value of for the attribute or empty {@code Optional
     */
    public Optional<String> getAttribute(HttpServletRequest req, String name) {
        return getSessionId(req)
                .map(SESSION_MAP::get)
                .map(attributes -> attributes.get(name));
    }

    private Optional<String> getSessionId(HttpServletRequest req) {
        return findCookieByName(req.getCookies(), CUSTOM_SESSION_ID)
                .map(Cookie::getValue);
    }

    private Optional<Cookie> findCookieByName(Cookie[] cookies, String cookieName) {
        if (cookies == null) {
            return Optional.empty();
        }

        return Arrays.stream(cookies)
                .filter(c -> c.getName().equalsIgnoreCase(cookieName))
                .findAny();
    }

    /**
     * Set a new or update an existing attribute in the existing session using provided name and value.
     * If no session exists, a new session is created with given attribute
     * and new {@value #CUSTOM_SESSION_ID} value is set as a cookie in the response
     *
     * @param req  current request
     * @param resp current response
     * @param name attribute name
     * @param val  attribute value
     */
    public void setAttribute(HttpServletRequest req,
                             HttpServletResponse resp,
                             String name, String val) {
        String sessionId = getSessionId(req).orElseGet(() -> UUID.randomUUID().toString());
        SESSION_MAP.computeIfAbsent(sessionId, id -> new HashMap<>()).put(name, val);

        resp.addCookie(new Cookie(CUSTOM_SESSION_ID, sessionId));
    }
}
