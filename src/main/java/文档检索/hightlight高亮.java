package 文档检索;

import Client.MyClient;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;

import java.io.IOException;

/***
 *@Author icepan
 *@Date 2020/8/17 下午7:31
 *@Description
 *
 ***/


public class hightlight高亮 {
    public static void main(String[] args) throws IOException {
        RestHighLevelClient client = MyClient.get();

        SearchRequest request = new SearchRequest("sms-logs");

        SearchSourceBuilder builder = new SearchSourceBuilder();

        //高亮
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.field("smsContent",10)
        .preTags("<front color='red'>")
        .postTags("</font>");

        builder.query(QueryBuilders.matchQuery("smsContent","孩子"));
        builder.highlighter(highlightBuilder);

        request.source(builder);

        SearchResponse response = client.search(request, RequestOptions.DEFAULT);

        for (SearchHit hit : response.getHits().getHits()) {
            System.out.println(hit.getHighlightFields().get("smsContent").fragments().length);
        }

        client.close();

    }
}
