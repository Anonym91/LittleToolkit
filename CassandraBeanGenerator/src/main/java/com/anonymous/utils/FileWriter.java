package com.anonymous.utils;

import com.anonymous.config.FileModule;
import com.anonymous.modules.FieldProperties;
import com.anonymous.swing.logAbtain.MySystemOut;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.swing.*;
import java.io.*;
import java.util.Date;
import java.util.List;

/**
 * @ClassName: FileWriter
 * @Author: DLF
 * @Version: 1.0v
 * @Date: 2020/4/10 0010
 * @Description: Create file path and write into Java Bean files
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Slf4j
public class FileWriter {

    private static final String FILE_SUFFIX = ".java";
    private JTextArea logArea;
    private String filePath;
    private String table;
    private List<FieldProperties> propertiesList;

    public void fileWrite() throws IOException {
        if(null==propertiesList || propertiesList.size()==0){
            return;
        }
        filePath = filePath +"/casBean/";
        if (table.contains("_")) {
            String parentPackagePath = filePath + table.substring(0, table.indexOf("_"));
            filePath = parentPackagePath + "/" + BeanFieldUtil.firstCharUpper(table.substring(table.indexOf("_") + 1)) + FILE_SUFFIX;
        } else {
            filePath = filePath + BeanFieldUtil.firstCharUpper(table) + FILE_SUFFIX;
        }

        String parentPath = filePath.substring(0, filePath.lastIndexOf("/"));
        File parentFilePath = new File(parentPath);
        if (!parentFilePath.exists()) {
            parentFilePath.mkdirs();
        }

        File savedFile = new File(filePath);
        if (!savedFile.exists()) {
            log.info(String.format("Creating new file: %s...", filePath));
            MySystemOut.System.out.println(logArea,String.format("Creating new file: %s...", filePath));
            savedFile.createNewFile();
            writeFileContent(filePath, getFileStr(propertiesList));
        }else {
            log.info(String.format("File[%s] already existed! Plz delete it to proceed...", filePath));
            MySystemOut.System.out.println(logArea,String.format("File[%s] already existed! Plz delete it to proceed...", filePath));
        }
    }

    private boolean writeFileContent(String filePath, String content) throws IOException {
        boolean flag = false;
        String fileIn = content + "\r\n";
        String temp = "";
        FileInputStream fis = null;
        InputStreamReader isr = null;
        BufferedReader br = null;
        FileOutputStream fos = null;
        PrintWriter pw = null;
        try {
            File file = new File(filePath);
            fis = new FileInputStream(file);
            isr = new InputStreamReader(fis);
            br = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();

            for (int i = 0; (temp = br.readLine()) != null; i++) {
                sb.append(temp);
                // 行与行之间的分隔符 相当于“\n”
                sb = sb.append(System.getProperty("line.separator"));
            }
            sb.append(fileIn);

            fos = new FileOutputStream(file);
            pw = new PrintWriter(fos);
            pw.write(sb.toString().toCharArray());
            pw.flush();
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (pw != null) {
                pw.close();
            }
            if (fos != null) {
                fos.close();
            }
            if (br != null) {
                br.close();
            }
            if (isr != null) {
                isr.close();
            }
            if (fis != null) {
                fis.close();
            }
        }
        log.info("写入成功!");
        MySystemOut.System.out.println(logArea,"File written successfully!");
        return flag;
    }

    private String getFileStr(List<FieldProperties> propertiesList) {
        String nameStr = GlobalUtil.fileName(table);
        String className = nameStr.contains("/") ? nameStr.substring(nameStr.indexOf("/") + 1) : nameStr;
        StringBuilder content = new StringBuilder();

        content
                .append(String.format(FileModule.description,className,new Date()))
                .append(FileModule.prefix)
                .append("\n")
                .append(String.format(FileModule.api_class,"$$$"))
                .append("\n")
                .append(String.format(FileModule.class_definition, className))
                .append("\n");
        for (FieldProperties prop : propertiesList) {
            content
                    .append("\t")
                    .append(String.format(FileModule.api_field,"$$$",prop.getFieldName(),prop.getFieldType().toLowerCase()))
                    .append("\n\t")
                    .append(String.format(FileModule.json_field,prop.getFieldName()))
                    .append("\n\t")
                    .append(String.format(FileModule.field_prefix, prop.getFieldType(), prop.getFieldName()))
                    .append(";\n\n");
        }
        content
                .append("\t@Override\n" +
                "\tpublic String toString(){\n" +
                "\treturn JSON.toJSONString(this);\n" +
                "\t}\n");
        content
                .append(String.format("\tpublic static %s fromJsonStr(String str){\n" +
                "\t\treturn JSON.parseObject(str,%s.class);\n" +
                "\t}\n",className,className));
        content
                .append(FileModule.finalChar)
                .append("\n");
        return content.toString();
    }

}
