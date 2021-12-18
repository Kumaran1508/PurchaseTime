<%@ page import="com.vk.purchasetime.models.ProductCategory" %>
<%@ page import="com.vk.purchasetime.models.Product" %>
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
    <title>Purchase Time - login</title>
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
<div style="align-self: center;" >
    <main class="text-center">
        <div class="row align-items-center">
            <div class="col-4 form-signin">
                <form action="/addproduct" method="post">
                    <h1 class="h3 mb-3 fw-normal">Product Details</h1>

                    <div class="form-floating m-1">
                        <input type="text" class="form-control" id="floatingname" placeholder="Product Name" name="productName" maxlength="15">
                        <label for="floatingname">Product Name</label>
                    </div>
                    <div class="form-floating m-1">
                        <input type="text" class="form-control" id="floatingcost" placeholder="Product Cost" name="cost" >
                        <label for="floatingcost">Product Cost</label>
                    </div>

                    <div class="form-floating m-1">
                        <input type="text" class="form-control" id="floatingunit" placeholder="Unit(eg. kg)" name="unit">
                        <label for="floatingunit">Unit</label>
                    </div>


                    <div class="form-floating m-1">
                        <input type="text" class="form-control" id="floatingurl" placeholder="ProductURL" name="url" >
                        <label for="floatingurl">ProductImage URL</label>
                    </div>


                    <div class="form-floating m-1">
                        <select class="form-select form-select-sm mb-1" id=floatingcategory aria-label=".form-select-lg example" name="category">
                            <%
                                ProductCategory[] categories = ProductCategory.values();
                                for (ProductCategory category:categories){
                                    out.println("<option class='' value='"+category.name()+"'>"+category.name()+"</option>");
                                }
                            %>
                        </select>
                        <label for="floatingcategory">Product Category</label>
                    </div>

                    <div class="form-floating m-1">
                        <input type="text" class="form-control" id="floatingdiscount" placeholder="Product Discount" name="discount" >
                        <label for="floatingdiscount">Product Discount</label>
                    </div>
                    <br>
                    <button class="w-20 btn btn btn-success" type="submit">Add Product</button>

                </form>
            </div>
            <div class="col-4 form-signin">
    <form action="/getreport" method="post">
        <h1 class="h3 mb-3 fw-normal">Invoice Report</h1>
        <div class="form-floating">
            <input type="date" class="form-control" id="fromdate" placeholder="From Date" name="fromdate">
            <label for="fromdate">Invoice From </label>
        </div>
        <div class="form-floating mt-1">
            <input type="date" class="form-control" id="todate" placeholder="To Date" name="todate">
            <label for="todate">Invoice To</label>
        </div>
        <br>
        <button class="w-20 btn btn-success" type="submit">Download Report</button>
    </form>
</div>
            <div class="col-4 form-signin">

                <form action="/productid" method="post">
                    <h1 class="h3 mb-3 fw-normal">Modify Product</h1>
                    <div class="form-floating">
                        <input type="text" class="form-control" id="floatingid" placeholder="Product Id" name="productId" maxlength="15">
                        <label for="floatingid">Product Id</label>
                    </div>
                    <br>
                    <button class="w-20 btn btn btn-success" type="submit">Edit/Delete Product</button>
                </form>


            </div>

        </div>

    </main>
</div>
<br><br><br><br><br>
<div class="footer bg-success" style="padding: 10px; bottom:-200px">
    <p class="text-light text-center">&copy; Copyright Agency and contributors 2021. Purchase Time  53 001 228 799</p>
</div>


</body>
</html>