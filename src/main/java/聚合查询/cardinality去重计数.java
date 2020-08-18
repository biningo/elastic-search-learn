package 聚合查询;

import Client.MyClient;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.metrics.Cardinality;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import java.io.IOException;

/***
 *@Author icepan
 *@Date 2020/8/18 上午9:32
 *@Description
 *
 ***/


public class cardinality去重计数 {

    public static void main(String[] args) throws Exception {

        RestHighLevelClient client = MyClient.get();

        SearchRequest request = new SearchRequest("sms-logs");

        SearchSourceBuilder builder = new SearchSourceBuilder();

        builder.aggregation(AggregationBuilders.cardinality("count-province").field("province"));

        request.source(builder);

        SearchResponse response = client.search(request, RequestOptions.DEFAULT);

        //向下转型
        Cardinality cardinality = response.getAggregations().get("count-province");
        System.out.println(cardinality.getValue());


        client.close();
    }

}
