package wallet;
import java.util.*;

class BankAccountCache{
	private HashMap<String, HashMap<String,BankAccount>> bank_map;
	
	public BankAccountCache(){
		this.bank_map = new HashMap<String, HashMap<String,BankAccount>>(); 
	}

	public String getUserBanks(String user_id){
		if (this.bank_map.containsKey(user_id)){
			HashMap<String,BankAccount> temp = this.bank_map.get(user_id);
			String output = "{\n";
			for (BankAccount bank:temp.values()){
				output += bank.toString() + "\n";
			}
			output +="}\n";
			return output;
		}
		else
			return "";
	}

	public void addNewUser(String user_id){
		this.bank_map.put(user_id,new HashMap<String,BankAccount>());
    }

	public void addNewBankAccount(String user_id, BankAccount bankaccount){
		this.bank_map.get(user_id).put(bankaccount.getBaId(),bankaccount);
    }
	
	public void deleteBankAccount(String user_id, String ba_id){
		this.bank_map.get(user_id).remove(ba_id);
	}

	public int getMaxUserBankId(String user_id) {
		int max_bank_id = 100;
        if (this.bank_map.containsKey(user_id)){
			HashMap<String,BankAccount> temp = this.bank_map.get(user_id);
			for (BankAccount bank:temp.values()){
				if (bank.getInternalId() > max_bank_id) {
					max_bank_id = bank.getInternalId();
				}
			}
		}
		return max_bank_id;
	}

}

