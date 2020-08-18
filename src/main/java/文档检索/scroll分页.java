package 文档检索;

import Client.MyClient;
import org.elasticsearch.action.search.*;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;

import java.io.IOException;

/***
 *@Author icepan
 *@Date 2020/8/17 下午3:32
 *@Description
 *
 ***/


public class scroll分页 {
    public static void main(String[] args) throws IOException {

        RestHighLevelClient client = MyClient.get();

        SearchRequest request = new SearchRequest();
        request.scroll(TimeValue.timeValueHours(1L));

        SearchSourceBuilder builder = new SearchSourceBuilder();
        builder.size(7);
        builder.sort("fee", SortOrder.DESC);
        builder.query(QueryBuilders.matchAllQuery());

        request.source(builder);

        SearchResponse response = client.search(request, RequestOptions.DEFAULT);

        System.out.println("-----------------第一页------------------");
        for (SearchHit hit : response.getHits().getHits()) {
            System.out.println(hit.getSourceAsMap());
        }

        String scrollId = response.getScrollId();

        while (true) {

            SearchScrollRequest scrollRequest = new SearchScrollRequest(scrollId);
            //指定存活时间  不指定的话查完就会被删除
            scrollRequest.scroll(TimeValue.timeValueHours(1));

            response = client.scroll(scrollRequest, RequestOptions.DEFAULT);

            SearchHit[] hits = response.getHits().getHits();
            if (hits != null && hits.length > 0) {
                System.out.println("------------------------------下一页----------------");
                for (SearchHit hit : hits) {
                    System.out.println(hit.getSourceAsMap());
                }
            } else {
                System.out.println("------------------------结束---------------------");
                break;
            }

        }

        //清除scroll
        ClearScrollRequest clearScrollRequest = new ClearScrollRequest();
        clearScrollRequest.addScrollId(scrollId);

        ClearScrollResponse clearScrollResponse = client.clearScroll(clearScrollRequest, RequestOptions.DEFAULT);
        System.out.println(clearScrollResponse.getNumFreed());


        client.close();

    }
}
