<%
    if(session.getAttribute("user") != null) {
        Cookie cookies[] = request.getCookies();
        for (Cookie cookie: cookies) {
            if (cookie.getName().equals("user")) {
                cookie.setMaxAge(0);
                cookie.setDomain("abc");
                response.addCookie(cookie);
            }
        }
        session.removeAttribute("user");
    }
    response.sendRedirect("/auth");
%>