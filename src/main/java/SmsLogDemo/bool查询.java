package SmsLogDemo;

import Client.MyClient;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import java.io.IOException;

/***
 *@Author icepan
 *@Date 2020/8/17 下午6:28
 *@Description
 *
 ***/


public class bool查询 {
    public static void main(String[] args) throws IOException {

        RestHighLevelClient client = MyClient.get();

        SearchRequest request = new SearchRequest("sms-logs");

        SearchSourceBuilder builder = new SearchSourceBuilder();

        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
        boolQuery.must(QueryBuilders.termQuery("province","上海"));
        boolQuery.mustNot(QueryBuilders.termQuery("corpName","电器"));

        builder.query(boolQuery);

        request.source(builder);

        SearchResponse response = client.search(request, RequestOptions.DEFAULT);

        for (SearchHit hit : response.getHits().getHits()) {
            System.out.println(hit.getSourceAsMap());
        }

        client.close();
    }
}
