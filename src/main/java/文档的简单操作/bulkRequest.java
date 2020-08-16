package 文档的简单操作;

import Client.MyClient;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.elasticsearch.action.bulk.BulkItemResponse;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.common.xcontent.XContentType;
import 文档的简单操作.entity.Book;

import java.io.IOException;
import java.util.Date;

/***
 *@Author icepan
 *@Date 2020/8/16 下午12:52
 *@Description es的批量CRUD操作  操作一样
 *
 ***/


public class bulkRequest {
    public static void main(String[] args) throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        RestHighLevelClient client = MyClient.get();

        BulkRequest bulkRequest = new BulkRequest();



        for(int i=5;i<10;i++){
            Book book = new Book(i,"java"+i,"j"+i,100L,new Date(),"java desc"+i);
            String jsonBook = mapper.writeValueAsString(book);
            IndexRequest request = new IndexRequest("book");

            bulkRequest.add(request.id(book.getId().toString()).source(jsonBook, XContentType.JSON));
        }

        BulkResponse responses = client.bulk(bulkRequest, RequestOptions.DEFAULT);
        for (BulkItemResponse item : responses.getItems()) {
            System.out.println(item.getResponse().getResult());
        }

        client.close();
    }
}
