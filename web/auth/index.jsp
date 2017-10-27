<%@include file="/middleware/loginIfRemember.jsp"%>
<%@ include file="/middleware/redirectIfLoggedIn.jsp"%>

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

<div class="container">

    <div class="row">
        &nbsp;
    </div>

    <div class="container-fluid">
        <div class="jumbotron">
            <div class="container">
                <h1 class="display-3">Welcome to Chatter.</h1>
                <p class="lead"> Find out what is happening in this super fast world, instantly! </p>
            </div>
        </div>
    </div>

    <div class="row">

    </div>

  <div class="row" id="app">

      <div class="col-md-4 offset-1">
          <div class="card">
              <div class="card-body">
                  <form class="form-signin">
                      <h2 class="form-signin-heading">Register</h2>
                      <div class="p-sm-1">
                          <label class="sr-only" for="rg_username">Username</label>
                          <div class="input-group mb-2 mb-sm-0">
                              <div class="input-group-addon">@</div>
                              <input type="text" class="form-control" id="rg_username" v-model="rg_username" name="username" placeholder="Username">
                          </div>
                      </div>
                      <div class="p-sm-1">
                          <label class="sr-only" for="rg_email">Email</label>
                          <div class="input-group mb-2 mb-sm-0">
                              <input type="text" class="form-control" id="rg_email" v-model="rg_email" name="email" placeholder="Email">
                          </div>
                      </div>
                      <div class="p-sm-1">
                          <label class="sr-only" for="rg_password">Password</label>
                          <div class="input-group mb-2 mb-sm-0">
                              <input type="password" class="form-control" id="rg_password" v-model="rg_password" name="password" placeholder="Password" required>
                          </div>
                      </div>
                  </form>
                  <button class="btn btn-primary bg-primary btn-block" @click="authenticate(registerData)">Register</button>

              </div>
          </div>
      </div>


      <div class="col-md-4 offset-2">
      <div class="card">
          <div class="card-body">
              <form class="form-signin">
                  <h2 class="form-signin-heading">Login</h2>
                  <div class="p-sm-1">
                      <label class="sr-only" for="inlineFormInputGroup">Username</label>
                      <div class="input-group mb-2 mb-sm-0">
                          <div class="input-group-addon">@</div>
                          <input type="text" class="form-control" name="username" v-model="username" id="inlineFormInputGroup" placeholder="Username">
                      </div>
                  </div>
                  <div class="p-sm-1">
                      <label class="sr-only" for="inputPassword">Password</label>
                      <div class="input-group mb-2 mb-sm-0">
                          <input type="password" class="form-control" name="password" v-model="password" id="inputPassword" placeholder="Password" required>
                      </div>
                  </div>
                  <div class="checkbox">
                      <label>
                          <input class="text-light" name="remember" type="checkbox" v-model="remember" value="remember-me"> Remember me
                      </label>
                  </div>
              </form>
              <button class="btn btn-primary bg-warning text-light btn-block " @click="authenticate(loginData)">Sign in</button>

          </div>
      </div>
    </div>

  </div>

</div> <!-- /container -->

<script src="../public/js/init.js"></script>

<script>
    var app = new Vue({
        el: '#app',
        data : {
            username : '',
            password : '',
            remember : '',
            rg_email : '',
            rg_username : '',
            rg_password : ''
        },
        computed : {
            loginData : function() {
                return {
                    url : '/auth/login.jsp',
                    payload : {
                        username : this.username,
                        password : this.password,
                        remember : this.remember
                    }
                }
            },
            registerData : function() {
                return {
                    url : '/auth/register.jsp',
                    payload : {
                        email : this.rg_email,
                        username: this.rg_username,
                        password: this.rg_password
                    }
                }
            }
        },
        methods : {
            authenticate : function(data) {
                if(this.errors.any()) return;
                var payload = data.payload;
                var url = data.url;
                axios.post(url, payload)
                    .then((response) => {
                        if(response.data.success) {
                            window.success("Success!");
                            setTimeout(function () {
                                window.location.href = '/';
                            },300);
                        } else {
                            window.error(response.data.error);
                        }
                    }).catch(() => {
                    window.error("Failed!");
                });
            }
        }
    });
</script>
</body>
</html>
