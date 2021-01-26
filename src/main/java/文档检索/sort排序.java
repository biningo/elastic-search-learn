package 文档检索;

import Client.MyClient;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;

import java.io.IOException;

/***
 *@Author icepan
 *@Date 2020/8/22 下午3:09
 *@Description
 *
 ***/


public class sort排序 {

    public static void main(String[] args) throws IOException {


        RestHighLevelClient client = MyClient.get();

        SearchRequest request = new SearchRequest("sms-logs");

        SearchSourceBuilder builder = new SearchSourceBuilder();

        builder.sort("createTime", SortOrder.DESC).query(QueryBuilders.matchAllQuery());
        builder.fetchSource(new String[]{"createTime","corpName"},null); //限制查询的字段

        request.source(builder);


        SearchResponse response = client.search(request, RequestOptions.DEFAULT);

        for (SearchHit hit : response.getHits().getHits()) {
            //如果指定的sort的字段，则不会计算socre
            System.out.println(hit.getSourceAsMap()+"  "+hit.getId()+" "+hit.getScore());
        }

        client.close();

    }

}
