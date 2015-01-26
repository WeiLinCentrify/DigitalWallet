package wallet;

import java.util.*;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface IDCardRepo extends MongoRepository<CardCache, String> {


   // public BasicDBObject(HashMap) findByUserId(String user_id);

}