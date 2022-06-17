package com.elasticsearch.engine.mapping.model;

import com.elasticsearch.engine.model.domain.AbstractQueryBean;
import com.elasticsearch.engine.model.domain.EsQueryFieldBean;
import org.elasticsearch.index.query.PrefixQueryBuilder;

/**
 * @author wanghuan
 * @description: PrefixQueryBean
 * @date 2022-01-26 11:28
 */
public class PrefixQueryBean extends AbstractQueryBean<PrefixQueryBuilder> {

    @Override
    public void configQueryBuilder(EsQueryFieldBean queryDes, PrefixQueryBuilder queryBuilder) {
    }
}
