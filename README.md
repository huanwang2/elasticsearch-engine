# elasticsearch-engine

## 介绍

elasticsearch-engine是基于 HighLevelRestClient 封装的 ElasticSearch 查询引擎框架. 支持ElasticSearch基于注解的结构化查询; 基于sql语句的方式查询;
并整合常见的ORM框架, 提供基于ORM框架的Mapper接口自动生成ElasticSearch Sql查询语句,并执行ElasticSearch查询;

在需要查询 ElasticSearch 的Mapper接口标注一个注解即可实现 ElasticSearch 查询,无需额外的代码开发; 并可以通过配置中心配置动态切换ElasticSearch和Mysql之间的查询,
实现ElasticSearch查询降级.

## 主要功能特性

1. 基于注解的方式实现elasticsearch的查询

2. 基于sql语句的方式实现elasticsearch的查询

3. 基于mybatis mapper接口 自动生成elasticsearch查询,并支持数据库回表查询

4. 基于jpa repository接口 自动生成elasticsearch查询,并支持数据库回表查询

5. 基于jooq dao实现类 自动生成elasticsearch查询,并支持数据库回表查询

## 架构模块

1. elasticsearch-engine-base 提供注解查询,sql语句查询,ORM查询sql解析,sql改写等基础功能
2. elasticsearch-engine-mybatis 基于mybatis拦截器 实现sql拦截,改写,执行elasticsearch查询
3. elasticsearch-engine-jpa 基于aop,hibernate sql拦截器以及重新jpa参数绑定模块 实现sql拦截,改写,执行elasticsearch查询
4. elasticsearch-engine-jooq 基于aop,jooq执行监听器 实现sql拦截,改写,执行elasticsearch查询

## 使用说明

### 1.注解查询

#### 1.1复杂参数

1)添加maven依赖

```java
<dependency>
<groupId>com.elasticsearch.engine</groupId>
<artifactId>elasticsearch-engine-base</artifactId>
<version>0.0.1-SNAPSHOT</version>
</dependency>
```

2)定义查询model

```java
package com.elasticsearch.engine.demo.dto.query;

import com.elasticsearch.engine.base.mapping.annotation.*;
import com.elasticsearch.engine.base.mapping.model.extend.PageParam;
import com.elasticsearch.engine.base.mapping.model.extend.RangeParam;
import com.elasticsearch.engine.base.mapping.model.extend.SignParam;
import com.elasticsearch.engine.base.model.annotion.Base;
import com.elasticsearch.engine.base.model.annotion.EsQueryIndex;
import com.elasticsearch.engine.base.model.annotion.Ignore;
import com.elasticsearch.engine.base.model.emenu.EsConnector;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author wanghuan
 * @description: 解析查询注解基础测试
 * @mail 958721894@qq.com
 * @date 2022-05-31 22:40
 */
@EsQueryIndex(value = "person_es_index")
@Data
public class PersonBaseQuery {

    @Term
    private BigDecimal salary;

    @Terms(value = @Base("item_no"))
    private List<String> personNos;

    @Terms
    private List<String> personNoList;

    @Range(value = @Base(value = "status", connect = EsConnector.SHOULD), tag = Range.LE_GE)
    private RangeParam rangeStatus;

    @Range
    private RangeParam createTime;

    @WildCard
    private String address;

    @Prefix
    private String personName;

    @To(@Base("create_time"))
    private LocalDateTime createTimeEnd;

    @From(value = @Base("create_time"))
    private LocalDateTime createTimeStart;

    @PageAndOrder
    private PageParam pageParam;

    /**
     * 标记注解不解析value,只解析注解值
     * 需要设置 value值不为空,查询条件才会生效, 但是设置的value不会被解析,仅仅标记是否添加该条件
     * 所以value可以任意设置, 但是注意 string 不能为空串,数组类型不能为null
     *
     * SignParam 表示一种无需解析参数值得 类型
     * 也可以使用 Sign.DEFAULT_STRING 表示
     */
    @Sort
    private SignParam sortStatus;

    @Aggs(value = @Base("status"), type = Aggs.COUNT_DESC)
    private SignParam groupStatus;

    /**
     * 表示忽略某个字段 ,被忽略的字段 无论属性值是否为空, 查询时都不会被解析
     */
    @Ignore
    private String token;

}
```

3)声明查询接口

```java

@EsQueryIndex("person_es_index")
public interface PersonEsModelRepository extends BaseESRepository<PersonEsEntity, Long> {
    /**
     * queryByMode
     *
     * @param param
     * @return
     */
    List<PersonEsEntity> queryByMode(PersonBaseQuery param);
}

```

4)测试示例

```java
/**
 * model查询测试
 */
@Test
public void queryByModelTest(){
        PersonBaseQuery person=new PersonBaseQuery();
        person.setPageParam(PageParam.builderPage().currentPage(1).pageSize(100).build());
        person.setSalary(new BigDecimal("67700"));
        person.setPersonName("张");
        person.setAddress("天府");
        person.setCreateTimeStart(LocalDateTime.now().minusDays(300));
        person.setCreateTimeEnd(LocalDateTime.now());
        List<PersonEsEntity> res=personEsModelRepository.queryByMode(person);
        log.info("res:{}",JsonParser.asJson(res));
        }
```

5)查询效果

```json
{
  "from": 0,
  "size": 100,
  "timeout": "10s",
  "query": {
    "bool": {
      "filter": [
        {
          "wildcard": {
            "address": {
              "wildcard": "*天府*"
            }
          }
        },
        {
          "prefix": {
            "personName": {
              "value": "张"
            }
          }
        },
        {
          "term": {
            "salary": {
              "value": 67700
            }
          }
        },
        {
          "range": {
            "create_time": {
              "from": "2021-08-23T21:17:23.385Z",
              "to": "2022-06-19T21:17:23.385Z",
              "include_lower": true,
              "include_upper": true,
              "time_zone": "+08:00",
              "format": "8uuuu-MM-dd'T'HH:mm:ss.SSS'Z'"
            }
          }
        }
      ]
    }
  }
}
```

#### 1.2简单参数

1)声明查询接口

```java

@EsQueryIndex(value = "person_es_index")
public interface PersonEsParamRepository extends BaseESRepository<PersonEsEntity, Long> {
    /**
     * List查询
     *
     * @return
     */
    List<PersonEsEntity> queryList(@Terms List<String> personNoList);
}
```

2)测试示例

```java

/**
 * List查询测试
 */
@Test
public void queryListResponse(){
        List<String> personNoList=Lists.newArrayList("US2022060100001","US2022060100002");
        List<PersonEsEntity> res=personEsParamRepository.queryList(personNoList);
        log.info("res:{}",JsonParser.asJson(res));
        }

```

3) 查询效果

```json
{
  "size": 1000,
  "timeout": "10s",
  "query": {
    "bool": {
      "filter": [
        {
          "terms": {
            "personNo": [
              "US2022060100001",
              "US2022060100002"
            ]
          }
        }
      ]
    }
  }
}

```

### 2.sql查询

### 3.扩展查询

#### 3.1 mybatis

#### 3.2 jpa

#### 3.3 jooq

## 使用示例

https://gitee.com/my-source-project/elasticsearch-engine-demo

## 相关文档

待补全...
