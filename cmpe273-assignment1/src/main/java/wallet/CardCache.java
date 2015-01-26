package wallet;
import java.util.*;

class CardCache{
	private HashMap<String, HashMap<String,IDCard>> card_map;
	
	public CardCache(){
		this.card_map = new HashMap<String, HashMap<String,IDCard>>(); 
	}

	public String getUserCards(String user_id){
		if (this.card_map.containsKey(user_id)){
			HashMap<String,IDCard> temp = this.card_map.get(user_id);
			String output = "{\n";
			for (IDCard card:temp.values()){
				output += card.toString() + "\n";
			}
			output +="}\n";
			return output;
		}
		else
			return "";
	}

	public void addNewUser(String user_id){
		this.card_map.put(user_id,new HashMap<String,IDCard>());
    }

	public void addNewCards(String user_id, IDCard card){
		this.card_map.get(user_id).put(card.getCardId(),card);
    }
	
	public void deleteCards(String user_id, String card_id){
		this.card_map.get(user_id).remove(card_id);
	}

}

