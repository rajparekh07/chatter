<%@ page import="chatter.utils.Escaper" %>
<%@ page import="chatter.database.User" %>
<%@ page import="chatter.responses.JSONResponse" %>
<%@ page import="chatter.requests.JSONRequest" %>
<%@ page import="chatter.utils.Hasher" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    String jsonResponse = "";


    JSONRequest jsonRequest = JSONRequest.init(request);
    String username = Escaper.escapeString(jsonRequest.getParameter("username"));

    String password = Escaper.escapeString(jsonRequest.getParameter("password"));
    boolean remember;
    try{
        remember = (Boolean) (jsonRequest.getObjectParameter("remember"));

    } catch (Exception e) {
        remember = false;
    }
    try {

        if(User.attemptLogin(username, password)) {
            User user = User.where("name",username);
            session.setAttribute("user", user);

            if (remember) {
                Cookie cookie = new Cookie("user", user.email);
                cookie.setDomain("abc");
                cookie.setMaxAge(60*60*24*30);
                response.addCookie(cookie);
            }
            jsonResponse = JSONResponse.init().success().make();
        } else {
            jsonResponse = JSONResponse.init().error("Authentication Failed").make();
        }
    } catch (Exception e) {
        jsonResponse = JSONResponse.init().error(e.getMessage()).make();
    }
    response.setContentType("application/json");
    response.getWriter().write(jsonResponse);
%>