package 聚合查询;

import Client.MyClient;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/***
 *@Author icepan
 *@Date 2020/8/28 下午2:59
 *@Description
 *
 ***/


public class terms聚合 {

    public static void main(String[] args) throws IOException {

        RestHighLevelClient client = MyClient.get();

        SearchRequest request = new SearchRequest("sms-logs");

        SearchSourceBuilder builder = new SearchSourceBuilder();

        builder.aggregation(
                AggregationBuilders
                        .terms("province-count")
                        .field("province")
        );
        request.source(builder);


        SearchResponse response = client.search(request, RequestOptions.DEFAULT);

        Terms terms =  response.getAggregations().get("province-count");
        List<? extends Terms.Bucket> buckets = terms.getBuckets();
        String result[][] = new String[buckets.size()][2];
        for(int i=0;i<buckets.size();i++){
            result[i][0] = (String) buckets.get(i).getKey();
            result[i][1] = String.valueOf(buckets.get(i).getDocCount());
        }


        for(int i=0;i<result.length;i++){
            System.out.println(result[i][0]+":"+result[i][1]);
        }

        client.close();


    }


}
