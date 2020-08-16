package 文档的简单操作;

import Client.MyClient;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;

import java.io.IOException;

/***
 *@Author icepan
 *@Date 2020/8/16 上午11:03
 *@Description
 *
 ***/


public class delete {

    public static void main(String[] args) throws IOException {
        RestHighLevelClient client = MyClient.get();

        DeleteRequest request = new DeleteRequest("book");

        DeleteResponse response = client.delete(request.id("1"), RequestOptions.DEFAULT);

        System.out.println(response.getResult());

        client.close();

    }

}
