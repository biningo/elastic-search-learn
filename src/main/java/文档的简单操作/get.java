package 文档的简单操作;

import Client.MyClient;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;

import java.io.IOException;
import java.util.HashMap;

/***
 *@Author icepan
 *@Date 2020/8/16 上午11:03
 *@Description
 *
 ***/


public class get {
    public static void main(String[] args) throws IOException {
        RestHighLevelClient client = MyClient.get();

        GetRequest request = new GetRequest("book");

        GetResponse response = client.get(request.id("2"), RequestOptions.DEFAULT);

        System.out.println(response.getSourceAsMap());

        client.close();
    }
}
