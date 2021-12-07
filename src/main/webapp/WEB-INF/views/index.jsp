<html>
<head></head>

<body>
<h1>Login</h1>

<form name='f' action="login" method='POST'>

    <table>
        <tr>
            <td>User:</td>
            <td><input type='text' name='username' value=''></td>
        </tr>
        <tr>
            <td>Password:</td>
            <td><input type='password' name='password' /></td>
        </tr>
        <tr>
            <td>Remember Me:</td>
            <td><input type="checkbox" name="remember-me" /></td>
        </tr>
        <tr>
            <td><input name="submit" type="submit" value="Login" /></td>
        </tr>
    </table>

</form>
<br>

<form action="signup" method="post">
    <input type="submit" value="Signup">
</form>
<a href="forgotpassword">Forgot Passowrd?</a>
</body>
</html>