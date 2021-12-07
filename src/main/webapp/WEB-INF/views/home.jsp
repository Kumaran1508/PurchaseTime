<%@page import="com.vk.purchasetime.models.Product"%>
<%@page import="java.util.List"%>

<html>
    <body>
        <p>Hi! ${username}</p>
        
        <%
        	List<Product> products = (List<Product>) request.getSession().getAttribute("products");
        
        	for(Product product : products){
        		out.println("<h3>"+product.getProductName()+"</h3>");
        	}
        %>

        

    </body>
</html>