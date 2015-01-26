package wallet;
import java.util.*;

class LoginCache{
	private HashMap<String, HashMap<String,WebLogin>> login_map;
	
	public LoginCache(){
		this.login_map = new HashMap<String, HashMap<String,WebLogin>>(); 
	}

	public String getUserLogin(String user_id){
		if (this.login_map.containsKey(user_id)){
			HashMap<String,WebLogin> temp = this.login_map.get(user_id);
			String output = "{\n";
			for (WebLogin weblogin:temp.values()){
				output += weblogin.toString() + "\n";
			}
			output +="}\n";
			return output;
		}
		else
			return "";
	}

	public void addNewUser(String user_id){
		this.login_map.put(user_id,new HashMap<String,WebLogin>());
    }

	public void addNewLogin(String user_id, WebLogin weblogin){
		this.login_map.get(user_id).put(weblogin.getLoginId(), weblogin);
    }
	
	public void deleteLogin(String user_id, String login_id){
		this.login_map.get(user_id).remove(login_id);
	}

}

