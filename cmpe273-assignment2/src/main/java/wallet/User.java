package wallet;

import java.util.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import com.fasterxml.jackson.annotation.JsonCreator;
import org.springframework.data.annotation.Id;

public class User 

{
  private String email, password;
  @Id
  private String userId = "u-";
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
    this.userId += Integer.toString(++count);
    Date cur_date = new Date();
    this.created_at = cur_date.toString();
    this.updated_at = created_at;

  }
  
  public User(String email, String password){
	  this.email = email;
	  this.password = password;
	  this.userId = Integer.toString(++count);
	  Date date = new Date();
	  created_at = date.toString();
	  updated_at = created_at;
  }
  public User() {
  }
  
  public String getUserId(){
	  return this.userId;
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
	  return "Customer[user_id: " + this.userId + 
   		   ",email: "+ this.email+ 
   		   ",password: "+ this.password + 
   		   ",created_at:"+ this.created_at +
   		   ",updated_at:"+ this.updated_at + "]";
  }
}

