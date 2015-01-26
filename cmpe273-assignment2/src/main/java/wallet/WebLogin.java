package wallet;

import java.util.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import com.fasterxml.jackson.annotation.JsonCreator;

public class WebLogin{
  private String login_id = "l-";
  private String url, login, password;
  private int internal_id;
  
  @JsonCreator
  public WebLogin(Map<String,Object> props) {
    this.url = (String) props.get("url");
    this.login = (String) props.get("login");
    this.password = (String) props.get("password");   
  }

  public WebLogin() {
    
  }

  public void setId(int id) {
    this.internal_id = id;
    this.login_id += Integer.toString(internal_id);
  }
  
  public String getLoginId(){
	  return this.login_id;
  }

  public int getInternalId() {
    return this.internal_id;
  }
  
  public String getUrl(){
	  return this.url;
  }
  
  public String getPassword(){
	  return this.password;
  }
  
  public String getLogin() {
	  return this.login;
  }
  
 
  public String toString(){
	  return "{\nlogin_id: " + this.login_id +
			 "\nurl: " + this.url +
			 "\nlogin: " + this.login +
			 "\npassword: " + this.password +"\n}\n";
  }
}

