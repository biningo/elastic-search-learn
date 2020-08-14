package get;

import org.apache.http.HttpHost;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.time.DateUtils;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/***
 *@Author icepan
 *@Date 2020/8/14 下午10:00
 *@Description
 *
 ***/


public class demo1 {
    public static void main(String[] args) throws IOException {

        RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(
                new HttpHost("localhost", 9200, "http")
        ));

        GetRequest request = new GetRequest("posts","1");
        GetResponse response = client.get(request, RequestOptions.DEFAULT);
        System.out.println(response);




        client.close();
    }
}
