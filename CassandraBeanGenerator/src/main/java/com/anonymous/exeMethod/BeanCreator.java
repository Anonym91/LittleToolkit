package com.anonymous.exeMethod;

import com.anonymous.cassandra.CasConfig;
import com.anonymous.cassandra.ColumnProperties;
import com.anonymous.modules.FieldProperties;
import com.anonymous.utils.BeanFieldUtil;
import com.anonymous.utils.FileWriter;
import com.datastax.driver.core.ResultSet;
import com.anonymous.swing.logAbtain.MySystemOut;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.swing.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 * @ClassName: Application
 * @Author: DLF
 * @Version: 1.0v
 * @Date: 2020/4/9 0009
 * @Description: Java Bean Creator
 */
@Slf4j
@Data
@NoArgsConstructor
public class BeanCreator {
    private JTextArea logArea;
    private String filePath;
    private String keyspace;
    private String tables;

    public BeanCreator(JTextArea logArea,String filePath,String keyspace, String tables) {
        this.logArea = logArea;
        this.keyspace = keyspace;
        this.tables = tables;
        this.filePath = filePath;
    }

    /**
     * Execute to generate Java Bean Files by inputted keyspace,tables and file path
     * @throws SQLException
     * @throws IOException
     */
    public void execute() throws SQLException, IOException {
        // Get multi tables split by ','
        String[] tableArray = tables.split(",");
        try{
            for(String table : tableArray){
                ResultSet rs = new ColumnProperties(logArea,keyspace,table, CasConfig.getInstance().getSession(logArea)).getRowRS();
                List<FieldProperties> propsList = new BeanFieldUtil(logArea,rs).getBeanPropertiesList();
                new FileWriter(logArea,filePath,table,propsList).fileWrite();
            }
        }catch (Exception e){
            MySystemOut.System.out.println(logArea,String.format("Failed to generate Java Bean files: %s",e.toString()));
            log.error(String.format("Failed to generate Java Bean files: %s",e.toString()));
        }
    }

}
