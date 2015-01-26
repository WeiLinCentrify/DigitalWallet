package sample.simple;

import java.net.URI;
import java.util.Timer;
import java.util.TimerTask;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.common.util.concurrent.ListenableFuture;
import com.justinsb.etcd.EtcdClient;
import com.justinsb.etcd.EtcdClientException;
import com.justinsb.etcd.EtcdResult;

@RestController
@EnableAutoConfiguration
@RequestMapping(value = "/api/v1")
public class HelloController   {

    EtcdClient client = new EtcdClient(URI.create("http://54.183.65.171:4001/"));
    static final String key = "010048168/counter";

    @RequestMapping(value = "/counter", method = RequestMethod.GET)
    public String counter() throws EtcdClientException {
        if (client.get(key) == null) {
            client.set(key, "0");
        }
        return IncrementAndGetCounterValue();
    }
   @RequestMapping(value = "/", method = RequestMethod.GET)
   public String checkHealth() {
   	return "System up";
   }
   private String IncrementAndGetCounterValue() throws EtcdClientException {
        EtcdResult result = client.get(key);
        String counterValue = result.node.value;
        int counterIntValue = Integer.parseInt(counterValue);
        counterValue = Integer.toString(++counterIntValue);
        client.set(key, counterValue);
        return counterValue;
    }
}
