package 聚合查询;

import Client.MyClient;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.range.Range;
import org.elasticsearch.search.aggregations.metrics.ExtendedStats;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import java.io.IOException;

/***
 *@Author icepan
 *@Date 2020/8/18 上午10:38
 *@Description
 *
 ***/


public class extended_stats数值统计 {
    public static void main(String[] args) throws IOException {

        RestHighLevelClient client = MyClient.get();

        SearchRequest request = new SearchRequest("sms-logs");

        SearchSourceBuilder builder = new SearchSourceBuilder();

        builder.aggregation(AggregationBuilders.extendedStats("num-statis").field("fee"));

        request.source(builder);

        SearchResponse response = client.search(request, RequestOptions.DEFAULT);

        ExtendedStats stats = response.getAggregations().get("num-statis");

        System.out.println(String.format("max:%f min:%f avg:%f sum:%f",stats.getMax(),stats.getMin(),stats.getAvg(),stats.getSum()));

        client.close();

    }
}
