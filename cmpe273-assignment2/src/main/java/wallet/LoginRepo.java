package wallet;

import java.util.*;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface LoginRepo extends MongoRepository<LoginCache, String> {


}