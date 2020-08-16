package 索引操作;

import Client.MyClient;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.index.search.SimpleQueryStringQueryParser;

import java.io.IOException;
import java.util.HashMap;

/***
 *@Author icepan
 *@Date 2020/8/16 上午10:36
 *@Description
 *
 ***/


public class create {
    public static void main(String[] args) throws IOException {
        RestHighLevelClient client = MyClient.get();

        Settings.Builder settings = Settings.builder()
                .put("number_of_shards", 5)
                .put("number_of_replicas", 1);


        HashMap<String,Object> username = new HashMap<>();
        username.put("type","keyword");
        HashMap<String,Object> age = new HashMap<>();
        age.put("type","long");
        HashMap<String,Object> desc = new HashMap<>();
        desc.put("type","text");
        desc.put("analyzer","ik_max_word");


        HashMap<String,Object> properties = new HashMap<>();
        properties.put("username",username);
        properties.put("age",age);
        properties.put("desc",desc);

        HashMap<String,Object> mappings = new HashMap<>();
        mappings.put("properties",properties);

        CreateIndexRequest request = new CreateIndexRequest("student2");
        request.mapping(mappings);
        request.settings(settings);


        CreateIndexResponse response = client.indices().create(request, RequestOptions.DEFAULT);
        client.close();
    }
}
