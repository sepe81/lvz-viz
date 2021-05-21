package de.codefor.le.model;

import java.time.Instant;

import org.elasticsearch.common.geo.GeoPoint;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import lombok.Data;

@Data
@Document(indexName = "policeticker-index")
public class PoliceTicker {

    @Id
    private String id;

    @Field(type = FieldType.Text, index = false)
    private String url;

    @Field(type = FieldType.Text)
    private String title;

    @Field(type = FieldType.Text)
    private String article;

    @Field(type = FieldType.Text, index = false)
    private String snippet;

    @Field(type = FieldType.Text, index = false)
    private String copyright;

    @Field(type = FieldType.Date, format = DateFormat.basic_date_time)
    private Instant datePublished;

    private GeoPoint coords;

}
