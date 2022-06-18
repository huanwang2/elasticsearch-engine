# elasticsearch-engine

#### 介绍
elasticsearch-engine是基于 HighLevelRestClient 封装的 ElasticSearch 查询引擎框架. 支持ElasticSearch基于注解的结构化查询; 基于sql语句的方式查询; 并整合常见的ORM框架, 提供基于ORM框架的Mapper接口自动生成ElasticSearch Sql查询语句,并执行ElasticSearch查询;
在需要查询 ElasticSearch 的Mapper接口标注一个注解即可实现 ElasticSearch 查询,无需额外的代码开发; 并可以通过配置中心配置动态切换ElasticSearch和Mysql之间的查询, 实现ElasticSearch查询降级.

#### 主要功能特性

1.  基于注解的方式实现elasticsearch的查询

2.  基于sql语句的方式实现elasticsearch的查询

3.  基于mybatis mapper接口 自动生成elasticsearch查询,并支持数据库回表查询

4.  基于jpa repository接口 自动生成elasticsearch查询,并支持数据库回表查询

5.  基于jooq dao实现类 自动生成elasticsearch查询,并支持数据库回表查询

#### 架构模块

1.  elasticsearch-engine-base 提供注解查询,sql语句查询,ORM查询sql解析,sql改写等基础功能
2.  elasticsearch-engine-mybatis 基于mybatis拦截器 实现sql拦截,改写,执行elasticsearch查询
3.  elasticsearch-engine-jpa 基于aop,hibernate sql拦截器以及重新jpa参数绑定模块 实现sql拦截,改写,执行elasticsearch查询
4.  elasticsearch-engine-jooq 基于aop,jooq执行监听器 实现sql拦截,改写,执行elasticsearch查询

#### 使用说明

1.  xxxx
2.  xxxx
3.  xxxx

#### 参与贡献

1.  Fork 本仓库
2.  新建 Feat_xxx 分支
3.  提交代码
4.  新建 Pull Request


#### 特技

1.  使用 Readme\_XXX.md 来支持不同的语言，例如 Readme\_en.md, Readme\_zh.md
2.  Gitee 官方博客 [blog.gitee.com](https://blog.gitee.com)
3.  你可以 [https://gitee.com/explore](https://gitee.com/explore) 这个地址来了解 Gitee 上的优秀开源项目
4.  [GVP](https://gitee.com/gvp) 全称是 Gitee 最有价值开源项目，是综合评定出的优秀开源项目
5.  Gitee 官方提供的使用手册 [https://gitee.com/help](https://gitee.com/help)
6.  Gitee 封面人物是一档用来展示 Gitee 会员风采的栏目 [https://gitee.com/gitee-stars/](https://gitee.com/gitee-stars/)
