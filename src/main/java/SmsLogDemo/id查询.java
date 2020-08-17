package SmsLogDemo;

import Client.MyClient;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import java.io.IOException;

/***
 *@Author icepan
 *@Date 2020/8/17 上午9:59
 *@Description
 *
 ***/


public class id查询 {
    public static void main(String[] args) throws IOException {

        RestHighLevelClient client = MyClient.get();

        //1、ID查询
        GetRequest request = new GetRequest("sms-logs", "1");

        GetResponse response = client.get(request, RequestOptions.DEFAULT);

        System.out.println(response.getSource()); //返回map


        //2、IDs查询 多id查询
        System.out.println("----------------------------------------------");
        SearchRequest searchRequest = new SearchRequest("sms-logs");

        SearchSourceBuilder builder = new SearchSourceBuilder();
        builder.query(QueryBuilders.idsQuery().addIds("1", "2", "3"));

        searchRequest.source(builder);

        SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);

        for (SearchHit hit : searchResponse.getHits()) {
            System.out.println(hit.getId());
            System.out.println(hit.getSourceAsMap());
        }


        client.close();

    }
}
