<%
    if(session.getAttribute("user") == null) {
        response.sendRedirect("/auth");
    }
%>