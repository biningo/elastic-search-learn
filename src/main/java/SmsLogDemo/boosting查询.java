package SmsLogDemo;

import Client.MyClient;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import java.io.IOException;

/***
 *@Author icepan
 *@Date 2020/8/17 下午6:53
 *@Description
 *
 ***/


public class boosting查询 {
    public static void main(String[] args) throws IOException {

        RestHighLevelClient client = MyClient.get();

        SearchRequest request = new SearchRequest("sms-logs");

        SearchSourceBuilder builder = new SearchSourceBuilder();

        TermQueryBuilder positiveQuery = QueryBuilders.termQuery("province", "上海");
        MatchQueryBuilder negativeQuery = QueryBuilders.matchQuery("corpName", "海尔电器");

        builder.query(QueryBuilders.boostingQuery(positiveQuery,negativeQuery).negativeBoost(0.5F));

        request.source(builder);

        SearchResponse response = client.search(request, RequestOptions.DEFAULT);

        for (SearchHit hit : response.getHits().getHits()) {
            System.out.println(hit.getScore());
            System.out.println(hit.getSourceAsMap());
        }

        client.close();
    }
}
