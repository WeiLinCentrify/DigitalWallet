package wallet;

import java.util.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import com.fasterxml.jackson.annotation.JsonCreator;

public class BankAccount{
  private String ba_id = "b-";
  private String account_name, routing_number,account_number;
  private int internal_id;
  
  @JsonCreator
  public BankAccount(Map<String,Object> props) {
    this.account_name = (String) props.get("account_name");
    this.routing_number = (String) props.get("routing_number");
    this.account_number = (String) props.get("account_number");
  }
  public BankAccount() {
    
  }

  public void SetAccountName(String name) {
    this.account_name =  name;
  }

  public String getBaId() {
    return this.ba_id;
  }

  public void setId(int id) {
    this.internal_id = id;
    this.ba_id += Integer.toString(internal_id);
  }

  public String getAccountName(){
	  return this.account_name;
  }
  
  public String getRoutingNumber(){
	  return this.routing_number;
  }
  
  public String getAccountNumber(){
	  return this.account_number;
  }

  public int getInternalId() {
    return this.internal_id;
  }
  
 
  public String toString(){
	  return "{\nba_id: " + this.ba_id +
			 "\naccount_name: " + this.account_name +
			 "\nrounting_number: " + this.routing_number +
			 "\naccount_number: " + this.account_number +"\n}\n";
  }
}

