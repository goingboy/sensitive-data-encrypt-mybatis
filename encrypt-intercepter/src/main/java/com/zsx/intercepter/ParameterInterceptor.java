package com.zsx.intercepter;

import com.baomidou.mybatisplus.core.MybatisParameterHandler;
import com.zsx.annotation.SensitiveField;
import com.zsx.annotation.SensitiveData;
import com.zsx.utils.DBAESUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.sql.PreparedStatement;
import java.util.*;

/**
 * @author zhousx
 * @create 2023/10/01-22:45
 **/
@Slf4j
// 注入Spring
@Component
@Intercepts({
        @Signature(type = ParameterHandler.class, method = "setParameters", args = PreparedStatement.class),
})
public class ParameterInterceptor implements Interceptor {

    @Autowired
    private com.zsx.utils.IEncryptUtil IEncryptUtil;


    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        //@Signature 指定了 type= parameterHandler 后，这里的 invocation.getTarget() 便是parameterHandler
        //若指定ResultSetHandler ，这里则能强转为ResultSetHandler
        MybatisParameterHandler parameterHandler = (MybatisParameterHandler) invocation.getTarget();
        // 获取参数对像，即 mapper 中 paramsType 的实例
        Field parameterField = parameterHandler.getClass().getDeclaredField("parameterObject");
        parameterField.setAccessible(true);
        //取出实例
        Object parameterObject = parameterField.get(parameterHandler);
        // 搜索该方法中是否有需要加密的普通字段
        List<String> paramNames = searchParamAnnotation(parameterHandler);
        if (parameterObject != null) {
            Class<?> parameterObjectClass = parameterObject.getClass();
            //对类字段进行加密
            //校验该实例的类是否被@SensitiveData所注解
            SensitiveData sensitiveData = AnnotationUtils.findAnnotation(parameterObjectClass, SensitiveData.class);
            if (Objects.nonNull(sensitiveData)) {
                //取出当前当前类所有字段，传入加密方法
                Field[] declaredFields = parameterObjectClass.getDeclaredFields();
                IEncryptUtil.encrypt(declaredFields, parameterObject);
            }

            //如果传参为List类型，对list里的对象进行加密
            processListParam(parameterObject);

            // 对普通字段进行加密
            if (!CollectionUtils.isEmpty(paramNames)) {
                // 反射获取 BoundSql 对象，此对象包含生成的sql和sql的参数map映射
                Field boundSqlField = parameterHandler.getClass().getDeclaredField("boundSql");
                boundSqlField.setAccessible(true);
                BoundSql boundSql = (BoundSql) boundSqlField.get(parameterHandler);
                PreparedStatement ps = (PreparedStatement) invocation.getArgs()[0];
                // 改写参数
                processParam(parameterObject, paramNames);
                // 改写的参数设置到原parameterHandler对象
                parameterField.set(parameterHandler, parameterObject);
                parameterHandler.setParameters(ps);
            }
        }
        return invocation.proceed();
    }

    /**
     * 如果传参为List类型，对list里的对象判断，是否进行加密
     * @param parameterObject
     * @throws IllegalAccessException
     */
    private void processListParam(Object parameterObject) throws IllegalAccessException {
        // mybatis会把list封装到一个ParamMap中
        if (parameterObject instanceof Map) {
            //1. 如果不对传参users使用@Param注解，则会在map中放入collection、list、users三个键值对，这三个指向同一个内存地址即内容相同。
            if (((Map) parameterObject).containsKey("list")) {
                Map map = (Map) parameterObject;
                ArrayList list = (ArrayList) map.get("list");
                Object element = list.get(0);
                Class<?> elementClass = element.getClass();
                SensitiveData tempSensitiveData = AnnotationUtils.findAnnotation(elementClass, SensitiveData.class);
                if (Objects.nonNull(tempSensitiveData)) {
                    for (Object elementObject : list) {
                        Field[] declaredFields = elementClass.getDeclaredFields();
                        IEncryptUtil.encrypt(declaredFields, elementObject);
                    }
                }
            }

            //2. 如果使用了@Param注解对参数重命名为users，那么map中只会放入users、param1两个键值对，这2个指向同一个内存地址即内容相同。
            if (((Map) parameterObject).containsKey("param1")) {
                Map map = (Map) parameterObject;
                Object param1 = map.get("param1");
                //如果param1是ArrayList,则转为arrayList。否则不转换
                if (param1 instanceof ArrayList) {
                    ArrayList list = (ArrayList) param1;
                    Object element = list.get(0);
                    Class<?> elementClass = element.getClass();
                    SensitiveData tempSensitiveData = AnnotationUtils.findAnnotation(elementClass, SensitiveData.class);
                    if (Objects.nonNull(tempSensitiveData)) {
                        for (Object elementObject : list) {
                            Field[] declaredFields = elementClass.getDeclaredFields();
                            IEncryptUtil.encrypt(declaredFields, elementObject);
                        }
                    }
                }
            }
        }
    }


    /**
     * 处理普通参数，对params中的String参数进行加密
     * @param parameterObject
     * @param params
     * @throws Exception
     */
    private void processParam(Object parameterObject, List<String> params) throws Exception {
        // 处理参数对象  如果是 map 且map的key 中没有 tenantId，添加到参数map中
        // 如果参数是bean，反射设置值

        if (parameterObject instanceof Map) {
            @SuppressWarnings("unchecked")
            Map<String, String> map = ((Map<String, String>) parameterObject);
            for (String param : params) {
                String value = map.get(param);
                map.put(param, value == null ? null : DBAESUtil.encrypt(value));
            }
//            parameterObject = map;
        }
    }

    /**
     * 查找参数的注解是否是含有 @EncryptTransaction注解，如果是，则存储参数名
     * @param parameterHandler
     * @return
     * @throws NoSuchFieldException
     * @throws ClassNotFoundException
     * @throws IllegalAccessException
     */
    private List<String> searchParamAnnotation(ParameterHandler parameterHandler) throws NoSuchFieldException, ClassNotFoundException, IllegalAccessException {
        Class<MybatisParameterHandler> handlerClass = MybatisParameterHandler.class;
        Field mappedStatementFiled = handlerClass.getDeclaredField("mappedStatement");
        mappedStatementFiled.setAccessible(true);
        MappedStatement mappedStatement = (MappedStatement) mappedStatementFiled.get(parameterHandler);
        String methodName = mappedStatement.getId();
        Class<?> mapperClass = Class.forName(methodName.substring(0, methodName.lastIndexOf('.')));
        methodName = methodName.substring(methodName.lastIndexOf('.') + 1);
        Method[] methods = mapperClass.getDeclaredMethods();
        Method method = null;
        for (Method m : methods) {
            if (m.getName().equals(methodName)) {
                method = m;
                break;
            }
        }
        List<String> paramNames = null;
        if (method != null) {

            Annotation[][] pa = method.getParameterAnnotations();
            Parameter[] parameters = method.getParameters();
            for (int i = 0; i < pa.length; i++) {
                for (Annotation annotation : pa[i]) {
                    if (annotation instanceof SensitiveField) {
                        if (paramNames == null) {
                            paramNames = new ArrayList<>();
                        }
                        paramNames.add(parameters[i].getName());
                    }
                }
            }
        }
        return paramNames;
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {

    }
}

