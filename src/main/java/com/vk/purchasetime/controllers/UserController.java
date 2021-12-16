package com.vk.purchasetime.controllers;

import com.vk.purchasetime.Test;
import com.vk.purchasetime.models.Product;
import com.vk.purchasetime.models.User;
import com.vk.purchasetime.repositories.*;
import com.vk.purchasetime.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Base64;
import java.util.List;

@Controller
public class UserController {

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
    public String doLogin(@RequestParam("username") final String username, @RequestParam("password") final String password, final HttpServletRequest request, Model model, HttpServletResponse response){
        if (username.contentEquals("admin") & password.contentEquals("Admin@123")) return "addproduct";
        User user = userRepository.findUserByUsernameAndPassword(username, Base64.getEncoder().encodeToString(password.getBytes()));
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

    @RequestMapping(value = "/logout",method = RequestMethod.GET)
    public String logoutG(HttpServletRequest request){
        request.getSession().invalidate();
        return "index";
    }

}
