package SmsLogDemo.entity;

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
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.common.xcontent.json.JsonXContent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/***
 *@Author icepan
 *@Date 2020/8/16 下午4:02
 *@Description
 *
 ***/


public class CreateData {


    public static void createIndex() throws IOException {
        // 1.准备关于索引的setting
        Settings.Builder settings = Settings.builder()
                .put("number_of_shards", 1)
                .put("number_of_replicas", 1);

        // 2.准备关于索引的mapping
        //创建时间
        HashMap<String, Object> createTime = new HashMap<>();
        createTime.put("type", "date");
        createTime.put("format", "yyyy-MM-dd");

        //发送时间
        HashMap<String, Object> sendTime = new HashMap<>();
        sendTime.put("type", "date");
        sendTime.put("format", "yyyy-MM-dd");

        //发送的长号码
        HashMap<String, Object> longCode = new HashMap<>();
        longCode.put("type", "keyword");

        //手机号
        HashMap<String, Object> mobile = new HashMap<>();
        mobile.put("type", "keyword");

        //公司
        HashMap<String, Object> corpName = new HashMap<>();
        corpName.put("type", "text");
        corpName.put("analyzer", "ik_max_word");

        //短信内容
        HashMap<String, Object> smsContent = new HashMap<>();
        smsContent.put("type", "text");
        smsContent.put("analyzer", "ik_max_word");

        //信息发送状态 0 1 是否成功
        HashMap<String, Object> state = new HashMap<>();
        state.put("type", "boolean");

        //运营商编号 1移动2联通3电信
        HashMap<String, Object> operatorId = new HashMap<>();
        operatorId.put("type", "integer");

        //省份
        HashMap<String, Object> province = new HashMap<>();
        province.put("type", "keyword");

        //下发服务器地址
        HashMap<String, Object> ipAddr = new HashMap<>();
        ipAddr.put("type", "ip");

        //短信报告返回时长 （s）
        HashMap<String, Object> replyTotal = new HashMap<>();
        replyTotal.put("type", "integer");

        //扣费（分）
        HashMap<String, Object> fee = new HashMap<>();
        fee.put("type", "integer");


        HashMap<String, Object> properties = new HashMap<>();
        properties.put("createTime", createTime);
        properties.put("sendTime", sendTime);
        properties.put("longCode", longCode);
        properties.put("mobile", mobile);
        properties.put("corpName", corpName);
        properties.put("smsContent", smsContent);
        properties.put("state", state);
        properties.put("operatorId", operatorId);
        properties.put("province", province);
        properties.put("ipAddr", ipAddr);
        properties.put("replyTotal", replyTotal);
        properties.put("fee", fee);

        HashMap<String, Object> mappings = new HashMap<>();
        mappings.put("properties", properties);

        CreateIndexRequest request = new CreateIndexRequest("sms-logs")
                .settings(settings)
                .mapping(mappings);

        RestHighLevelClient client = MyClient.get();
        CreateIndexResponse response = client.indices().create(request, RequestOptions.DEFAULT);
        System.out.println(response.index().toString());
        client.close();

    }

    public static void insertData() throws InterruptedException, IOException {

        ObjectMapper mapper = new ObjectMapper();

        // 1.准备多个json 对象
        String longcode = "1008687";
        String mobile = "138340658";
        List<String> companies = new ArrayList<>();
        companies.add("腾讯课堂");
        companies.add("阿里旺旺");
        companies.add("海尔电器");
        companies.add("海尔智家公司");
        companies.add("格力汽车");
        companies.add("苏宁易购");
        List<String> provinces = new ArrayList<>();
        provinces.add("北京");
        provinces.add("重庆");
        provinces.add("上海");
        provinces.add("晋城");

        BulkRequest bulkRequest = new BulkRequest();
        for (int i = 1; i < 16; i++) {
            Thread.sleep(1000);
            SmsLogs s1 = new SmsLogs();
            s1.setId(i);
            s1.setCreateTime(new Date());
            s1.setSendTime(new Date());
            s1.setLongCode(longcode + i);
            s1.setMobile(mobile + 2 * i);
            s1.setCorpName(companies.get(i % 5));
            s1.setSmsContent(SmsLogs.doc.substring((i - 1) * 100, i * 100));
            s1.setState(i % 2 == 0 ? false : true);
            s1.setOperatorId(i % 3);
            s1.setProvince(provinces.get(i % 4));
            s1.setIpAddr("127.0.0." + i);
            s1.setReplyTotal(i * 3);
            s1.setFee(i * 6 + "");
            String json1 = mapper.writeValueAsString(s1);

            bulkRequest.add(
                    new IndexRequest("sms-logs")
                            .id(s1.getId().toString())
                            .source(json1, XContentType.JSON)
            );

            System.out.println("数据" + i + s1.toString());
        }

        // 3.client 执行
        RestHighLevelClient client = MyClient.get();
        BulkResponse responses = client.bulk(bulkRequest, RequestOptions.DEFAULT);
        // 4.输出结果
        for (BulkItemResponse item : responses.getItems()) {
            System.out.println(item.getId()+"】:"+item.getResponse().getResult());
        }

    }


    public static void main(String[] args) throws IOException, InterruptedException {
        createIndex();
        insertData();
    }


}
