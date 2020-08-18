package 文档检索;

import Client.MyClient;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.reindex.BulkByScrollResponse;
import org.elasticsearch.index.reindex.DeleteByQueryRequest;

import java.io.IOException;

/***
 *@Author icepan
 *@Date 2020/8/17 下午4:02
 *@Description
 *
 ***/


public class delete_by_query {
    public static void main(String[] args) throws IOException {

        RestHighLevelClient client = MyClient.get();

        DeleteByQueryRequest request = new DeleteByQueryRequest("sms-logs");

        request.setQuery(QueryBuilders.rangeQuery("fee").gte(70).lte(90));

        BulkByScrollResponse response = client.deleteByQuery(request, RequestOptions.DEFAULT);

        System.out.println(response.getTotal());

        client.close();
    }
}
