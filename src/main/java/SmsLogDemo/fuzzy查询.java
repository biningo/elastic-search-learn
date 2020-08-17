package SmsLogDemo;

import Client.MyClient;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import java.io.IOException;

/***
 *@Author icepan
 *@Date 2020/8/17 上午10:32
 *@Description
 *
 ***/


public class fuzzy查询 {
    public static void main(String[] args) throws IOException {
        RestHighLevelClient client = MyClient.get();

        SearchRequest request = new SearchRequest("sms-logs");

        SearchSourceBuilder builder = new SearchSourceBuilder();

        builder.query(QueryBuilders.fuzzyQuery("smsContent","钟年人"));

        request.source(builder);

        SearchResponse response = client.search(request, RequestOptions.DEFAULT);


        for (SearchHit hit : response.getHits()) {
            System.out.println(hit.getSourceAsMap());
        }


        //指定profix_length
        System.out.println("-----------------------------------");
        builder.query(QueryBuilders.fuzzyQuery("smsContent","钟年人").prefixLength(1));

        request.source(builder);

        response = client.search(request,RequestOptions.DEFAULT);

        for (SearchHit hit : response.getHits()) {
            System.out.println(hit.getSourceAsMap());
        }

        client.close();
    }
}
