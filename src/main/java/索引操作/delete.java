package 索引操作;

import Client.MyClient;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;

import java.io.IOException;

/***
 *@Author icepan
 *@Date 2020/8/16 上午10:51
 *@Description
 *
 ***/


public class delete {
    public static void main(String[] args) throws IOException {
        RestHighLevelClient client = MyClient.get();

        DeleteIndexRequest request = new DeleteIndexRequest("student");
        client.indices().delete(request, RequestOptions.DEFAULT);

        client.close();

    }
}
