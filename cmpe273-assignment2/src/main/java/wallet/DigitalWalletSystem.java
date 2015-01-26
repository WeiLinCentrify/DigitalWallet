package wallet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.json.JSONObject;
import com.mashape.unirest.http.*;
import java.util.*;
import javax.servlet.http.*;
import com.mongodb.*;

@RestController
public class DigitalWalletSystem  {
  //initialize all repos 
    @Autowired
    UserRepo user_repo;
    @Autowired
    IDCardRepo card_repo;
    @Autowired
    LoginRepo login_repo;
    @Autowired
    BankAccountRepo bank_repo;

    //create one object for each class which can be saved into Mongodb databse.
    Cache user_cache = new Cache();
    CardCache card_cache = new CardCache();
    LoginCache login_cache = new LoginCache();
    BankAccountCache bank_cache = new BankAccountCache();


    @RequestMapping("/api/v1")
    public String index() {
        return "Welcome to Digital Wallet System";
    }
    
    //POST:Create User
    @RequestMapping(value="/api/v1/users", method=RequestMethod.POST )
    @ResponseStatus( HttpStatus.CREATED )
    @ResponseBody
    public String CreateUser(@RequestBody User user) {
      if (user == null || user.getEmail() == null && user.getPassword() == null) {
        throw new BadRequestException("Missing required fields: email and password"); 
      }
      else if (user == null || user.getEmail() == null) {
        throw new BadRequestException("Missing required field: email"); 
      }
      else if (user == null || user.getPassword() == null) {
        throw new BadRequestException("Missing required field: password"); 
      }
      user_repo.save(user); 
      //card_repo.deleteAll();
      //login_repo.deleteAll();
      //bank_repo.deleteAll();
      if (card_repo.findAll().size()==0){
        card_repo.save(new CardCache());
        login_repo.save(new LoginCache());
        bank_repo.save(new BankAccountCache());
      }

      card_cache = card_repo.findAll().get(0);
      card_cache.addNewUser(user.getUserId());
      card_repo.deleteAll();
      card_repo.save(card_cache);
      System.out.println(card_repo.findAll().get(0));

      login_cache = login_repo.findAll().get(0);
      login_cache.addNewUser(user.getUserId());
      login_repo.deleteAll();
      login_repo.save(login_cache);

      bank_cache = bank_repo.findAll().get(0);
      bank_cache.addNewUser(user.getUserId());
      bank_repo.deleteAll();
      bank_repo.save(bank_cache);
      return user.toString();
    }
    
  //GET:View User
    @RequestMapping(value="/api/v1/users/{user_id}", method=RequestMethod.GET)
    @ResponseBody
    public String ViewUser(@PathVariable("user_id") String user_id, HttpServletRequest req, HttpServletResponse res) 
    { 
      User user = user_repo.findByUserId(user_id); 
      return user.toString();
    }
    
    
    //PUT: Update User
    @RequestMapping(value="/api/v1/users/{user_id}", method=RequestMethod.PUT )
    @ResponseStatus( HttpStatus.CREATED )
    @ResponseBody
    public String CreateUser(@PathVariable("user_id") String user_id, @RequestBody Map props) {
      // handle error  Throw HTTP400
       if ( (String) props.get("password") == null && (String) props.get("email") == null) {
        throw new BadRequestException("Required fields missing: email and password"); 
       }
       else if ((String) props.get("email") == null ) {
        throw new BadRequestException("Required fields missing: email"); 
       }
       else if ((String) props.get("password") == null) {
        throw new BadRequestException("Required fields missing: password"); 
       }

       User user = user_repo.findByUserId(user_id);
       user.setPassword((String) props.get("password"));
       user.setEmail((String) props.get("email"));

       // delete first then save to simulate update
       user_repo.deleteByUserId(user_id);
       user_repo.save(user);

       return user.toString();
    }
 // ----------------------------IDCard---------------------------------
    //POST:Create ID Card 
    @RequestMapping(value="/api/v1/users/{user_id}/idcards", method=RequestMethod.POST )
    @ResponseStatus( HttpStatus.CREATED )
    @ResponseBody
    public String CreateIDCard(@PathVariable("user_id") String user_id, @RequestBody IDCard card) {
    	// handle error 
      if (card == null || card.getCardName() == null || card.getCardNumber() == null || card.getExpirationDate()== null) {
          throw new BadRequestException("Required fields Missing.\nRequires card_name/card_number/expiration_date"); 
         }

      card_cache = card_repo.findAll().get(0);
      card.setId(card_cache.getMaxUserCardId(user_id) + 1);
      card_cache.addNewCards(user_id,card);
 
      card_repo.deleteAll();
      card_repo.save(card_cache);
      System.out.println(card_repo.findAll().get(0).getUserCards("u-102"));
      return card.toString();
    }
    
  //GET:List All ID Cards
    @RequestMapping(value="/api/v1/users/{user_id}/idcards", method=RequestMethod.GET)
    @ResponseBody
    public String ViewUserCards(@PathVariable("user_id") String user_id) {
      System.out.println(card_repo.findAll().get(0).getUserCards("u-102"));
      return card_repo.findAll().get(0).getUserCards(user_id);
    }
   
    //DELETE: Delete ID Card
    @RequestMapping(value="/api/v1/users/{user_id}/idcards/{card_id}", method=RequestMethod.DELETE )
    @ResponseStatus( HttpStatus.NO_CONTENT )
    @ResponseBody
    public void CreateUser(@PathVariable("user_id") String user_id, @PathVariable("card_id") String card_id) {
      if (card_repo.findAll().size()==0){
        card_repo.save(new CardCache());
      }
      card_cache = card_repo.findAll().get(0);
      if (user_cache.getUser(user_id) == null) {
        //handle error
       }
       card_cache.deleteCards(user_id,card_id);
       card_repo.deleteAll();
       card_repo.save(card_cache);
       
    }
   // ----------------------------Web Login---------------------------------
    //POST:Create Web Login
    @RequestMapping(value="/api/v1/users/{user_id}/weblogins", method=RequestMethod.POST )
    @ResponseStatus( HttpStatus.CREATED )
    @ResponseBody
    public String CreateIDCard(@PathVariable("user_id") String user_id, @RequestBody WebLogin weblogin) {
    	// handle error 
      if (weblogin == null || weblogin.getUrl() == null || weblogin.getLogin() == null || weblogin.getPassword()== null) {
          throw new BadRequestException("Required fields Missing.\nRequires url/login/password"); 
       }
      if (login_repo.findAll().size()==0){
        login_repo.save(new LoginCache());
      }
      login_cache = login_repo.findAll().get(0);
      weblogin.setId(login_cache.getMaxUserLoginId(user_id) + 1);
      login_cache.addNewLogin(user_id,weblogin);
      login_repo.deleteAll();
      login_repo.save(login_cache);
      return weblogin.toString();
    }
    
  //GET:List All Web-site Logins
    @RequestMapping(value="/api/v1/users/{user_id}/weblogins", method=RequestMethod.GET)
    @ResponseBody
    public String ViewUserLogins(@PathVariable("user_id") String user_id) {

      if (user_repo.findByUserId(user_id) == null) {
          return "";
      }
      if (login_repo.findAll().size()==0){
        login_repo.save(new LoginCache());
      }

      login_cache = login_repo.findAll().get(0);


      return login_cache.getUserLogin(user_id);
    }
    
    //DELETE: Delete Web Login
    @RequestMapping(value="/api/v1/users/{user_id}/weblogins/{login_id}", method=RequestMethod.DELETE )
    @ResponseStatus( HttpStatus.NO_CONTENT )
    @ResponseBody
    public void DeleteUserLogin(@PathVariable("user_id") String user_id, @PathVariable("login_id") String login_id) {
      if (user_repo.findByUserId(user_id) == null) {
        //handle error
       }
      if (login_repo.findAll().size() == 0){
        login_repo.save(new LoginCache());
      }
      login_cache = login_repo.findAll().get(0);
      login_cache.deleteLogin(user_id,login_id); 
      login_repo.deleteAll();
      login_repo.save(login_cache);
    }
 // ----------------------------Bank Account--------------------------------- 
    //POST:Create Bank Account 
    @RequestMapping(value="/api/v1/users/{user_id}/bankaccounts", method=RequestMethod.POST )
    @ResponseStatus( HttpStatus.CREATED )
    @ResponseBody
    public String CreateBankAccount(@PathVariable("user_id") String user_id, @RequestBody BankAccount bankaccount) {
    	// handle error 
    	if (bankaccount == null || bankaccount.getRoutingNumber() == null || bankaccount.getAccountNumber()== null) {
          throw new BadRequestException("Required fields Missing.\nRequires account_name/routing_number/account_number"); 
       }
      if (bank_repo.findAll().size() == 0){
        bank_repo.save(new BankAccountCache());
      }
      // ------------Assignment 2 part 2-----------------
       try {

        HttpResponse<JsonNode> jsonResponse = Unirest.get(
        "https://routingnumbers.herokuapp.com/api/data.json?rn={route_num}")
        .routeParam("route_num", bankaccount.getRoutingNumber())
        .asJson();
        JsonNode body = jsonResponse.getBody();

            String jString = body.toString();
            JSONObject jObject = new JSONObject(jString);
            String new_accountname = jObject.getString("customer_name");
            bankaccount.SetAccountName(new_accountname);
        }
             catch (Exception e) {
          System.out.println(e.toString());
      }

      bank_cache = bank_repo.findAll().get(0);
      bankaccount.setId(bank_cache.getMaxUserBankId(user_id) + 1);
      bank_cache.addNewBankAccount(user_id,bankaccount);
      bank_repo.deleteAll();
      bank_repo.save(bank_cache);
      return bankaccount.toString();
    }
    
  //GET:List All Bank Accounts
    @RequestMapping(value="/api/v1/users/{user_id}/bankaccounts", method=RequestMethod.GET)
    @ResponseBody
    public String ViewUserBankAccounts(@PathVariable("user_id") String user_id) 
    { if (user_repo.findByUserId(user_id) == null) {
          return "";
      }
      if (bank_repo.findAll().size()==0){
        bank_repo.save(new BankAccountCache());
      }
      bank_cache = bank_repo.findAll().get(0);
      return bank_cache.getUserBanks(user_id);
    }
    
    //DELETE: Delete Bank Account
    @RequestMapping(value="/api/v1/users/{user_id}/bankaccounts/{ba_id}", method=RequestMethod.DELETE )
    @ResponseStatus( HttpStatus.NO_CONTENT )
    @ResponseBody
    public void DeleteUserBankAccount(@PathVariable("user_id") String user_id, @PathVariable("ba_id") String ba_id) {
      if (user_cache.getUser(user_id) == null) {
        //handle error
       }
      if (bank_repo.findAll().size()==0){
        bank_repo.save(new BankAccountCache());
      }
      bank_cache = bank_repo.findAll().get(0);
      bank_cache.deleteBankAccount(user_id,ba_id); 
      bank_repo.deleteAll();
      bank_repo.save(bank_cache);
    }

}


