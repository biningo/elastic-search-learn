package search;

import org.apache.http.HttpHost;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import java.io.IOException;

/***
 *@Author icepan
 *@Date 2020/8/14 下午10:19
 *@Description
 *
 ***/


public class demo1 {

    public static void main(String[] args) throws IOException {
        RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(
                new HttpHost("localhost", 9200, "http")
        ));
        SearchSourceBuilder builder = new SearchSourceBuilder();
        builder.query(QueryBuilders.matchAllQuery());
        SearchRequest request = new SearchRequest("posts");
        request.source(builder);
        SearchResponse response = client.search(request, RequestOptions.DEFAULT);

        for (SearchHit hit : response.getHits()) {
            System.out.println(hit.getSourceAsMap());
        }

        builder.query(QueryBuilders.matchQuery("user","kobe"));
        request.source(builder);
        response = client.search(request,RequestOptions.DEFAULT);
        response.getHits().forEach(s-> System.out.println(s.getSourceAsMap()));

        client.close();


    }
}
