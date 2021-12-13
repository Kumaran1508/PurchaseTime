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

<div class="p-5"></div>
<!--  Form  -->
<div style="align-self: center; padding-left: 200px; padding-right: 200px;" >
    <main class="form-signin text-center">
        <form action="/addproduct" method="post">
            <h1 class="h3 mb-3 fw-normal">Product Details</h1>

            <div class="form-floating">
                <input type="text" class="form-control" id="floatingname" placeholder="Product Name" name="productName" maxlength="15">
                <label for="floatingname">Product Name</label>
            </div>
            <div class="form-floating">
                <input type="text" class="form-control" id="floatingcost" placeholder="Product Cost" name="cost" >
                <label for="floatingcost">Product Cost</label>
            </div>

            <div class="form-floating">
                <input type="text" class="form-control" id="floatingunit" placeholder="Unit(eg. kg)" name="unit">
                <label for="floatingunit">Unit</label>
            </div>


            <div class="form-floating">
                <input type="text" class="form-control" id="floatingurl" placeholder="ProductURL" name="url" >
                <label for="floatingurl">ProductImage URL</label>
            </div>

            <%ProductCategory grocery= ProductCategory.GROCERY;
                ProductCategory electronics = ProductCategory.ELECTRONICS;
                ProductCategory tools=ProductCategory.TOOLS;
                ProductCategory wearables=ProductCategory.WEARABLES;
                ProductCategory homeaccesories=ProductCategory.HOME_ACCESSORIES;
            %>
            <div class="form-floating">
                <select class="form-select form-select-lg mb-3" id=floatingcategory aria-label=".form-select-lg example" name="category">
                    <option value='<%= ProductCategory.GROCERY%>' >Grocery</option>
                    <option value='<%= ProductCategory.ELECTRONICS%>'>Electronics</option>
                    <option value=<%= ProductCategory.TOOLS%> ${param.category == tools ? '' : ''}>Tools</option>
                    <option value=<%= ProductCategory.WEARABLES%> ${param.category == wearables ? '' : ''}>Wearables</option>
                    <option value=<%= ProductCategory.HOME_ACCESSORIES%> ${param.category == homeaccesories ? '' : ''}>Home Accesories</option>
                </select>
                <label for="floatingcategory">Product Category</label>
            </div>

            <div class="form-floating">
                <input type="text" class="form-control" id="floatingdiscount" placeholder="Product Discount" name="discount" >
                <label for="floatingdiscount">Product Discount</label>
            </div>
            <br>
            <button class="w-20 btn btn-lg btn-success" type="submit">Add Product</button>

        </form>
    </main>
</div>
<br><br><br><br><br>
<div class="footer bg-success" style="padding: 10px;">
    <p class="text-light text-center">© Copyright Agency and contributors 2021. Purchase Time  53 001 228 799</p>
</div>


</body>
</html>