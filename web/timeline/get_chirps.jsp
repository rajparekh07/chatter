<%@ page import="chatter.responses.JSONResponse" %>
<%@ page import="chatter.requests.JSONRequest" %>

<%@ page import="chatter.utils.Escaper" %>
<%@ page import="chatter.database.Chirp" %>
<%@ page import="java.util.List" %>
<%@ page import="chatter.database.User" %>
<%@ page import="org.json.simple.JSONArray" %>
<%@ page import="org.json.simple.JSONObject" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="java.sql.ResultSet" %>
<%@ page import="chatter.database.Database" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<%
    User user = User.getLoggedInUser(session);
    int count = Integer.parseInt(request.getParameter("c"));
    if (user != null) {
        try {
            List<Chirp> chirps = Chirp.getTimeline(user.id, count);
            JSONArray array = new JSONArray();

                for (Chirp chirp : chirps) {
                    JSONObject jsonObject = new JSONObject();

                    jsonObject.put("id", chirp.id);
                    jsonObject.put("data", chirp.data);
                    User chirpUser = User.findUser(chirp.user_id);
                    JSONObject userObject = new JSONObject();
                    userObject.put("id", chirpUser.id);
                    userObject.put("name", chirpUser.name);
                    userObject.put("email", chirpUser.email);
                    jsonObject.put("user", userObject);
                    jsonObject.put("created_at", chirp.timestamp);
                    ResultSet rs = Database.init().query("SELECT id from likes where chirp_id = " + chirp.id + " and user_id = " + user.id).fireSelect();
                    boolean liked = rs.first();
                    jsonObject.put("liked", liked);
                    array.add(jsonObject);
                }

            response.setContentType("application/json");
            response.getWriter().write(array.toJSONString());

        } catch (Exception e) {
            e.printStackTrace();
        }

    } else {
        response.setContentType("application/json");
        response.getWriter().write("[]");
    }
%>