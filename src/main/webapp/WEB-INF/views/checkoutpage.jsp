<%@ page import="com.vk.purchasetime.repositories.ProductRepository" %>
<%@ page import="org.springframework.beans.factory.annotation.Autowired" %>
<%@ page import="com.vk.purchasetime.models.Product" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.HashMap" %>
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
    <title><% out.println(LanguageSupportService.get("checkout",lang)); %></title>
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


    <div class="container-fluid">
        <%--   Navbar     --%>
        <div class="container-fluid text-center mb-2">
            <header class="d-flex flex-wrap align-items-center justify-content-center justify-content-md-between py-3 mb-4 border-bottom">
                <div class="col-4 d-flex mb-1 mb-md-0 text-dark text-decoration-none align-items-center">
                    <h5 class="mr-3" id="username"><%
                        out.println(LanguageSupportService.get("hi",lang)+"! "+request.getSession().getAttribute("username"));
                    %></h5>
                </div>
                <div class="col-4 d-flex mb-1 mb-md-0 text-dark text-decoration-none justify-content-center align-items-center">
                    <img src="\assets\imgs\logo.jpg" class="img-fluid rounded" alt="logo" width="50px" height="50px">
                    <h3 class="text-success align-self-center"><% out.println(LanguageSupportService.get("apptitle",lang)); %></h3>
                </div>
                <div class="col-4 d-flex mb-1 mb-md-0 text-dark text-decoration-none justify-content-center align-items-center">
                    <a href="/logout" style="align-self: flex-end" class="btn btn-outline-danger btn-sm"><% out.println(LanguageSupportService.get("logout",lang)); %></a>
                </div>

            </header>
        </div>

        <div class="container">
            <div class="row g-5">
                <div class="col-md-5 col-lg-4 order-md-last">
                    <h4 class="d-flex justify-content-between align-items-center mb-3">
                        <span class="text-success"><% out.println(LanguageSupportService.get("yourcart",lang)); %></span>
                        <span class="badge bg-success rounded-pill">${cart.size()}</span>
                    </h4>
                    <ul class="list-group mb-3">
                        <%
                            HashMap<Product,Integer> products = (HashMap<Product, Integer>) request.getSession().getAttribute("cart");

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
                                            "<span>"+LanguageSupportService.get("totalinr",lang)+"</span>\n" +
                                            "<strong name='amount'>&#8377;"+String.format("%.2f",Total)+"</strong>\n" +
                                            "<input type=\"hidden\" name=\"amount\" value=\""+String.format("%.2f",Total)+"\">"+
                                        "</li>");
                        %>
                    </ul>
                </div>
            <div class="col-md-7 col-lg-8 ">
                <form action="/create-checkout-session" method="post">
                    <h4 class="mb-3"><% out.println(LanguageSupportService.get("billingaddress",lang)); %></h4>
                    <div class="row g-3">
                        <div class="col-sm-6">
                            <label for="firstName" class="form-label"><% out.println(LanguageSupportService.get("firstname",lang)); %></label>
                            <input type="text" name="firstName" class="form-control" id="firstName" placeholder="" value="" required="">
                            <div class="invalid-feedback">
                                Valid first name is required.
                            </div>
                        </div>

                        <div class="col-sm-6">
                            <label for="lastName" class="form-label"><% out.println(LanguageSupportService.get("lastname",lang)); %></label>
                            <input type="text" name="lastName" class="form-control" id="lastName" placeholder="" value="" required="">
                            <div class="invalid-feedback">
                                Valid last name is required.
                            </div>
                        </div>

                        <div class="col-12">
                            <label for="address" class="form-label"><% out.println(LanguageSupportService.get("address",lang)); %></label>
                            <input type="text" name="address" class="form-control" id="address" placeholder="" required="">
                            <div class="invalid-feedback">
                                Please enter your shipping address.
                            </div>
                        </div>

                        <div class="col-12">
                            <label for="address2" class="form-label"><% out.println(LanguageSupportService.get("address2",lang)); %><span class="text-muted">(Optional)</span></label>
                            <input type="text" name="address2" class="form-control" id="address2" placeholder="">
                        </div>

                        <div class="col-md-3">
                            <label for="country" class="form-label"><% out.println(LanguageSupportService.get("country",lang)); %></label>
                            <select name="country" class="form-select" id="country" required="">
                                <option><% out.println(LanguageSupportService.get("india",lang)); %></option>
                            </select>
                            <div class="invalid-feedback">
                                Please select a valid country.
                            </div>
                        </div>

                        <div class="col-md-5">
                            <label for="state" class="form-label"><% out.println(LanguageSupportService.get("state",lang)); %></label>
                            <select name="state" class="form-select" name="state" id="state" required="">
                                <option><% out.println(LanguageSupportService.get("tamilnadu",lang)); %></option>
                            </select>
                            <div class="invalid-feedback">
                                Please provide a valid state.
                            </div>
                        </div>

                        <div class="col-md-4">
                            <label for="zip" class="form-label"><% out.println(LanguageSupportService.get("zip",lang)); %></label>
                            <input type="text" name="zipcode" class="form-control" id="zip" placeholder="" required="">
                            <div class="invalid-feedback">
                                Zip code required.
                            </div>
                        </div>
                    </div>

                    <hr class="my-4">

                    <input class="w-100 btn btn-success btn-lg" type="submit" value="<% out.println(LanguageSupportService.get("continuetocheckout",lang)); %>">
                </form>
            </div>
        </div>
    </div>


    <footer>
        <div class="footer bg-success" style="position: fixed;">
            <p class="text-light text-center" style="padding: 1%; margin-top: 1px; margin-bottom: 1px;"><% out.println(LanguageSupportService.get("footertext",lang)); %></p>
        </div>
    </footer>
</body>
</html>