package 索引操作;

import Client.MyClient;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.client.indices.GetIndexResponse;

import java.io.IOException;

/***
 *@Author icepan
 *@Date 2020/8/16 上午10:53
 *@Description
 *
 ***/


public class get {
    public static void main(String[] args) throws IOException {
        RestHighLevelClient client = MyClient.get();

        GetIndexRequest request = new GetIndexRequest("book");

        boolean exists = client.indices().exists(request, RequestOptions.DEFAULT);
        System.out.println(exists);
        if(exists){
            GetIndexResponse response = client.indices().get(request, RequestOptions.DEFAULT);
            response.getMappings().forEach((i,p)-> System.out.println(i+"】:"+p.getSourceAsMap()));
            response.getSettings().forEach((i,p)-> System.out.println(i+"】:"+p.getAsGroups().get("index") ));
        }

        client.close();
    }
}
