package SmsLogDemo;

import Client.MyClient;
import org.elasticsearch.action.index.IndexRequest;
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
 *@Date 2020/8/16 下午5:02
 *@Description term关键字精确查询 不对查询关键字分词
 *
 ***/


public class term {
    public static void main(String[] args) throws IOException {

        Integer PAGE_SIZE=5;
        Integer PAGE = 1;


        RestHighLevelClient client = MyClient.get();

        SearchRequest request = new SearchRequest("sms-logs");

        SearchSourceBuilder builder = new SearchSourceBuilder();
        builder.from((PAGE-1)*PAGE_SIZE);
        builder.size(PAGE_SIZE);
        builder.query(QueryBuilders.termQuery("province","上海"));

        request.source(builder);

        SearchResponse response = client.search(request, RequestOptions.DEFAULT);

        for (SearchHit hit : response.getHits()) {
            System.out.println(hit.getSourceAsMap());
        }

//------------------------------------terms---------------------------------------
        System.out.println("---------------------terms-----------------------------------");
        builder.from((PAGE-1)*PAGE_SIZE);
        builder.size(PAGE_SIZE);
        builder.query(QueryBuilders.termsQuery("province","上海","北京"));
        request.source(builder);
        response = client.search(request,RequestOptions.DEFAULT);
        for (SearchHit hit : response.getHits()) {
            System.out.println(hit.getSourceAsMap());
        }

        client.close();

    }
}
