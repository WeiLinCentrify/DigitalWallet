package wallet;

import java.util.*;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface BankAccountRepo extends MongoRepository<BankAccountCache, String> {

  
}