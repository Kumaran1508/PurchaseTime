<%@ page import="com.vk.purchasetime.services.LanguageSupportService" %>
<!DOCTYPE html>
<%
    String lang = (String) request.getSession().getAttribute("lang");
%>
<html lang="en">
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
    <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
    <link rel="stylesheet" href="signin.css">
    <link rel="canonical" href="https://getbootstrap.com/docs/5.1/examples/sign-in/">
    <link rel="icon" href="\assets\imgs\logo-light.png">

    <meta charset="UTF-8">
    <title><% out.println(LanguageSupportService.get("signup",lang)); %></title>
    <style>
        .bd-placeholder-img {
            font-size: 1.125rem;
            text-anchor: middle;
            -webkit-user-select: none;
            -moz-user-select: none;
            user-select: none;
        }

        @media (min-width: 768px) {
            .bd-placeholder-img-lg {
                font-size: 3.5rem;
            }
        }
    </style>
</head>
<body class="container-fluid">
<!--  Navbar  -->
<div class="container-fluid text-center mb-2">
    <header class="d-flex flex-wrap align-items-center justify-content-center justify-content-md-between py-3 mb-4 border-bottom">
        <div class="col-12 d-flex mb-1 mb-md-0 text-dark text-decoration-none justify-content-center align-items-center">
            <img src="\assets\imgs\logo.jpg" class="img-fluid rounded" alt="logo" width="50px" height="50px">
            <h3 class="text-success align-self-center"><% out.println(LanguageSupportService.get("apptitle",lang)); %></h3>
        </div>

    </header>
</div>

<!--  Form  -->
<main class="form-signin text-center">
    <form action="register" method="post">
        <h1 class="h3 mb-3 fw-normal"><% out.println(LanguageSupportService.get("createacc",lang)); %></h1>

        <div class="form-floating m-1">
            <input name="username" type="text" class="form-control" id="floatingusername" placeholder="<% out.println(LanguageSupportService.get("username",lang)); %>" minlength="5" maxlength="15" required>
            <label for="floatingusername"><% out.println(LanguageSupportService.get("username",lang)); %></label>
        </div>
        <div class="form-floating m-1">
            <input name="email" type="email" class="form-control" id="floatingemail" placeholder="<% out.println(LanguageSupportService.get("email",lang)); %>" required>
            <label for="floatingemail"><% out.println(LanguageSupportService.get("emailadd",lang)); %></label>
        </div>
        <div class="form-floating m-1">
            <input name="phoneno" type="number" class="form-control" id="floatingphno" placeholder="<% out.println(LanguageSupportService.get("phone",lang)); %>" min="1000000000" max="9999999999" required>
            <label for="floatingphno"><% out.println(LanguageSupportService.get("phone",lang)); %></label>
        </div>
        <div class="form-floating m-1">
            <input name='password' type="password" class="form-control" id="floatingPassword" placeholder="<% out.println(LanguageSupportService.get("password",lang)); %>" minlength="8" maxlength="16" pattern="(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[^a-zA-Z0-9]).{8,}" required>
            <label for="floatingPassword"><% out.println(LanguageSupportService.get("password",lang)); %></label>
        </div>

        <button class="w-100 btn btn-lg btn-success" type="submit"><% out.println(LanguageSupportService.get("signup",lang)); %></button>

        <div class="d-flex p-3 justify-content-between align-items-center">
            <a href="/forgotpassword" class="text-body"><% out.println(LanguageSupportService.get("forgotpassword",lang)); %></a>
        </div>

    </form>
</main>
<div class="footer bg-success">
    <p class="text-light text-center"><% out.println(LanguageSupportService.get("footertext",lang)); %></p>
</div>


</body>
</html>