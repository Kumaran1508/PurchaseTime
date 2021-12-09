package com.vk.purchasetime.controllers;

import com.vk.purchasetime.models.Product;
import com.vk.purchasetime.models.User;
import com.vk.purchasetime.repositories.ProductRepository;
import com.vk.purchasetime.repositories.UserRepository;
import com.vk.purchasetime.services.EmailServicev1;
import com.vk.purchasetime.services.PaymentService;
import com.vk.purchasetime.services.SMSService;
import com.vk.purchasetime.services.TokenGenerator;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;

@Controller
public class ShoppingController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProductRepository productRepository;


    @PostMapping(value = "/login")
    public String doLogin(@RequestParam("username") final String username, @RequestParam("password") final String password, final HttpServletRequest request, Model model,HttpServletResponse response){
        User user = userRepository.findUserByUsernameAndPassword(username,Base64.getEncoder().encodeToString(password.getBytes()));
        System.out.println(Base64.getEncoder().encodeToString(password.getBytes()));
        if (user==null)
            return "index";
        else{
            user.setFlag(true);
            userRepository.save(user);
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


        PaymentService paymentService = new PaymentService();
        paymentService.makePayment(response,request.getParameter("amount"));
    }

    @RequestMapping(value = "/logout",method = RequestMethod.POST)
    public String logout(HttpServletRequest request){
        request.getSession().invalidate();
        return "index";
    }

    @RequestMapping(value = "/checkout-products",method = RequestMethod.POST)
    public String addProductstoCart(HttpServletRequest request){
        System.out.println("check cart");
        Enumeration<String> productIDs = request.getParameterNames();

        HashMap<Product,Integer> products = new HashMap<>();
        while(productIDs.hasMoreElements()){
            String productId = productIDs.nextElement();
            Product product = productRepository.findById(Integer.valueOf(productId)).get();
            int count = Integer.valueOf(request.getParameter(productId));

            System.out.println(product.getProductName()+" "+count);
            if(count>0){
                products.put(product,count);
            }


            request.getSession().removeAttribute("products");
            request.getSession().setAttribute("cart",products);
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




}
