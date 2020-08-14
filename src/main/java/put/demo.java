package put;

import org.apache.http.HttpHost;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;

/***
 *@Author icepan
 *@Date 2020/8/14 下午9:14
 *@Description
 *
 ***/


public class demo {

    public static void main(String[] args) throws IOException {

        RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(
                new HttpHost("localhost", 9200, "http")
        ));


        HashMap<String, Object> obj = new HashMap<String, Object>();
        obj.put("user","icepan");
        obj.put("postDate",new Date());
        obj.put("message","trying out Elasticsearch");
        IndexRequest posts = new IndexRequest("posts").id("3").source(obj);
        IndexResponse response = client.index(posts, RequestOptions.DEFAULT);
        System.out.println(response.toString());


        try {
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
