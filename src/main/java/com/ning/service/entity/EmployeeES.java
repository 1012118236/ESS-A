package com.ning.service.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
//import org.springframework.data.elasticsearch.annotations.Document;
//import org.springframework.data.elasticsearch.annotations.Field;
//import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;

/**
 * @author shenjiang
 * @Description: es文章
 * @Date: 2019/7/25 14:27
 */
//@Document(indexName = "company",type = "article")
public class EmployeeES implements Serializable {

    @Id
    private String id;
    //专栏ID
    private String columnid;
    //用户名
    private String username;
    //标题
    //@Field(index = true,analyzer = "ik_max_word",searchAnalyzer = "ik_max_word")
    private String title;
    //正文
    //@Field(index = true,analyzer = "ik_max_word",searchAnalyzer = "ik_max_word",type=FieldType.Text)
    private String content;

    private String url;

    public EmployeeES() {
    }

    public EmployeeES(String id, String columnid, String username, String title, String content, String url) {
        this.id = id;
        this.columnid = columnid;
        this.username = username;
        this.title = title;
        this.content = content;
        this.url = url;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setColumnid(String columnid) {
        this.columnid = columnid;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getId() {
        return id;
    }

    public String getColumnid() {
        return columnid;
    }

    public String getUsername() {
        return username;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getUrl() {
        return url;
    }
}
