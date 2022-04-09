package com.elasticsearch.engine.elasticsearchengine.model.annotion;

import com.elasticsearch.engine.elasticsearchengine.ElasticsearchEngineConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * Enable ElasticSearch Handler
 * <p>
 * author     JohenTeng
 * date      2021/9/18
 */
@Documented
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Import({ElasticsearchEngineConfiguration.class})
@ComponentScan(basePackages = "com.elasticsearch.engine.elasticsearchengine")
public @interface EnableEsHelper {
}
