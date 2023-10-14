package com.zsx.common;

import cn.hutool.crypto.symmetric.SymmetricAlgorithm;
import cn.hutool.crypto.symmetric.SymmetricCrypto;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;
 
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
 
@Slf4j
@MappedJdbcTypes(JdbcType.VARCHAR)
@MappedTypes(String.class)
public class CryptoTypeHandler extends BaseTypeHandler<String> {
 
    private final byte[] key = {-26, -70, -29, -99, 73, -82, 91, -50, 79, -77, 59, 104, 2, -36, 50, -22, -39, -15, -57, -89, 81, -99, 42, -89};
 
    private final SymmetricCrypto des = new SymmetricCrypto(SymmetricAlgorithm.DESede, key);
 
 
    /*
     * 加工入参
     */
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, String parameter, JdbcType jdbcType) throws SQLException {
        if (parameter != null) {
            //加密
            String encryptHex = des.encryptHex(parameter);
            log.info("{} ---加密为---> {}", parameter, encryptHex);
            ps.setString(i, encryptHex);
        }
    }
 
    /*
     * 根据列名获取返回结果，可在此方法中加工返回值
     */
    @Override
    public String getNullableResult(ResultSet rs, String columnName) throws SQLException {
        String originRes = rs.getString(columnName);
        if (originRes != null) {
            String res = des.decryptStr(originRes);
            log.info("{} ---解密为---> {}", originRes, res);
            return res;
        }
        log.info("结果为空，无需解密");
        return null;
    }
 
    /*
     * 根据列下标获取返回结果，可在此方法中加工返回值
     */
    @Override
    public String getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        String originRes = rs.getString(columnIndex);
        if (originRes != null) {
            String res = des.decryptStr(originRes);
            log.info("{} ---解密为---> {}", originRes, res);
            return res;
        }
        log.info("结果为空，无需解密");
        return null;
    }
 
    /*
     * 根据列下标获取返回结果（存储过程），可在此方法中加工返回值
     */
    @Override
    public String getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        String originRes = cs.getString(columnIndex);
        if (originRes != null) {
            String res = des.decryptStr(originRes);
            log.info("{} ---解密为---> {}", originRes, res);
            return res;
        }
        log.info("结果为空，无需解密");
        return null;
    }
 
}