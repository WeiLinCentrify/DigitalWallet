package wallet;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepo extends MongoRepository<User, String> {

    public User findByUserId(String userId);

    List <User> deleteByUserId(String userId);
}
