<%@ page import="chatter.database.User" %>
<%@ page import="chatter.responses.JSONResponse" %>
<%@ page import="chatter.requests.JSONRequest" %>

<%@ page import="chatter.utils.Escaper" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<%
    JSONRequest jsonRequest = JSONRequest.init(request);

    String name = Escaper.escapeString(jsonRequest.getParameter("username"));
    String email = Escaper.escapeString(jsonRequest.getParameter("email"));
    String password = Escaper.escapeString(jsonRequest.getParameter("password"));
    User user = new User(name, email, password);
    String jsonResponse = "";
    try {
        if(user.save() > 0) {
            try {
                session.setAttribute("user", (User.where("email",email)));
                jsonResponse = JSONResponse.init().success().make();
            } catch (Exception e) {
                jsonResponse = JSONResponse.init().error(e.getMessage()).make();
            }
        } else {
            jsonResponse = JSONResponse.init().failed().make();
        }
    } catch (Exception e) {
        e.printStackTrace();
        jsonResponse = JSONResponse.init().error(e.getMessage()).make();

    }

    response.setContentType("application/json");
    response.getWriter().write(jsonResponse);
%>