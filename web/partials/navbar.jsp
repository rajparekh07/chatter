<%@ page import="chatter.database.User" %>
<nav class="navbar navbar-expand-lg navbar-dark bg-primary justify-content-between">
    <div class="container">
        <a class="navbar-brand" href="/">Chatter</a>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNavDropdown" aria-controls="navbarNavDropdown" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarNavDropdown">
            <ul class="navbar-nav">
                <li class="nav-item">
                    <a class="nav-link" href="/">Home </a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="/profile/?name=<%
                            User user = User.getLoggedInUser(session);
                            if (user != null) {
                                out.println(user.name);
                            }
                        %>">Profile</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="/trends">Trends</a>
                </li>
                <li class="nav-item dropdown float-right">
                    <a class="nav-link dropdown-toggle" id="navbarDropdownMenuLink" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                        <%
                            if (user != null) {
                                out.println(" @" + user.name);
                            }
                        %>
                    </a>
                    <div class="dropdown-menu" aria-labelledby="navbarDropdownMenuLink">
                        <a class="dropdown-item" href="/auth/logout/">Logout</a>
                    </div>
                </li>
            </ul>
        </div>
    </div>
</nav>