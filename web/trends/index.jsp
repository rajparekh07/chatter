<%@ page import="java.sql.ResultSet" %>
<%@ page import="chatter.database.Database" %>
<%@ page import="java.util.List" %>
<%@ include file="/middleware/redirectIfNotLoggedIn.jsp" %>

<!doctype html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <title>Chatter</title>


    <!-- Bootstrap core CSS -->
    <link href="../public/css/init.css" rel="stylesheet">

</head>

<body class="bg-dark">

<%@include file="/partials/navbar.jsp"%>

<%
    try {
        ResultSet trends = Database.init().query("SELECT data, count('data') as count FROM trends group by data order by count desc LIMIT 10").fireSelect();
        ResultSet mentions = Database.init().query("SELECT user_id, count('user_id') as count FROM mentions group by user_id order by count desc LIMIT 10").fireSelect();

%>

<div class="container" id="app">
    <div class="row">
        &nbsp;
    </div>
    <div class="row justify-content-center">
        <div class="col-lg-6 col-md-8 col-sm-12">
            <div class="card">
                <div class="card-body">
                    <h4 class="card-title">
                        TOP 10 Trends on Chatter!
                    </h4>
                    <ul>
                        <%
                            while (trends.next()) {
                        %>
                        <li><%=trends.getString("data")%></li>
                        <%
                            }
                        %>
                    </ul>
                </div>
            </div>
        </div>
    </div>
    <div class="row">
        &nbsp;
    </div>
    <div class="row justify-content-center">
        <div class="col-lg-6 col-md-8 col-sm-12">
            <div class="card">
                <div class="card-body">
                    <h4 class="card-title">
                        TOP 10 People on Chatter!
                    </h4>
                    <ul>
                        <%
                            while (mentions.next()) {
                                User u = User.findUser(mentions.getInt("user_id"));
                        %>
                        <li><a href="/profile/?name=<%= u.name%>">@<%= u.name%></a></li>
                        <%
                            }
                        %>
                    </ul>
                </div>
            </div>
        </div>
    </div>
<%

    } catch (Exception e) {
        e.printStackTrace();
    }
%>


</body>
</html>