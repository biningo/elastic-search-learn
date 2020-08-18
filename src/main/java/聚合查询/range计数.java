package 聚合查询;

import Client.MyClient;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.range.Range;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import java.io.IOException;
import java.sql.SQLOutput;

/***
 *@Author icepan
 *@Date 2020/8/18 上午9:47
 *@Description
 *
 ***/


public class range计数 {
    public static void main(String[] args) throws IOException {

        RestHighLevelClient client = MyClient.get();

        SearchRequest request = new SearchRequest("sms-logs");

        SearchSourceBuilder builder = new SearchSourceBuilder();

        builder.aggregation(AggregationBuilders.range("count-range").field("fee")
                .addRange(50,100)
                .addUnboundedTo(10)
                .addUnboundedFrom(54)
        );

        request.source(builder);

        SearchResponse response = client.search(request, RequestOptions.DEFAULT);

        Range range = response.getAggregations().get("count-range");
        for (Range.Bucket bucket : range.getBuckets()) {
            System.out.println(String.format("[%s-%s):%d",bucket.getFrom(),bucket.getTo(),bucket.getDocCount()));
        }

        //date_range
        System.out.println("-------------------------------------");
        builder.aggregation(AggregationBuilders.dateRange("date-range").field("sendTime")
                    .format("yyyy")
                    .addUnboundedFrom("2020")
        );
        request.source(builder);

        response = client.search(request,RequestOptions.DEFAULT);
        range = response.getAggregations().get("date-range");
        for (Range.Bucket bucket : range.getBuckets()) {
            System.out.println(String.format("[%s-%s):%d",bucket.getFrom(),bucket.getTo(),bucket.getDocCount()));
        }


        //ip_range
        System.out.println("-----------------------------------------");
        builder.aggregation(AggregationBuilders.ipRange("ip-range")
                .field("ipAddr")
                .addRange("127.0.0.8","127.0.0.10")
        );
        request.source(builder);

        response = client.search(request,RequestOptions.DEFAULT);
        range = response.getAggregations().get("ip-range");
        for (Range.Bucket bucket : range.getBuckets()) {
            System.out.println(String.format("[%s-%s):%d",bucket.getFrom(),bucket.getTo(),bucket.getDocCount()));
        }


        client.close();

    }
}
