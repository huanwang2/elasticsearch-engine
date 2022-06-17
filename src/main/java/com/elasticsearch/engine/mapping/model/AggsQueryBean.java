package com.elasticsearch.engine.mapping.model;

import com.elasticsearch.engine.model.domain.AbstractQueryBean;
import com.elasticsearch.engine.model.domain.EsQueryFieldBean;
import lombok.Data;
import org.elasticsearch.index.query.QueryBuilder;

/**
 * @author wanghuan
 * @description: AggsQueryBean
 * @date 2022-01-26 11:28
 */
@Data
public class AggsQueryBean extends AbstractQueryBean {

    private String type;

    private int size;

    @Override
    public void configQueryBuilder(EsQueryFieldBean queryDes, QueryBuilder queryBuilder) {

    }

}
