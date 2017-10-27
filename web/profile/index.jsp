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
    boolean isSelf = user.name.equalsIgnoreCase(request.getParameter("name"));
    try {

        user = User.where("name", request.getParameter("name"));
    } catch (Exception e) {
        e.printStackTrace();
    }
%>
<div class="container" id="app">
    <div class="row">
        &nbsp;
    </div>
    <div class="row justify-content-center">
        <div class="col-lg-6 col-md-8 col-sm-12">
            <div class="card">
                <div class="card-body">
                   <div class="justify-content-center" style=" margin: 0 auto !important">
                       <img src="/public/download.jpeg" style="width: 100px !important;" class="rounded-circle float-right">
                       <h4 class="card-title"><%= user.name%></h4>
                       <h5 class="card-title"><%= user.email%></h5>
                   </div>
                    <button type="button" class="btn btn-primary">
                        Followers <span class="badge badge-light"><%= user.followers().size()%></span>
                    </button>
                    <button type="button" class="btn btn-primary">
                        Following <span class="badge badge-light"><%= user.following().size()%></span>
                    </button>

                    <% if (!isSelf) {
                        List<User> list = User.getLoggedInUser(session).following();
                        boolean following = false;
                        for (User u : list ) {
                            if (u.id == user.id) {
                                following = true;
                            }
                        }
                        if(following) {
                        %>
                    <hr>

                    <button class="btn btn-primary container-fluid" @click="follow(<%=user.id%>)"> Unfollow </button>

                    <%
                            } else {
                            %>
                    <hr>

                    <button class="btn btn-outline-primary container-fluid"  @click="follow(<%=user.id%>)"> Follow </button>

                    <%
                            }
                    }%>
                </div>
            </div>
        </div>
    </div>
    <br>
    <%
        ResultSet rs;

        try {
            %>
    <div class="row justify-content-center">
        <div class="col-lg-6 col-md-8 col-sm-12">
            <%
                    rs = Database.init().query("SELECT c.*, u.* from chirps c join users u on c.user_id = u.id where u.id =" + user.id + " order by c.created_at desc").fireSelect();

                    while(rs.next()) {
            %>
                    <div class="card">
                        <div class="card-body">

                            <h5 class="card-title">                     <img src="/public/download.jpeg" style="width: 35px!important;" class="rounded-circle">
                                @ <%= rs.getString("u.name")%></h5>
                            <p class="card-text font-weight-light"><%= rs.getString("c.data")%></p>
                            <div>
                                <div class="btn-group float-left" role="group" aria-label="Send Chirp">

                                    &nbsp;&nbsp;
                                </div>
                                <%--<button class="btn  rounded-circle" @click="like(chirp.id)" :class="chirp.liked ? 'bg-danger text-white' : 'btn-outline-danger'">&#9825;</button>--%>

                                <span class="float-right"> <%= rs.getString("c.created_at")%></span>

                            </div>
                        </div>
                    </div>

            <%
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            %>

        </div>
    </div>
</div>
<script src="/public/js/init.js"></script>
<script>
    var vue = new Vue({
        el : '#app',
       methods : {
           follow : function (id) {
               var vm = this;
               axios.get("/profile/follow.jsp?u=" + id)
                   .then( function (response) {
                       if (response.data.success) {
                           window.location.reload();
                       }
                   }).catch( function () {

               });
           },
       }
    });
</script>
</body>
</html>