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
 *@Date 2020/8/17 下午7:11
 *@Description
 *
 ***/


public class filter过滤器查询 {
    public static void main(String[] args) throws IOException {

        RestHighLevelClient client = MyClient.get();

        SearchRequest request = new SearchRequest("sms-logs");

        SearchSourceBuilder builder = new SearchSourceBuilder();


        builder.query(QueryBuilders.
                boolQuery()
                    .filter(
                        QueryBuilders.termQuery("province", "上海")
                    )
                    .filter(
                        QueryBuilders.rangeQuery("fee").lt(50)
                    ));

        request.source(builder);

        SearchResponse response = client.search(request, RequestOptions.DEFAULT);

        for (SearchHit hit : response.getHits().getHits()) {
            System.out.println(hit.getSourceAsMap());
        }

        client.close();

    }
}
