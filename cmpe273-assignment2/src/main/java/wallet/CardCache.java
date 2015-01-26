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
	public int getMaxUserCardId(String user_id) {
		int max_card_id = 100;
        if (this.card_map.containsKey(user_id)){
			HashMap<String,IDCard> temp = this.card_map.get(user_id);
			for (IDCard card:temp.values()){
				if (card.getInternalId() > max_card_id) {
					max_card_id = card.getInternalId();
				}
			}
		}
		return max_card_id;
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
	public String toString() {
		return "Card Cache";
	}

}

