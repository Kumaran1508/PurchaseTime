<!DOCTYPE html>
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
    <title>Purchase Time - Sign Up</title>
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
            <h3 class="text-success align-self-center">Purhcase Time</h3>
        </div>

    </header>
</div>

<!--  Form  -->
<main class="form-signin text-center">
    <form action="register" method="post">
        <h1 class="h3 mb-3 fw-normal">Create Account</h1>

        <div class="form-floating p-2">
            <input name="username" type="text" class="form-control" id="floatingusername" placeholder="Username" minlength="5" maxlength="15" required>
            <label for="floatingusername">Username</label>
        </div>
        <div class="form-floating p-2">
            <input name="email" type="email" class="form-control" id="floatingemail" placeholder="E-mail" required>
            <label for="floatingemail">Email address</label>
        </div>
        <div class="form-floating p-2">
            <input name="phoneno" type="number" class="form-control" id="floatingphno" placeholder="Phone Number" min="1000000000" max="9999999999" required>
            <label for="floatingphno">Phone Number</label>
        </div>
        <div class="form-floating p-2">
            <input name='password' type="password" class="form-control" id="floatingPassword" placeholder="Password" minlength="8" maxlength="16" pattern="(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[^a-zA-Z0-9]).{8,}" required>
            <label for="floatingPassword">Password</label>
        </div>

        <button class="w-100 btn btn-lg btn-success" type="submit">Sign Up</button>

        <div class="d-flex p-3 justify-content-between align-items-center">
            <!-- Checkbox -->
            <div class="form-check mb-0">
                <input class="form-check-input me-2" type="checkbox" value="" id="form2Example3" />
                <label class="form-check-label" for="form2Example3">
                    Remember me
                </label>
            </div>
            <a href="/forgotpassword" class="text-body">Forgot password?</a>
        </div>

    </form>
</main>
<div class="footer bg-success">
    <p class="text-light text-center">&copy;2021</p>
</div>


</body>
</html>