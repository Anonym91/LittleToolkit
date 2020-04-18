package com.anonymous.modules;

import com.alibaba.fastjson.JSON;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName: CasProperties
 * @Author: DLF
 * @Version: 1.0v
 * @Date: 2020/4/9 0009
 * @Description: Java bean field names and types
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FieldProperties {

    private String fieldName;

    private String fieldType;

    @Override
    public String toString(){
        return JSON.toJSONString(this);
    }
}
