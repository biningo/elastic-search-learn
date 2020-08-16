package 文档的简单操作;

import Client.MyClient;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;

import java.io.IOException;
import java.util.HashMap;

/***
 *@Author icepan
 *@Date 2020/8/16 下午12:44
 *@Description
 *
 ***/


public class update {

    public static void main(String[] args) throws IOException {

        RestHighLevelClient client = MyClient.get();

        HashMap<String, Object> mapper = new HashMap<>();
        mapper.put("title","golang高级编程3");
        mapper.put("content","golang是一门非常nb的语言");


        UpdateRequest request = new UpdateRequest("book","2");
        request.doc(mapper);

        UpdateResponse response = client.update(request, RequestOptions.DEFAULT);

        System.out.println(response.getResult());

        client.close();
    }

}
