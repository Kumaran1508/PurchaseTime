package com.vk.purchasetime.controllers;

import com.vk.purchasetime.Test;
import com.vk.purchasetime.models.*;
import com.vk.purchasetime.repositories.*;
import com.vk.purchasetime.services.*;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.*;

@Controller
public class ShoppingController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private InvoicePrimaryRepository invoicePrimaryRepository;
    @Autowired
    private InvoiceTransactionRepository invoiceTransactionRepository;
    @Autowired
    private PaymentService paymentService;
    @Autowired
    private InvoiceGenerator invoiceGenerator;

    @Autowired
    private Test test;


    @PostMapping(value = "/login")
    public String doLogin(@RequestParam("username") final String username, @RequestParam("password") final String password, final HttpServletRequest request, Model model,HttpServletResponse response){
        User user = userRepository.findUserByUsernameAndPassword(username,Base64.getEncoder().encodeToString(password.getBytes()));
        if (user==null)
            return "index";
        else{
            HttpSession session = request.getSession();
            session.setAttribute("username",user.getUsername());
            session.setAttribute("userid",user.getUserId());
            List<Product> productList = (List<Product>) productRepository.findAll();
            model.addAttribute("products",productList);

            SMSService smsService = new SMSService();
            String otp = smsService.smsSender("+916369463739");
            session.setAttribute("otp",otp);
            System.out.println(otp);
            return "otpauth";
        }
    }

    @GetMapping(value = "/")
    public String home(){
        return "index";
    }

    @RequestMapping(value = "/home",method = RequestMethod.POST)
    public String gohome(@RequestParam("enteredOTP") final String otp,HttpServletRequest request){
        System.out.println(request.getSession().getAttribute("otp"));
        boolean flag=false;
        if(otp.contentEquals((String)request.getSession().getAttribute("otp"))){
                System.out.println("otp verified");
                request.getSession().setAttribute("otpverified",true);
                flag=true;
        }
        else{
            System.out.println("debug");
        }

        System.out.println(request.getSession().getAttribute("username"));
        if (request.getSession().getAttribute("username")!=null && flag){
            List<Product> productList = (List<Product>) productRepository.findAll();
            request.getSession().setAttribute("products",productList);

            List<Product> topSelling = productRepository.findTop4ByOrderBySoldDesc();
            request.getSession().setAttribute("topSelling",topSelling);

            List<Product> topDeals = productRepository.findTop4ByOrderByDiscountDesc();
            request.getSession().setAttribute("topDeals",topDeals);


            return "home";
        }


        else return  "index";
    }

    @RequestMapping(value = "/home",method = RequestMethod.GET)
    public String gethome(HttpServletRequest request){
        System.out.println("on get home");
        System.out.println(request.getSession().getAttributeNames());
        if (request.getSession().getAttribute("username")!=null
                && request.getSession().getAttribute("otpverified")!=null){
            List<Product> productList = (List<Product>) productRepository.findAll();
            request.getSession().setAttribute("products",productList);


            List<Product> topSelling = productRepository.findTop4ByOrderBySoldDesc();
            request.getSession().setAttribute("topSelling",topSelling);

            List<Product> topDeals = productRepository.findTop4ByOrderByDiscountDesc();
            request.getSession().setAttribute("topDeals",topDeals);

            return "home";
        }


        else return  "index";
    }


    @RequestMapping(value = "/signup",method = RequestMethod.GET)
    public String signup(){
        return "signup";
    }

    @RequestMapping(value = "/register",method = RequestMethod.POST)
    public String register(@RequestParam("username") final String userName,
                           @RequestParam("email") final String email,
                           @RequestParam("phoneno") final String phoneno,
                           @RequestParam("password") final String password){

        String hashedpassword = Base64.getEncoder().encodeToString(password.getBytes());

        User user = new User(userName,hashedpassword,phoneno,email);
        userRepository.save(user);


        return "index";
    }


    @RequestMapping(value = "/resetpassword",method = RequestMethod.GET)
    public String resetPassword(@RequestParam(name = "authtoken") String token,HttpServletRequest request){
        User user = userRepository.findUserByResetPassToken(token);
        if (user!=null){
            request.getSession().setAttribute("userid",user.getUserId());
            return "resetpassword";
        }

        else return "/";
    }

    @RequestMapping(value = "/resetpw",method = RequestMethod.POST)
    public String reset(@RequestParam(name = "email") String email){
        User user = userRepository.findUserByEmail(email);
        if (user!=null){
            user.setResetPassToken(TokenGenerator.getToken());
            System.out.println("http://localhost:8080/resetpassword?authtoken="+user.getResetPassToken());
            EmailServicev1 emailServicev1 = new EmailServicev1();


            try{
                emailServicev1.resetPassword(email, user.getResetPassToken());
            }
            catch(Exception e){
                return "/";
            }
            userRepository.save(user);
        }

        return "index";
    }

    @RequestMapping(value = "/forgotpassword",method = RequestMethod.GET)
    public String forgotpassword(){
        return "forgotpassword";
    }


    @RequestMapping(value = "newpassword",method = RequestMethod.POST)
    public String setnewPassword(HttpServletRequest request,@RequestParam(name = "newpass")String newPassword){
        System.out.println(request.getSession().getAttribute("userid"));
        User user = userRepository.findById((int) request.getSession().getAttribute("userid")).get();
        user.setPassword(Base64.getEncoder().encodeToString(newPassword.getBytes()));
        user.setResetPassToken(null);
        userRepository.save(user);
        return "index";
    }

    @RequestMapping(value = "testpay",method = RequestMethod.GET)
    public String testpay(){return "checkoutpage";}

    @RequestMapping(value = "/create-checkout-session",method = RequestMethod.POST)
    public void maketestpay(HttpServletResponse response,HttpServletRequest request){
//        test.checkShopping();
        User user = userRepository.findById((Integer) request.getSession().getAttribute("userid")).get();
        Profile profile = new Profile(
                request.getParameter("firstName")+" "+request.getParameter("lastName"),
                request.getParameter("address")+"\n"+request.getParameter("address2"),
                ProfileType.HOME
        );
        profile.setUser(user);
        profile.setState("Tamil Nadu");
        profile.setPincode(request.getParameter("zipcode"));
        profileRepository.save(profile);
        profile.getProfileId();

        request.getSession().setAttribute("profile",profile);


        paymentService.makePayment(response, String.valueOf(request.getSession().getAttribute("amount")));


    }

    @RequestMapping(value = "/invoice",method = RequestMethod.GET)
    public String bill(HttpServletRequest request){

        User user = userRepository.findById((Integer) request.getSession().getAttribute("userid")).get();
        Profile profile = (Profile) request.getSession().getAttribute("profile");
        HashMap<Product,Integer> cart = (HashMap<Product, Integer>) request.getSession().getAttribute("cart");

        //Write to Invoice Primary Table
        InvoicePrimary invoicePrimary = new InvoicePrimary(new Date(),
                Double.valueOf(String.valueOf(request.getSession().getAttribute("amount"))),
                TransactionType.DEBIT_CARD);
        invoicePrimary.setUser(user);
        invoicePrimary.setProfile(profile);
        invoicePrimaryRepository.save(invoicePrimary);

        //Write to Invoice Transaction Table
        for (Product product : cart.keySet()){
            product.setSold(product.getSold()+cart.get(product));
            productRepository.save(product);
            InvoiceTransaction invoiceTransaction = new InvoiceTransaction(new InvoiceTransactionId(invoicePrimary.getInvoiceId(),product.getProductId()),cart.get(product),product.getCost(),product.getDiscount());
            invoiceTransactionRepository.save(invoiceTransaction);
        }

        //Create Invoice and send E-mail
        invoiceGenerator.createPdf(invoicePrimary,profile,cart);



        return "success";
    }

//    @RequestMapping(value = "/logout",method = RequestMethod.POST)
//    public String logout(HttpServletRequest request){
//        request.getSession().invalidate();
//        return "index";
//    }

    @RequestMapping(value = "/logout",method = RequestMethod.GET)
    public String logoutG(HttpServletRequest request){
        request.getSession().invalidate();
        return "index";
    }

    @RequestMapping(value = "/checkout-products",method = RequestMethod.POST)
    public String addProductstoCart(HttpServletRequest request){
        System.out.println("check cart");
        Enumeration<String> productIDs = request.getParameterNames();

        HashMap<Product,Integer> products = new HashMap<>();
        double amt = 0.0;
        while(productIDs.hasMoreElements()){
            String productId = productIDs.nextElement();
            Product product = productRepository.findById(Integer.valueOf(productId)).get();
            int count = Integer.valueOf(request.getParameter(productId));


            if(count>0){
                products.put(product,count);
                amt += product.getCost()*(100-product.getDiscount())*0.01*count;
            }

            request.getSession().removeAttribute("products");
            request.getSession().setAttribute("cart",products);
            request.getSession().setAttribute("amount",String.format("%.2f",amt));

//            InvoicePrimary invoicePrimary = new InvoicePrimary(
//                    new Date(),
//                    Double.valueOf(request.getParameter("amount")),
//
//                    );

        }
        return "checkoutpage";
    }

    @RequestMapping(value = "/addtocart",method = RequestMethod.POST)
    public String addItemtoCart(HttpServletRequest request){
        HashMap<Integer,Integer> cart = (HashMap<Integer, Integer>) request.getSession().getAttribute("cart");
        if (cart==null) cart = new HashMap<>();
        System.out.println(cart);



        Integer productId = Integer.valueOf(request.getParameter("productId"));
        System.out.println(productRepository.findById(productId).get().getProductName());

        if (productId!=null){
            if (cart.containsKey(productId))
                cart.put(productId, cart.get(productId)+1);
            else cart.put(productId, 1);
        }

        return "home";
    }

    @RequestMapping(value = "/addproduct",method = RequestMethod.POST)
    public String addproduct(@RequestParam("productName") final String productName,
                             @RequestParam("cost") final String cost,
                             @RequestParam("category") final String category,
                             @RequestParam("discount") final String discount,
                             @RequestParam("url") final String url,
                             @RequestParam("unit") final String unit){

        System.out.println(category);
        Product product=new Product(productName,Double.parseDouble(cost),ProductCategory.valueOf(category),Double.parseDouble(discount),url);
        product.setCategory(category);

        productRepository.save(product);
        return "index";

    }

    @RequestMapping(value = "/additems",method = RequestMethod.GET)
    public String addproduct(){
        return "addproduct";
    }



    @RequestMapping(value = "/productid",method = RequestMethod.POST)
    public String getproductid(@RequestParam("productId") final String productId,HttpServletRequest request)
    {
        if(productRepository.findById(Integer.parseInt(productId))!=null) {
            Product product=productRepository.findByProductId(Integer.parseInt(productId));
            request.getSession().setAttribute("editedid",productId);
            request.getSession().setAttribute("editproduct",product);

        }

        return "editproduct";

    }

    @RequestMapping(value = "/editproduct",method = RequestMethod.POST)
    public String editproduct(@RequestParam("productName") final String productName,
                              @RequestParam("cost") final String cost,
                              @RequestParam("category") final String category,
                              @RequestParam("discount") final String discount,
                              @RequestParam("url") final String url,
                              HttpServletRequest request){

        Product product=productRepository.findByProductId(Integer.parseInt((String)request.getSession().getAttribute("editedid")));
        product.setProductName(productName);
        product.setDiscount(Double.parseDouble(discount));
        product.setCategory(category);
        product.setCost(Double.parseDouble(cost));
        product.setUrl(url);
        productRepository.save(product);
        return "addproduct";
    }

    @RequestMapping(value = "/editproduct",method = RequestMethod.POST,params = "delete")
    public String deleteproduct(HttpServletRequest request){
        Product product=productRepository.findByProductId(Integer.parseInt((String)request.getSession().getAttribute("editedid")));
        productRepository.delete(product);
        return "addproduct";
    }




}
