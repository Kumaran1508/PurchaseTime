<%@ page import="com.vk.purchasetime.repositories.ProductRepository" %>
<%@ page import="org.springframework.beans.factory.annotation.Autowired" %>
<%@ page import="com.vk.purchasetime.models.Product" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.HashMap" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
    <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.7.2/font/bootstrap-icons.css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>


    <link rel="stylesheet" href="signin.css">
    <link rel="canonical" href="https://getbootstrap.com/docs/5.1/examples/sign-in/">
    <link rel="icon" href="assets\imgs\logo-light.png">

    <meta charset="UTF-8">
    <title>Home</title>
    <style>
        .card-img-top {
            width: 100%;
            height: 100%;
            object-fit: cover;
        }
        .card:hover {
            background-color: #eee;
            transform: scale(1.03);
        }
    </style>
    <script>
        function change(sign,input){
            if (sign=='-' && document.getElementById(input.value).value>0) document.getElementById(input.value).value--;
            else if (sign=='+') document.getElementById(input.value).value++;
        }
    </script>
</head>
<body class="container-fluid">
<form action="/create-checkout-session" method="post">
    <div class="container">
        <div class="container-fluid text-center mb-2">
            <header class="d-flex flex-wrap align-items-center justify-content-center justify-content-md-between py-3 mb-4 border-bottom">
                <div class="col-4 d-flex mb-1 mb-md-0 text-dark text-decoration-none align-items-center">
                    <h5 class="mr-3" id="username">Hi! ${username}</h5>
                </div>
                <div class="col-4 d-flex mb-1 mb-md-0 text-dark text-decoration-none justify-content-center align-items-center">
                    <img src="\assets\imgs\logo.jpg" class="img-fluid rounded" alt="logo" width="50px" height="50px">
                    <h3 class="text-success align-self-center">Purhcase Time</h3>
                </div>
                <div class="col-4 d-flex mb-1 mb-md-0 text-dark text-decoration-none justify-content-center align-items-center">
                    <a href="/logout" style="align-self: flex-end" class="btn btn-outline-danger btn-sm">LOGOUT</a>
                </div>

            </header>
        </div>
        <form class="needs-validation" novalidate="">
            <div class="container">
                <div class="row g-5">
                    <div class="col-md-5 col-lg-4 order-md-last">
                        <h4 class="d-flex justify-content-between align-items-center mb-3">
                            <span class="text-success">Your cart</span>
                            <span class="badge bg-success rounded-pill">${cart.size()}</span>
                        </h4>
                        <ul class="list-group mb-3">

    <%

        HashMap<Product,Integer> products = (HashMap<Product, Integer>) request.getSession().getAttribute("cart");

        double Total = 0.0;
        int index =0;
        for (Product product : products.keySet()){
            Total += product.getCost()*product.getDiscount()*0.01*products.get(product);
            if(index%2==0){
                out.println("<li class=\"list-group-item d-flex justify-content-between lh-sm\">" +
                                "<div>" +
                                    "<h6 class=\"my-0\">"+product.getProductName()+"</h6>" +
                                    "<small class=\"text-muted\">"+products.get(product)+"</small>" +
                                "</div>" +
                                "<span class=\"text-muted\">&#8377;"+String.format("%.2f",product.getCost()*(100-product.getDiscount())*0.01*products.get(product))+"</span>" +
                            "</li>");
            }
            else{
                out.println("<li class=\"list-group-item d-flex justify-content-between bg-light\">" +
                        "<div class=\"text-success\">" +
                        "<h6 class=\"my-0\">"+product.getProductName()+"</h6>" +
                        "<small class=\"text-muted\">"+products.get(product)+"</small>" +
                        "</div>" +
                        "<span class=\"text-success\">&#8377;"+String.format("%.2f",product.getCost()*(100-product.getDiscount())*0.01*products.get(product))+"</span>" +
                        "</li>");
            }
            Total += product.getCost()*(100-product.getDiscount())*0.01*products.get(product);
        }

        out.println("<li class=\"list-group-item d-flex justify-content-between\">\n" +
                        "<span>Total (INR)</span>\n" +
                        "<strong>&#8377;"+String.format("%.2f",Total)+"</strong>\n" +
                    "</li>");
    %>
        </ul>
    </div>
    <div class="col-md-7 col-lg-8 ">
        <h4 class="mb-3">Billing address</h4>
        <div class="row g-3">
            <div class="col-sm-6">
                <label for="firstName" class="form-label">First name</label>
                <input type="text" class="form-control" id="firstName" placeholder="" value="" required="">
                <div class="invalid-feedback">
                    Valid first name is required.
                </div>
            </div>

            <div class="col-sm-6">
                <label for="lastName" class="form-label">Last name</label>
                <input type="text" class="form-control" id="lastName" placeholder="" value="" required="">
                <div class="invalid-feedback">
                    Valid last name is required.
                </div>
            </div>

            <div class="col-12">
                <label for="address" class="form-label">Address</label>
                <input type="text" class="form-control" id="address" placeholder="1234 Main St" required="">
                <div class="invalid-feedback">
                    Please enter your shipping address.
                </div>
            </div>

            <div class="col-12">
                <label for="address2" class="form-label">Address 2 <span class="text-muted">(Optional)</span></label>
                <input type="text" class="form-control" id="address2" placeholder="Apartment or suite">
            </div>

            <div class="col-md-3">
                <label for="country" class="form-label">Country</label>
                <select class="form-select" id="country" required="">
                    <option>India</option>
                </select>
                <div class="invalid-feedback">
                    Please select a valid country.
                </div>
            </div>

            <div class="col-md-5">
                <label for="state" class="form-label">State</label>
                <select class="form-select" name="state" id="state" required="">
                    <option>Tamil Nadu</option>
                </select>
                <div class="invalid-feedback">
                    Please provide a valid state.
                </div>
            </div>

            <div class="col-md-4">
                <label for="zip" class="form-label">Zip</label>
                <input type="text" class="form-control" id="zip" placeholder="" required="">
                <div class="invalid-feedback">
                    Zip code required.
                </div>
            </div>
        </div>

        <hr class="my-4">


        <button class="w-100 btn btn-success btn-lg" type="submit">Continue to checkout</button>

    </div>
    </div>
    </div>


    <footer>
        <div class="footer bg-success" style="position: fixed;">
            <p class="text-light text-center" style="padding: 1%; margin-top: 1px; margin-bottom: 1px;">© Copyright Agency and contributors 2021. Purchase Time  53 001 228 799</p>
        </div>
    </footer>
</body>
</html>