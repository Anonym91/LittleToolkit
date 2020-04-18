package com.anonymous.utils;

/**
 * @ClassName: GlobalUtil
 * @Author: DLF
 * @Version: 1.0v
 * @Date: 2020/4/9 0009
 * @Description:
 */
public class GlobalUtil {

    public static String getCurrentProjectPath() {
        String path = System.getProperty("user.dir");
        return path;
    }

    public static String fileName(String table){
        if(table.contains("_")){
            String[] tmp = table.split("_");
            table = tmp[0]+"/"+BeanFieldUtil.firstCharUpper(tmp[1]);
            return table;
        }
        return BeanFieldUtil.firstCharUpper(table);
    }
}
