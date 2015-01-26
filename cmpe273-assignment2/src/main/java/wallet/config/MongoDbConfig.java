package wallet.config;
 
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import com.mongodb.Mongo;
import com.mongodb.MongoURI;
 
@Profile("default")
@Configuration
@EnableMongoRepositories(basePackages = "wallet")
public class MongoDbConfig extends AbstractMongoConfiguration {
 
 @Value("mongodb://cmpe273-wallet:cmpe273@ds047030.mongolab.com:47030/cmpe273-wallet")
 private String url;
 
 @Value("cmpe273-wallet")
 private String databaseName;
 
 @Override
 protected String getDatabaseName() {
  return databaseName;
 }
 
 @Override
 public Mongo mongo() throws Exception {
  return new Mongo(new MongoURI(url));
 }
 
}
