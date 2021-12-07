<html>
<head>
    <title>Buy cool new product</title>
    <link rel="stylesheet" href="style.css">
    <script src="https://polyfill.io/v3/polyfill.min.js?version=3.52.1&features=fetch"></script>
    <script src="https://js.stripe.com/v3/"></script>

    <script>
        const stripe = Stripe("pk_test_51K434zSHbrVpMbSJqYxw0dNnzyaqIThFyl6Oysc36lXmQ97H9JimwrR5E3WbxGu8v7ZFXpzlgJMWWMrGnpuRA0dB00SguaFjRX");

    </script>
</head>
<body>
<section>
    <div class="product">
        <img src="https://i.imgur.com/EHyR2nP.png" alt="The cover of Stubborn Attachments" />
        <div class="description">
            <h3>Stubborn Attachments</h3>
            <h5>$20.00</h5>
        </div>
    </div>
    <form action="/create-checkout-session" method="POST">
        <button type="submit" id="checkout-button">Checkout</button>
    </form>
    <br>
    <div id="payment-element">
        <!--Stripe.js injects the Payment Element-->
    </div>
</section>
</body>
</html>