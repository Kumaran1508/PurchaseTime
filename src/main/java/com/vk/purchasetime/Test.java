package com.vk.purchasetime;

import com.vk.purchasetime.models.*;
import com.vk.purchasetime.repositories.*;
import com.vk.purchasetime.services.InvoiceGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.util.HashMap;

@Component
public class Test implements CommandLineRunner {

    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final ProfileRepository profileRepository;
    @Autowired
    private final ProductRepository productRepository;
    @Autowired
    private final HubRepository hubRepository;
    @Autowired
    private final InvoicePrimaryRepository invoicePrimaryRepository;
    @Autowired
    private final TransactionRepository transactionRepository;
    @Autowired
    private final InvoiceTransactionRepository invoiceTransactionRepository;

    public Test(UserRepository userRepository, ProfileRepository profileRepository, ProductRepository productRepository, HubRepository hubRepository, InvoicePrimaryRepository invoicePrimaryRepository, TransactionRepository transactionRepository, InvoiceTransactionRepository invoiceTransactionRepository) {
        this.userRepository = userRepository;
        this.profileRepository = profileRepository;
        this.productRepository = productRepository;
        this.hubRepository = hubRepository;
        this.invoicePrimaryRepository = invoicePrimaryRepository;
        this.transactionRepository = transactionRepository;
        this.invoiceTransactionRepository = invoiceTransactionRepository;
    }


    @Override
    public void run(String... args) throws Exception {
//        testPrimaryTransaction();
//        testUserProfile();
//        testProductHub();
//        testprimaryInvoiceTrans();
//        testPrimatyProf();
//        checkShopping(null);
//        print();


        






    }

    void testUserProfile(){
        User user = new User("ahsgdgah","yfasfa","","");
        userRepository.save(user);

        Profile profile = new Profile("asjafaaggd","ajsfkhakfhkhkh", ProfileType.HOME);

        user.getProfiles().add(profile);
        profile.setUser(user);

        profileRepository.save(profile);
    }

    void testProductHub(){
        Product product = new Product("ashdaf",2456.56, ProductCategory.GROCERY,34,"");
        product.setProductId((int)productRepository.count()+1);
        productRepository.save(product);

        Hub hub = new Hub(new HubId(600014,product.getProductId()),5);

        hubRepository.save(hub);
    }

    void testPrimaryTransaction(){
        InvoicePrimary invoicePrimary = new InvoicePrimary(new Date(120,11,1),234.23,TransactionType.CASH_ON_DELIVERY);

        Transaction transaction = new Transaction(TransactionStatus.SUCCESS,3400);
        transaction.setInvoicePrimary(invoicePrimary);
        invoicePrimary.getTransactions().add(transaction);

        invoicePrimaryRepository.save(invoicePrimary);
        transactionRepository.save(transaction);

    }

    void testprimaryInvoiceTrans(){



        InvoicePrimary invoicePrimary = new InvoicePrimary(new Date(121,11,1),234.23,TransactionType.CASH_ON_DELIVERY);
        invoicePrimaryRepository.save(invoicePrimary);


        InvoiceTransaction invoiceTransaction=new InvoiceTransaction(new InvoiceTransactionId((int) invoicePrimary.getInvoiceId(),1),2.0,234.0,5.0);
        invoiceTransaction.setInvoicePrimary(invoicePrimary);
        InvoiceTransaction invoiceTransaction1=new InvoiceTransaction(new InvoiceTransactionId((int) invoicePrimary.getInvoiceId(),2),2.0,234.0,5.0);
        invoiceTransaction1.setInvoicePrimary(invoicePrimary);


        invoicePrimary.getInvoiceTransactions().add(invoiceTransaction);
        invoicePrimary.getInvoiceTransactions().add(invoiceTransaction1);


        invoiceTransactionRepository.save(invoiceTransaction);
        invoiceTransactionRepository.save(invoiceTransaction1);

    }

    void testPrimatyProf(){
        InvoicePrimary invoicePrimary = new InvoicePrimary(new Date(121,11,5),345.0,TransactionType.DEBIT_CARD);


        Profile profile = new Profile("asjafaaggd","ajsfkhakfhkhkh", ProfileType.HOME);
        profile.getInvoicePrimaryList().add(invoicePrimary);

        invoicePrimary.setProfile(profile);
        profileRepository.save(profile);
        invoicePrimaryRepository.save(invoicePrimary);
    }

    void print(){
        for (InvoicePrimary invoicePrimary : invoicePrimaryRepository.findAll()){
            System.out.println(invoicePrimary);
        }
    }

    public void checkShopping(HashMap<Product,Integer> cart){
        User user = new User("Kumaran","pcsdsged","6369463739","kumarans1508@gmail.com");
        userRepository.save(user);

        Profile profile = new Profile("asjafaaggd","ajsfkhakfhkhkh", ProfileType.HOME);
        user.getProfiles().add(profile);
        profile.setUser(user);
        profile.setState("TamilNadu");
        profile.setPincode("600053");
        profile.setPhoneNumber("9551046889");

        InvoicePrimary invoicePrimary = new InvoicePrimary(new Date(121,11,5),345.0,TransactionType.DEBIT_CARD);
        invoicePrimary.setProfile(profile);
        invoicePrimary.setUser(user);
        profile.getInvoicePrimaryList().add(invoicePrimary);



        Transaction transaction = new Transaction(TransactionStatus.SUCCESS,3400);
        transaction.setInvoicePrimary(invoicePrimary);

        invoicePrimary.getTransactions().add(transaction);

        profileRepository.save(profile);
        invoicePrimaryRepository.save(invoicePrimary);

        InvoiceTransaction invoiceTransaction=new InvoiceTransaction(new InvoiceTransactionId((int) invoicePrimary.getInvoiceId(),1),2.0,234.0,5.0);
        InvoiceTransaction invoiceTransaction1=new InvoiceTransaction(new InvoiceTransactionId((int) invoicePrimary.getInvoiceId(),2),1.0,114.25,7.0);


        invoiceTransaction1.setInvoicePrimary(invoicePrimary);
        invoiceTransaction.setInvoicePrimary(invoicePrimary);
        invoicePrimary.getInvoiceTransactions().add(invoiceTransaction);
        invoicePrimary.getInvoiceTransactions().add(invoiceTransaction1);

        invoiceTransactionRepository.save(invoiceTransaction);
        invoiceTransactionRepository.save(invoiceTransaction1);
        transactionRepository.save(transaction);


        InvoiceGenerator invoiceGenerator = new InvoiceGenerator();
        invoiceGenerator.createPdf(invoicePrimary,profile,cart);




    }
}
