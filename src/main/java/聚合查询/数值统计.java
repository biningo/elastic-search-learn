package 聚合查询;

import Client.MyClient;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.AggregationPhase;
import org.elasticsearch.search.aggregations.metrics.*;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import java.io.IOException;

/***
 *@Author icepan
 *@Date 2020/8/22 下午5:28
 *@Description
 *
 ***/


public class 数值统计 {
    public static void main(String[] args) throws IOException {

        RestHighLevelClient client = MyClient.get();

        SearchRequest request = new SearchRequest("sms-logs");

        SearchSourceBuilder builder = new SearchSourceBuilder();

        //sum
        builder.aggregation(AggregationBuilders.sum("sum-fee").field("fee"));
        request.source(builder);
        SearchResponse response = client.search(request, RequestOptions.DEFAULT);
        Sum sum = response.getAggregations().get("sum-fee");
        System.out.println("sum:"+sum.getValue());

        //max
        builder.aggregation(AggregationBuilders.max("max-fee").field("fee"));
        request.source(builder);
        response = client.search(request,RequestOptions.DEFAULT);
        Max max = response.getAggregations().get("max-fee");
        System.out.println("max:"+max.getValue());


        client.close();
    }
}
