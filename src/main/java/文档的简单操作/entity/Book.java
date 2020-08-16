package 文档的简单操作.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/***
 *@Author icepan
 *@Date 2020/8/16 上午11:24
 *@Description
 *
 ***/

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Book {

    @JsonIgnore
    private Integer id;

    private String title;
    private String author;
    private Long count;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date time;

    private String desc;


}

