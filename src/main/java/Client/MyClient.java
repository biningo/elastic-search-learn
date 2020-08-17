package Client;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;

/***
 *@Author icepan
 *@Date 2020/8/16 上午10:21
 *@Description
 *
 ***/


public class MyClient {
    public static RestHighLevelClient get(){
        return new RestHighLevelClient(RestClient.builder(
                new HttpHost("192.168.0.104", 9200, "http")
        ));
    }
}
