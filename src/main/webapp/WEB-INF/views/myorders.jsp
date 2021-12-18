<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@page import="java.util.List"%>
<%@ page import="com.twilio.rest.chat.v1.service.UserReader" %>
<%@ page import="com.vk.purchasetime.repositories.UserRepository" %>
<%@ page import="com.vk.purchasetime.models.*" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Collections" %>


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
        // function change(sign,input){
        //     if (sign=='-' && document.getElementById(input.value).value>0) document.getElementById(input.value).value--;
        //     else if (sign=='+') document.getElementById(input.value).value++;
        // }

        function change(sign,input){
            if (sign=='-' && document.getElementById(input.value).value>0) document.getElementsByName(input.value).forEach(subract);
            else if (sign=='+') document.getElementsByName(input.value).forEach(add)
        }

        function add(element){
            element.value++;
        }

        function subract(element){
            element.value--;
        }
    </script>
</head>
<body class="container-fluid">
    <!--  Navbar  -->
    <div class="container-fluid text-center mb-2">
        <header class="d-flex flex-wrap align-items-center justify-content-center justify-content-md-between py-3 mb-4 border-bottom">
            <div class="col-4 d-flex mb-1 mb-md-0 text-dark text-decoration-none align-items-center">
                <h5 class="mr-3" id="username"><%
                    //Hi! ${username}
                    String username= (String) request.getSession().getAttribute("username");
                    if(username!=null)
                        out.println("Hi! "+username);
                    else out.println("Please Login");
                %></h5>
            </div>
            <div class="col-4 d-flex mb-1 mb-md-0 text-dark text-decoration-none justify-content-center align-items-center">
                <img src="\assets\imgs\logo.jpg" class="img-fluid rounded" alt="logo" width="50px" height="50px">
                <h3 class="text-success align-self-center">Purhcase Time</h3>
            </div>
            <div class="col-4 d-flex flex-row justify-content-end mb-1 mb-md-0 text-dark text-decoration-none">
                <%
                    if(username!=null && request.getSession().getAttribute("otpverified")!=null) {
                        out.println("<f<button type=\"submit\" class=\"btn btn-outline-success btn-sm mr-3\"><i class=\"bi bi-cart-fill mr-2\"></i>Go to cart</button>");
                        out.println("<a href=\"/logout\" class=\"btn btn-outline-danger btn-sm\"><i class=\"bi bi-box-arrow-right mr-2\"></i>LOGOUT</a>");
                    }
                    else if(username!=null && request.getSession().getAttribute("otpverified")==null){
                        response.sendRedirect("/otpauth");
                    }
                    else
                        out.println("<a href=\"/login\" style=\"align-self: flex-end\" class=\"btn btn-outline-success btn-sm\">Login</a>");
                %>


            </div>

        </header>
    </div>

    <h3>My Orders</h3>
    <div class="table-responsive">
        <table class="table table-striped table-light table-hover table-boderless text-center">
            <thead>
            <tr class="">
                <th class="bg-success text-light col-2" scope="col">Id</th>
                <th class="bg-success text-light col-6" scope="col">Date</th>
                <th class="bg-success text-light col-2" scope="col">Amount</th>
                <th class="bg-success text-light col-2" scope="col">Invoice</th>
            </tr>
            </thead>
            <tbody>

            <%
                ArrayList<InvoicePrimary> li=(ArrayList<InvoicePrimary>)request.getSession().getAttribute("userinvoicelist");
                Collections.reverse(li);
                for(InvoicePrimary ip:li){
                out.println (" <tr> <th scope=\"row\">"+ip.getInvoiceId()+
                        "</th> <td>"+ip.getInvoiceDate()+
                        "</td> <td>"+ip.getAmount()+"</td> "
                        +"<td><form action='/showorder' method='post'><input type='hidden' name='invoiceid' value='"+ ip.getInvoiceId()+"'><input type=\"submit\" value=\"View\" class=\"btn btn-success\"></form></td>\n" +
                        "</tr>");
                }
            %>



            </tbody>
        </table>
    </div>


<footer>
    <div class="bg-success">
        <p class="text-light text-center" style="padding: 1%; margin-top: 1px; margin-bottom: 1px;">&copy; Copyright Agency and contributors 2021. Purchase Time  53 001 228 799</p>
    </div>
</footer>
</body>
</html>