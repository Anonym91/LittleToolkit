package com.anonymous.config;

/**
 * @ClassName: FileModule
 * @Author: DLF
 * @Version: 1.0v
 * @Date: 2020/4/10 0010
 * @Description: Module of Java Bean
 */
public class FileModule {

    // Description of Java Class
    public static final String description = "/**\n" +
            " * @ClassName: %s\n" +
            " * @Author: anonymous\n" +
            " * @Version: 1.0v\n" +
            " * @Date: %s\n" +
            " * @Description: Created By CasBeanCreator Utils...\n" +
            " * @FileDesc: $$$\n" +
            " */\n";
    // lombok @Data annotation
    public static final String prefix = "@Data";
    // swagger-ui doc annotation
    public static final String api_class = "@ApiModel(value = \"%s\")";
    // swagger-ui field properties annotation
    public static final String api_field = "@ApiModelProperty(value = \"%s\", name = \"%s\", dataType = \"%s\")";
    // fast-json annotation
    public static final String json_field = "@JSONField(name = \"%s\",serialzeFeatures = SerializerFeature.WriteMapNullValue)";
    // class definition
    public static final String class_definition = "public class %s {";
    // field definition
    public static final String field_prefix = "private %s %s";
    public static final String finalChar = "}";

}
