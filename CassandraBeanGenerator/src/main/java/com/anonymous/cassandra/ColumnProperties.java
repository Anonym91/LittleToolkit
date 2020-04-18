package com.anonymous.cassandra;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Session;
import com.anonymous.swing.logAbtain.MySystemOut;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.swing.*;

/**
 * @ClassName: GetResultSet
 * @Author: DLF
 * @Version: 1.0v
 * @Date: 2020/4/9 0009
 * @Description: C* table's column names and field properties
 * Got by the c* system schema table--system_schema.columns
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Slf4j
public class ColumnProperties {

    private JTextArea logArea; // Jtextarea for log printing
    private String keyspace; // C* keyspace
    private String table; // C* table
    private Session session; // C* session

    private static final String CQL_SUFFIX = "ALLOW FILTERING"; // CQL SUFFIX

    /**
     * Get the result set of all column names and types by inputted keyspace name & table name
     * @return result set
     */
    public ResultSet getRowRS(){
        ResultSet rs = null; //
        String cql = "SELECT column_name,type FROM system_schema.columns WHERE keyspace_name = ? AND table_name = ? "+CQL_SUFFIX; // 查询语句
        if(null != session){
            rs = session.execute(cql,keyspace,table);
            MySystemOut.System.out.println(logArea,String.format("Executing cql: %s",getCql(cql)));
            log.info(String.format("Executing cql: %s",getCql(cql)));
        }else {
            MySystemOut.System.out.println(logArea,"Session not existed!");
            log.info("Session not existed!!");
        }
        return rs;
    }

    /**
     * Get the real cql by replacing variables(?) by their value
     * @param cql
     * @return cql string
     */
    private String getCql(String cql){
        StringBuilder sb = new StringBuilder();
        int first_idx = cql.indexOf("?");
        int sec_idx = cql.lastIndexOf("?");
        sb.append(cql.substring(0,first_idx))
        .append(keyspace)
        .append(cql.substring(first_idx+1,sec_idx))
        .append(table)
        .append(cql.substring(sec_idx+1));
        return sb.toString();
    }
}
