package 文档的简单操作;

import Client.MyClient;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import 文档的简单操作.entity.Book;

import java.io.IOException;
import java.util.Date;

/***
 *@Author icepan
 *@Date 2020/8/16 上午11:03
 *@Description
 *
 ***/


public class create {

    public static void main(String[] args) throws IOException {

        RestHighLevelClient client = MyClient.get();

        ObjectMapper mapper = new ObjectMapper();

        Book book = new Book(2, "go并发编程", "icepan", 1000L, new Date(), "golang编程哈哈h哈哈哈");
        String bookJson = mapper.writeValueAsString(book);

        IndexRequest request = new IndexRequest("book");
        request.id(book.getId().toString()).source(bookJson, XContentType.JSON);

        IndexResponse response = client.index(request, RequestOptions.DEFAULT);

        System.out.println(response.getResult()); //CREATED UPDATED

        client.close();
    }

}
