<%@ include file="middleware/redirectIfNotLoggedIn.jsp" %>

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

<div class="container-fluid" id="app">
    <div class="row">
        &nbsp;
    </div>

    <div class="row justify-content-center">
        <div class="col-lg-6 col-md-8 col-sm-12">
            <div class="card">
                <div class="card-body">
                    <form>
                        <div class="form-group">
                            <textarea class="form-control" id="exampleFormControlTextarea1" name="chirp" v-model="new_chirp" v-validate="'required|max:140'" rows="3" placeholder="What is Happening?"></textarea>
                        </div>
                        <p class="text-danger"> {{ errors.first("chirp") }} </p>

                        <div class="btn-group float-right" role="group" aria-label="Send Chirp">
                            <button type="button" class="btn btn-secondary" @click="reset">Reset</button>
                            <button type="button" class="btn btn-primary" @click="send">Send</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
    <div class="row">
        &nbsp;
    </div>

    <div class="row justify-content-center">
        <div class="col-lg-6 col-md-8 col-sm-12">
            <div class="card"
                v-for="(chirp, index) in chirps"
            >
                <div class="card-body">

                    <h5 class="card-title">                    <img src="/public/download.jpeg" style="width: 35px!important;" class="rounded-circle">
                        @ {{ chirp.user.name }}</h5>
                    <p class="card-text font-weight-light">{{ chirp.data }}</p>
                    <div>
                        <div class="btn-group float-left" role="group" aria-label="Send Chirp">

                            &nbsp;&nbsp;
                        </div>
                        <button class="btn  rounded-circle" @click="like(chirp.id)" :class="chirp.liked ? 'bg-danger text-white' : 'btn-outline-danger'">&#9825;</button>

                        <span class="float-right"> {{ chirp.created_at }}</span>

                    </div>
                </div>
            </div>
        </div>
    </div>
    <br>
    <div class="row justify-content-center">
        <div class="btn btn btn-light" @click="fetchChirps((count+=10))"> Load More </div>
    </div>
</div>

<script src="/public/js/init.js"></script>

<script>
    var app = new Vue({
        el : '#app',
        data : {
            chirps : [],
            new_chirp : '',
            count : 10
        },
        mounted : function () {
            this.fetchChirps(this.count);
        },
        methods : {
            send : function () {
                var data = {
                    chirp : this.new_chirp,
                    user_id : <% if (user != null) out.print(user.id); %>
                };
                var url = "/timeline/new_chirp.jsp";
                var vm = this;
                axios.post(url, data).then(function (response) {
                   if (response.data.success) {
                       vm.fetchChirps();
                       window.success("Sent!");
                       vm.reset();
                   }
                }).catch(function () {

                });
            },
            reset : function () {
                this.new_chirp = "";
            },
            fetchChirps : function (count) {
                var vm = this;
                axios.get("/timeline/get_chirps.jsp?c=" + count)
                    .then(function (response) {
                        vm.chirps = response.data;
                    }).catch(function () {

                })
            },
            like : function (id) {
                var vm = this;
                axios.get("/timeline/like_chirp.jsp?c=" + id)
                    .then( function (response) {
                        if (response.data.success) {
                            vm.fetchChirps(vm.count);
                        }
                    }).catch( function () {

                });
            }
        }
    });
</script>
</body>
</html>