<%
    if(session.getAttribute("user") != null) {
        response.sendRedirect("/");
    } else {
        Cookie cookies[] = request.getCookies();
        for (Cookie cookie: cookies) {
            System.out.println(cookie.getName());
            if (cookie.getName().equals("user")) {
                User user = null;
                System.out.println("sdasdadads");
                try {
                    user = User.where("email", cookie.getValue());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                session.setAttribute("user", user);
                response.sendRedirect("/");
            }
        }
    }
%>