<%@ page import="com.vk.purchasetime.models.Product" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.List" %>
<%@ page import="com.vk.purchasetime.models.InvoiceTransaction" %>
<%@ page import="com.vk.purchasetime.services.LanguageSupportService" %>
<%
    String lang = (String) request.getSession().getAttribute("lang");
%>
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
    <title><% out.println(LanguageSupportService.get("order",lang)); %></title>
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
                <h3 class="text-success align-self-center"><% out.println(LanguageSupportService.get("apptitle",lang)); %></h3>
            </div>
            <div class="col-4 d-flex flex-row justify-content-end mb-1 mb-md-0 text-dark text-decoration-none">
                <%
                    if(username!=null && request.getSession().getAttribute("otpverified")!=null) {
                        out.println("<button type=\"submit\" class=\"btn btn-outline-success btn-sm mr-3\"><i class=\"bi bi-cart-fill mr-2\"></i>"+LanguageSupportService.get("gotocart",lang)+"</button>");
                        out.println("<a  href=\"/myorders\" class=\"btn btn-outline-dark btn-sm mr-3\"><i class=\"bi bi-receipt mr-2\"></i>"+LanguageSupportService.get("myorders",lang)+"</button>");
                        out.println("<a href=\"/logout\" class=\"btn btn-outline-danger btn-sm\"><i class=\"bi bi-box-arrow-right mr-2\"></i>"+LanguageSupportService.get("logout",lang)+"</a>");
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


    <div class="row justify-content-center py-5">
        <div class="col-md-6 col-lg-6 order-md-last justify-content-center">
            <h4 class="d-flex justify-content-between align-items-center mb-3">
                <span class="text-success"><% out.println(LanguageSupportService.get("order",lang)); %> </span>
                <span class="badge bg-success rounded-pill"><%
                    HashMap<Product,Integer> products = (HashMap<Product, Integer>) request.getSession().getAttribute("cart");
                    List<InvoiceTransaction> invoiceTransactions = (List<InvoiceTransaction>) request.getSession().getAttribute("invtrans");
                    out.println(products.size());
                %></span>
            </h4>
            <ul class="list-group mb-3">
                <%

                    double Total = 0.0;
                    int index =0;
                    for (Product product : products.keySet()){
                        Total += product.getCost()*(100-product.getDiscount())*0.01*products.get(product);
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
                        index++;
                    }

                    out.println("<li class=\"list-group-item d-flex justify-content-between\">\n" +
                            "<span>Total (INR)</span>\n" +
                            "<strong name='amount'>&#8377;"+String.format("%.2f",Total)+"</strong>\n" +
                            "<input type=\"hidden\" name=\"amount\" value=\""+String.format("%.2f",Total)+"\">"+
                            "</li>");
                %>
            </ul>
            <div class="row justify-content-center">
                <div><form action="/reorder" method="post">
                <input type="submit" class="btn btn-success w-25" value="<% out.println(LanguageSupportService.get("repeatorder",lang)); %>">
                </form></div>
            </div>
        </div>

    </div>



</body>
</html>