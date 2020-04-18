package com.anonymous.utils;

import com.anonymous.modules.FieldProperties;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.anonymous.swing.logAbtain.MySystemOut;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.swing.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @ClassName: BeanFieldUtil
 * @Author: DLF
 * @Version: 1.0v
 * @Date: 2020/4/10 0010
 * @Description: Bean field properties transferred by column properties
 */
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
public class BeanFieldUtil {

    private JTextArea logArea;
    // cassandra 结果集
    private ResultSet rs;

    /**
     * Get field properties by C* result set
     * @return List of field properties
     * @throws SQLException
     */
    public List<FieldProperties> getBeanPropertiesList() throws SQLException {
        List<FieldProperties> properties = new ArrayList<FieldProperties>();
        if(null == rs){
            MySystemOut.System.out.println(logArea,"Execute cql failed! Plz connected to cluster or check your C* host, keyspace or table if correctly typed ...");
            log.error("Execute cql failed! Plz connected to cluster or check your C* host, keyspace or table if correctly typed ...");
        }
            for(Row row : rs){
                FieldProperties props = new FieldProperties();
                props.setFieldName(nameTrans(row.getString("column_name"))); //获取属性名称
                props.setFieldType(typeTrans(row.getString("type"))); //获取属性类型
                properties.add(props);
            }
        return properties;
    }

    /**
     * Cassandra column name transferred to bean field names
     * @param columnName
     * @return field name
     */
    public static String nameTrans(String columnName){
        String result = null;
        if(null != columnName && !"".equals(columnName)){
            if(!columnName.contains("_")){
                result = firstCharUpper(columnName);
            }
            StringBuilder sb = new StringBuilder();
            List<String> strs = Arrays.asList(columnName.split("_"));
            if(strs.size() > 0 && null != strs){
                for(int i=0; i<strs.size() ;i++){
                    if(i==0){
                        sb.append(strs.get(i));
                    }else{
                        sb.append(firstCharUpper(strs.get(i)));
                    }
                }
            }

            result = sb.toString();
        }
        return result;
    }

    /**
     * Cassandra column type transferred to bean field types
     * @param columnType
     * @return field type
     */
    public static String typeTrans(String columnType){
        String result = null;
        switch (columnType){
            case "int" :
                result = "Integer";
                break;
            case "text":
                result = "String";
                break;
            case "float":
                result = "Float";
                break;
            case "double":
                result = "Float";
                break;
            default:
                break;
        }
        return result;
    }

    /**
     * First character Upper
     * @param str
     * @return
     */
    public static String firstCharUpper(String str){
        return str.substring(0,1).toUpperCase()+str.substring(1);
    }

}
