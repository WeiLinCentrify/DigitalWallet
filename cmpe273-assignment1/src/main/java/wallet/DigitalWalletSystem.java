package wallet;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import java.util.*;
import javax.servlet.http.*;

@RestController
public class DigitalWalletSystem  {
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
       user_cache.addUser(user);
       card_cache.addNewUser(user.getUserId());
       login_cache.addNewUser(user.getUserId());
       bank_cache.addNewUser(user.getUserId());
       return user.toString();
    }
    
  //GET:View User
    @RequestMapping(value="/api/v1/users/{user_id}", method=RequestMethod.GET)
    @ResponseBody
    public String ViewUser(@PathVariable("user_id") String user_id, HttpServletRequest req, HttpServletResponse res) 
    { 
      String last_modified = req.getHeader("If-None-Match");
      Boolean[] is_deleted = new Boolean[1];
      User user = user_cache.getUser(user_id, is_deleted, last_modified);
      if (user == null) {
         if(!is_deleted[0]) {
	     res.setStatus(304); // Not modified
	 }
         return "{}";	 
      }
      res.setHeader("ETAG", user.getUpdated_at());
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
       
       user_cache.updateUser(user_id,(String) props.get("password"),(String) props.get("email"));
       User user = user_cache.getUser(user_id);
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
     
       card_cache.addNewCards(user_id,card);
       return card.toString();
    }
    
  //GET:List All ID Cards
    @RequestMapping(value="/api/v1/users/{user_id}/idcards", method=RequestMethod.GET)
    @ResponseBody
    public String ViewUserCards(@PathVariable("user_id") String user_id) 
    { if (user_cache.getUser(user_id) == null) {
          return "";
      }
      return card_cache.getUserCards(user_id);
    }
   
    //DELETE: Delete ID Card
    @RequestMapping(value="/api/v1/users/{user_id}/idcards/{card_id}", method=RequestMethod.DELETE )
    @ResponseStatus( HttpStatus.NO_CONTENT )
    @ResponseBody
    public void CreateUser(@PathVariable("user_id") String user_id, @PathVariable("card_id") String card_id) {
       if (user_cache.getUser(user_id) == null) {
        //handle error
       }
       card_cache.deleteCards(user_id,card_id);
       
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
       login_cache.addNewLogin(user_id,weblogin);
       return weblogin.toString();
    }
    
  //GET:List All Web-site Logins
    @RequestMapping(value="/api/v1/users/{user_id}/weblogins", method=RequestMethod.GET)
    @ResponseBody
    public String ViewUserLogins(@PathVariable("user_id") String user_id) 
    { if (user_cache.getUser(user_id) == null) {
          return "";
      }
      return login_cache.getUserLogin(user_id);
    }
    
    //DELETE: Delete Web Login
    @RequestMapping(value="/api/v1/users/{user_id}/weblogins/{login_id}", method=RequestMethod.DELETE )
    @ResponseStatus( HttpStatus.NO_CONTENT )
    @ResponseBody
    public void DeleteUserLogin(@PathVariable("user_id") String user_id, @PathVariable("login_id") String login_id) {
       if (user_cache.getUser(user_id) == null) {
        //handle error
       }
       login_cache.deleteLogin(user_id,login_id); 
    }
 // ----------------------------Bank Account--------------------------------- 
    //POST:Create Bank Account 
    @RequestMapping(value="/api/v1/users/{user_id}/bankaccounts", method=RequestMethod.POST )
    @ResponseStatus( HttpStatus.CREATED )
    @ResponseBody
    public String CreateIDCard(@PathVariable("user_id") String user_id, @RequestBody BankAccount bankaccount) {
    	// handle error 
    	if (bankaccount == null || bankaccount.getAccountName() == null || bankaccount.getRoutingNumber() == null || bankaccount.getAccountNumber()== null) {
          throw new BadRequestException("Required fields Missing.\nRequires account_name/routing_number/account_number"); 
       }
       bank_cache.addNewBankAccount(user_id,bankaccount);
       return bankaccount.toString();
    }
    
  //GET:List All Bank Accounts
    @RequestMapping(value="/api/v1/users/{user_id}/bankaccounts", method=RequestMethod.GET)
    @ResponseBody
    public String ViewUserBankAccounts(@PathVariable("user_id") String user_id) 
    { if (user_cache.getUser(user_id) == null) {
          return "";
      }
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
       bank_cache.deleteBankAccount(user_id,ba_id); 
    }
}


