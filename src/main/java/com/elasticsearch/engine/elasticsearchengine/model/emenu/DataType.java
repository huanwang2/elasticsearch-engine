package com.elasticsearch.engine.elasticsearchengine.model.emenu;

import java.util.Objects;

/**
 * program: esdemo
 * description: es字段数据结构
 * author: X-Pacific zhang
 * create: 2019-01-25 16:58
 **/
public enum DataType {
    /**
     * 
     */
    keyword_type, text_type, byte_type, short_type, integer_type, long_type, float_type, double_type, boolean_type, date_type, nested_type, geo_point_type;


    public static DataType getDataTypeByStr(String str) {
        if (Objects.equals(str,"keyword")) {
            return keyword_type;
        } else if (Objects.equals(str,"text")) {
            return text_type;
        } else if (Objects.equals(str,"byte")) {
            return byte_type;
        } else if (Objects.equals(str,"short")) {
            return short_type;
        } else if (Objects.equals(str,"integer")) {
            return integer_type;
        } else if (Objects.equals(str,"long")) {
            return long_type;
        } else if (Objects.equals(str,"float")) {
            return float_type;
        } else if (Objects.equals(str,"double")) {
            return double_type;
        } else if (Objects.equals(str,"boolean")) {
            return boolean_type;
        } else if (Objects.equals(str,"date") || Objects.equals(str,"datetime")) {
            return date_type;
        } else {
            return text_type;
        }
    }
}
