<%@ page import="chatter.database.User" %>
<%@ page import="chatter.responses.JSONResponse" %>
<%@ page import="chatter.requests.JSONRequest" %>

<%@ page import="chatter.utils.Escaper" %>
<%@ page import="chatter.database.Database" %>
<%@ page import="chatter.utils.Timestamp" %>
<%@ page import="java.sql.ResultSet" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<%
    int chirp_id = Integer.parseInt(request.getParameter("c"));
    User user = User.getLoggedInUser(session);


    String jsonResponse = "";
    try {
        Database d = null;
        ResultSet rs = Database.init().query("SELECT id FROM likes where user_id = " + user.id + " and chirp_id = " + chirp_id).fireSelect();

        if (rs.first()) {
            d = Database.init().query("DELETE FROM likes where id = " + rs.getInt("id"));
        } else {
            d = Database.init().query("INSERT INTO likes(user_id, chirp_id, created_at) VALUES ('"+ user.id +"', '"+ chirp_id +"','"+ Timestamp.now() +"')");
        }

        if(d.fireUpdate() > 0) {
            try {
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