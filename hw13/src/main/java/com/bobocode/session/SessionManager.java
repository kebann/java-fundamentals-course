package com.bobocode.session;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.time.Instant;
import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.*;

public class SessionManager {
    private final String CUSTOM_SESSION_ID = "CUSTOM_SESSION_ID";
    private final ConcurrentMap<String, CustomSession> sessionMap;
    private final long DEFAULT_SESSION_TTL = 30;

    public SessionManager() {
        this.sessionMap = new ConcurrentHashMap<>();
        scheduleSessionCleanUp();
    }

    /**
     * Find an attribute value in the session if such session and attribute name exist.
     *
     * @param req  incoming HTTP request
     * @param name session attribute name to look up
     * @return an {@code Optional} containing the value of for the attribute or empty {@code Optional
     */
    public <T> Optional<T> getAttribute(HttpServletRequest req, String name) {
        return getSessionId(req)
                .map(sessionMap::get)
                .map(session -> (T) session.get(name));
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

    public void setAttribute(HttpServletRequest req,
                             HttpServletResponse resp,
                             String name, String val) {
        String sessionId = getSessionId(req).orElse(generateSessionId());

        CustomSession session = sessionMap.computeIfAbsent(sessionId, this::createNewSession);
        session.put(name, val);

        addSessionCookie(resp, sessionId);
    }

    private void addSessionCookie(HttpServletResponse resp, String sessionId) {
        Cookie cookie = new Cookie(CUSTOM_SESSION_ID, sessionId);
        cookie.setMaxAge((int) TimeUnit.SECONDS.toMillis(DEFAULT_SESSION_TTL));
        resp.addCookie(cookie);
    }

    private CustomSession createNewSession(String id) {
        return new CustomSession()
                .setId(id)
                .setCreationTimestamp(Instant.now().toEpochMilli())
                .setTtl(TimeUnit.SECONDS.toMillis(DEFAULT_SESSION_TTL));
    }

    private String generateSessionId() {
        return UUID.randomUUID().toString();
    }

    private void scheduleSessionCleanUp() {
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.scheduleAtFixedRate(this::removeExpiredSessions,
                DEFAULT_SESSION_TTL,
                DEFAULT_SESSION_TTL,
                TimeUnit.SECONDS);
    }

    private void removeExpiredSessions() {
        sessionMap.forEach((id, session) -> {
            boolean isExpired = Instant.now().toEpochMilli() - session.getTtl() < session.getCreationTimestamp();
            if (isExpired) {
                sessionMap.remove(id);
            }
        });
    }
}
