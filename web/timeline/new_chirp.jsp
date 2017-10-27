<%@ page import="chatter.responses.JSONResponse" %>
<%@ page import="chatter.requests.JSONRequest" %>

<%@ page import="chatter.utils.Escaper" %>
<%@ page import="chatter.database.Chirp" %>
<%@ page import="java.util.List" %>
<%@ page import="chatter.utils.StringExtractor" %>
<%@ page import="chatter.database.Mention" %>
<%@ page import="chatter.database.Trend" %>
<%@ page import="chatter.database.User" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<%
    JSONRequest jsonRequest = JSONRequest.init(request);

    String chirp = Escaper.escapeString(jsonRequest.getParameter("chirp"));
    int user_id = ((Long)jsonRequest.getObjectParameter("user_id")).intValue();
    Chirp newChirp = new Chirp(chirp, user_id);


    String jsonResponse = "";
    try {
        int res = newChirp.save();
        if(res > 0) {
            try {
                List<String> mentions = StringExtractor.extract("@", newChirp.data);
                List<String> trends = StringExtractor.extract("#", newChirp.data);
                for (String mention:
                     mentions) {
                    try {
                        User u = User.where("name", mention.substring(1));
                        Mention m = new Mention(res, u.id);
                        m.save();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                for (String trend :
                        trends) {
                    try {
                        Trend m = new Trend(res, trend);
                        m.save();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
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