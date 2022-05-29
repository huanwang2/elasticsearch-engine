package com.elasticsearch.engine.elasticsearchengine.common.proxy.handler.exsql.impl;

import com.elasticsearch.engine.elasticsearchengine.common.parse.sql.SqlParamParseHelper;
import com.elasticsearch.engine.elasticsearchengine.common.proxy.enums.EsSqlQueryEnum;
import com.elasticsearch.engine.elasticsearchengine.common.proxy.handler.exannotation.AnnotationQueryCommon;
import com.elasticsearch.engine.elasticsearchengine.common.proxy.handler.exsql.EsSqlQueryHandler;
import com.elasticsearch.engine.elasticsearchengine.common.queryhandler.sql.EsSqlExecuteHandler;
import com.elasticsearch.engine.elasticsearchengine.common.utils.ThreadLocalUtil;
import com.elasticsearch.engine.elasticsearchengine.model.annotion.EsQuery;
import com.elasticsearch.engine.elasticsearchengine.model.constant.CommonConstant;
import com.elasticsearch.engine.elasticsearchengine.model.emenu.SqlParamParse;
import com.elasticsearch.engine.elasticsearchengine.model.exception.EsHelperExecuteException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Objects;

/**
 * @author wanghuan
 * @description: ROOD
 * @date 2022-04-26 23:13
 */
@Slf4j
@Component
public class EsAnnotationSqlQueryHandler implements EsSqlQueryHandler {

    @Resource
    private EsSqlExecuteHandler esSqlExecuteHandler;

    @Override
    public Boolean matching(EsSqlQueryEnum factory) {
        return EsSqlQueryEnum.ANNOTATION_QUERY.equals(factory);
    }

    @Override
    public Object handle(Object proxy, Method method, Object[] args) {
        String prefix = ThreadLocalUtil.get(CommonConstant.INTERFACE_METHOD_NAME);
        //方法返回值
        Class<?> returnType = method.getReturnType();
        //方法返回值的泛型
        Class<?> returnGenericType = AnnotationQueryCommon.getReturnGenericType(method);
        EsQuery esQuery = method.getAnnotation(EsQuery.class);
        if (Objects.isNull(esQuery) || StringUtils.isEmpty(esQuery.value())) {
            throw new EsHelperExecuteException(prefix + "@EsQuery 注解不存在或参数为空");
        }
        // 解析sql参数
        String sql = SqlParamParseHelper.getMethodArgsSql(esQuery.value(),method, args, SqlParamParse.ANN_SQL_PARAM);

        List<?> list;
        if (List.class.isAssignableFrom(returnType) && Objects.nonNull(returnGenericType)) {
            list = esSqlExecuteHandler.queryBySql(sql, returnGenericType);
        } else {
            list = esSqlExecuteHandler.queryBySql(sql, returnType);
        }

        if (List.class.isAssignableFrom(returnType)) {
            return list;
        } else {
            if (list.size() > 0) {
                return list.get(0);
            }
            return null;
        }
    }
  
}
