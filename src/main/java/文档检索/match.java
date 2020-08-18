package 文档检索;

import Client.MyClient;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import java.io.IOException;

/***
 *@Author icepan
 *@Date 2020/8/16 下午8:21
 *@Description
 *
 ***/


public class match {
    public static void main(String[] args) throws IOException {

        ObjectMapper mapper = new ObjectMapper();
        RestHighLevelClient client = MyClient.get();

        SearchRequest request = new SearchRequest("sms-logs");

        SearchSourceBuilder builder = new SearchSourceBuilder();

//---------------------------------match_all-------------------
        builder.query(QueryBuilders.matchAllQuery());
        builder.from(0).size(20); //默认10条  设置20条

        request.source(builder);

        SearchResponse response = client.search(request, RequestOptions.DEFAULT);
        for (SearchHit hit : response.getHits()) {
            System.out.println(hit.getSourceAsMap());
        }

//-------------------------------match-----------------------------
        System.out.println("-----------------------------------------------");
        builder.query(QueryBuilders.matchQuery("smsContent", "居民那吧"));

        request.source(builder);
        response = client.search(request, RequestOptions.DEFAULT);
        for (SearchHit hit : response.getHits()) {
            System.out.println(hit.getSourceAsMap());
        }
//-----------------------------布尔match---------------------------
        System.out.println("------------------------------");
        builder.query(QueryBuilders.matchQuery("smsContent", "那 小镇").operator(Operator.OR));
        request.source(builder);

        response = client.search(request, RequestOptions.DEFAULT);
        for (SearchHit hit : response.getHits()) {
            System.out.println(hit.getSourceAsMap());
        }


//--------------------------------multil_match-------------
        System.out.println("---------------------------------");
        builder.query(QueryBuilders.multiMatchQuery("北京", "smsContent", "province"));
        request.source(builder);
        response = client.search(request, RequestOptions.DEFAULT);
        for (SearchHit hit : response.getHits()) {
            System.out.println(hit.getSourceAsMap());
        }

        client.close();

    }
}
