package wallet;

import java.util.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import com.fasterxml.jackson.annotation.JsonCreator;

public class User
{
  private String email, password;
  private String user_id = "u-";
  private String name;
  private String created_at, updated_at;
  static int count = 100; 
  
 @JsonCreator
  public User(Map<String,Object> props)
  {
    this.password = (String) props.get("password");
    this.email = (String) props.get("email");
    if (this.email == null || this.password == null) {
      // Invalid object.
      return;
    }
    this.name = (String) props.get("name");
    this.user_id += Integer.toString(++count);
    Date cur_date = new Date();
    this.created_at = cur_date.toString();
    this.updated_at = created_at;

  }
  
  public User(String email, String password){
	  this.email = email;
	  this.password = password;
	  this.user_id = Integer.toString(++count);
	  Date date = new Date();
	  created_at = date.toString();
	  updated_at = created_at;
  }
  
  public String getUserId(){
	  return this.user_id;
  }
  
  public String getName(){
	  return this.name;
  }
  
  public String getEmail(){
	  return this.email;
  }
  
  public String getPassword() {
	  return this.password;
  }
  
  public String getCreated_at(){
	  return this.created_at;
  }
 
  public String getUpdated_at(){
	  return this.updated_at;
  }


  public void setUserName(String _name){
	  this.name = _name;
	  Date date = new Date();
	  this.updated_at = date.toString();
  }
  
  public void setPassword(String password){
	  this.password = password;
	  Date date = new Date();
	  this.updated_at = date.toString(); 
  }
  
  public void setEmail(String email){
	  this.email = email;
	  Date date = new Date();
	  this.updated_at = date.toString();
  }
 
  public String toString(){
	  return "{\nuser_id: " + this.user_id + 
   		   "\nemail: "+ this.email+ 
   		   "\npassword: "+ this.password + 
   		   "\ncreated_at:"+ this.created_at +
   		   "\nupdated_at:"+ this.updated_at + "\n}\n\n";
  }
}

