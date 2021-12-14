<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@page import="java.util.List"%>
<%@ page import="com.twilio.rest.chat.v1.service.UserReader" %>
<%@ page import="com.vk.purchasetime.repositories.UserRepository" %>
<%@ page import="com.vk.purchasetime.models.*" %>


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
<form action="/checkout-products" method="post">

<!--  Navbar  -->
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
            <button type="submit" class="btn btn-success btn-sm mr-3"><i class="bi bi-cart-fill mr-2"></i>Go to cart</button>
            <a href="/logout" style="align-self: flex-end" class="btn btn-outline-danger btn-sm">LOGOUT</a>
        </div>

    </header>
</div>
<div class="container-fluid">
    <div class="row">
        <!--            Navbar    -->
        <div class="col-sm-2">
            <nav id="navbar-example3" class="navbar navbar-light bg-light flex-column justify-content-start">
                <a class="navbar-brand" href="#">Home</a>
                <nav class="nav nav-pills flex-column">
                    <a class="nav-link text-success" href="#item-1">Deals fo the day</a>
                    <a class="nav-link text-success" href="#item-2">Top Selling</a>
                    <%

                        for (ProductCategory category : ProductCategory.values()){
                            out.println("<a class=\"nav-link text-success\" href='#"+category.name()+"'>"+category.name()+"</a>");
                        }
                    %>

                </nav>
            </nav>
        </div>


        <div class="col-sm-10">
        <div class="row justify-content-center">
        <%
            List<Product> products = (List<Product>) request.getSession().getAttribute("products");




            for(Product product : products){
                out.println("<div class=\"col-lg-3 d-flex my-2\">" +
                                "<div class=\"card\">\n" +
                                    "<img class=\"card-img-top shadow-sm\" src="+product.getUrl()+" class=\"card-img-top\" alt=\"...\">\n" +
                                    "<div class=\"card-body\">\n" +
                                        "<h5 class=\"card-title\">"+product.getProductName()+"</h5>\n" +
                                        "<h6 class=\"card-text\">"+product.getCategory()+"</h6>\n" +
                                        "<h5 class=\"card-title\"><s class=\"text-muted\">(&#8377;"
                                            +String.format("%.2f",product.getCost())+")</s>&#8377;"+String.format("%.2f",product.getCost()*(100-product.getDiscount())*0.01)+"</h5>\n" +
                                        "<p>Discount : "+(int) product.getDiscount()+"%</p>\n" +
                                            "<div class=\"input-group mb-2\">\n" +
                                                "<button type='button' onclick=\"change('-',this)\" value='"+product.getProductId()+"' class=\"input-group-text btn btn-outline-success\">-</button>\n" +
                                                "<input type=\"number\" class=\"form-control col-2 text-center\" id='"+product.getProductId()+"' name='"+product.getProductId()+"' value=\"0\">" +
                                                "<button type='button' onclick=\"change('+',this)\" value='"+product.getProductId()+"' class=\"input-group-text btn btn-outline-success\">+</button>\n" +
                                            "</div>"+
                                    "</div>\n" +
                                "</div>\n" +
                           "</div>");
            }

        %>
        </div></div>


    </div></div></form>
<footer>
    <div class="footer bg-success" style="position: fixed;">
        <p class="text-light text-center" style="padding: 1%; margin-top: 1px; margin-bottom: 1px;">© Copyright Agency and contributors 2021. Purchase Time  53 001 228 799</p>
    </div>
</footer>
</body>
</html>