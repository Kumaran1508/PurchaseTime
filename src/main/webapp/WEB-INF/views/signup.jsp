<html>
<body>
<form action="register" method="post">
<table>
        <tr>
            <td>UserName:</td>
            <td><input type='text' name='username' ></td>
        </tr>
        <tr>
            <td>Email:</td>
            <td><input type='email' name='email' /></td>
        </tr>
        <tr>
            <td>Phone:</td>
            <td><input type='text' name='phoneno' pattern=[0-9]{10}></td>
        </tr>
        <tr>
            <td>Password:</td>
            <td><input type='password' name='password' /></td>
        </tr>
        <tr>
            <td><input name="submit" type="submit" value="Signup" /></td>
        </tr>
    </table>
</form>
</body>
</html>