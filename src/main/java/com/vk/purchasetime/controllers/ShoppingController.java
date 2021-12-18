package com.vk.purchasetime.controllers;

import com.vk.purchasetime.Test;
import com.vk.purchasetime.models.*;
import com.vk.purchasetime.repositories.*;
import com.vk.purchasetime.services.*;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.tomcat.util.file.ConfigurationSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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



    @GetMapping(value = "/")
    public String home(HttpServletRequest request){

        List<Product> productList = (List<Product>) productRepository.findAll();
        request.getSession().setAttribute("products",productList);


        List<Product> topSelling = productRepository.findTop4ByOrderBySoldDesc();
        request.getSession().setAttribute("topSelling",topSelling);

        List<Product> topDeals = productRepository.findTop4ByOrderByDiscountDesc();
        request.getSession().setAttribute("topDeals",topDeals);
        return "home";
    }
    @GetMapping(value = "/myorders")
    public String myorders(HttpServletRequest request){

        try {
            request.getSession().setAttribute("userinvoicelist",invoicePrimaryRepository.findByUser(userRepository.findById((int)request.getSession().getAttribute("userid")).get()));
        } catch (Exception e) {
            return "error";
        }
        return "myorders";
    }

    @RequestMapping(value = "/home",method = RequestMethod.GET)
    public String gethome(HttpServletRequest request){
        System.out.println("on get home");
        System.out.println(request.getSession().getAttributeNames());
        try {
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
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
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
        new Thread(){
            @Override
            public void run() {
                invoiceGenerator.createPdf(invoicePrimary,profile,cart);
            }
        }.start();



        return "success";
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
        }
        request.getSession().removeAttribute("products");
        request.getSession().setAttribute("cart",products);
        request.getSession().setAttribute("amount",String.format("%.2f",amt));
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


    @RequestMapping(value = "/error",method = RequestMethod.POST)
    public String error(){
        return "error";
    }

    @PostMapping(value = "/showorder")
    public String showorder(HttpServletRequest request){
        int invoiceid = Integer.parseInt(request.getParameter("invoiceid"));
        HashMap<Product,Integer> products = new HashMap<>();
        List<InvoiceTransaction> invoiceTransactions = invoiceTransactionRepository.findAllByInvoicePrimaryInvoiceId((long)invoiceid);
        for (InvoiceTransaction invoiceTransaction : invoiceTransactions){
            products.put(productRepository.findById(invoiceTransaction.getInvoiceTransactionId().getProductId()).get(),(int) invoiceTransaction.getQuantity());
        }
        request.getSession().setAttribute("cart",products);
        request.getSession().setAttribute("invtrans",invoiceTransactions);
        return "order";
    }

    @PostMapping(value = "/reorder")
    public String reorder(HttpServletRequest request){
        double amt = 0.0;
        HashMap<Product,Integer> products = new HashMap<>();
        for (Product product : products.keySet()){
            amt += product.getCost()*(100-product.getDiscount())*0.01*products.get(product);
        }
        request.getSession().setAttribute("amount",String.format("%.2f",amt));
        return "checkoutpage";
    }

    @PostMapping(value = "/getreport")
    public ResponseEntity<InputStreamResource> download(HttpServletRequest request,HttpServletResponse httpServletResponse,
                                                                 @RequestParam("fromdate") String from,
                                                                 @RequestParam("todate") String to)throws IOException {
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        try{
            Date fromdate = formatter.parse(from);
            Date todate = formatter.parse(to);

            List<InvoicePrimary> invoices = invoicePrimaryRepository.findAllByInvoiceDateBetween(fromdate,todate);
            for (InvoicePrimary invoice : invoices) System.out.println(invoice.getInvoiceId());

            SaleDataGenerator dataGenerator = new SaleDataGenerator();
            String filepath = dataGenerator.excelSheetGenerator(invoices);

            System.out.println(filepath);
            Path path = Paths.get(filepath);

            String filename = "tutorials.xlsx";
            byte[] encoded = Files.readAllBytes(path);
            ByteArrayInputStream is= new ByteArrayInputStream(encoded);
            InputStreamResource file = new InputStreamResource(is);

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
                    .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
                    .body(file);

//            File f = new File(filepath);
//                    httpServletResponse.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
//            httpServletResponse.setContentLength((int) f.length());
//            httpServletResponse.setHeader("Content-Disposition", "attachment; filename=PaymentDetails.xlsx");
//            FileCopyUtils.copy(f,httpServletResponse.getOutputStream());
//            httpServletResponse.flushBuffer();
//
//            byte[] encoded = Files.readAllBytes(path);
//            String result= StandardCharsets.UTF_8.decode(ByteBuffer.wrap(encoded)).toString();
//
//            InputStream is = new ByteArrayInputStream(result.getBytes("UTF-8"));
//
//            return ResponseEntity.ok()
//                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=reports.xlsx")
//                    .contentLength(filepath.length())
//                    .contentType(MediaType.valueOf("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=UTF-8"))
//                    .body(is);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }


     //   return "addproduct";
    }



}
