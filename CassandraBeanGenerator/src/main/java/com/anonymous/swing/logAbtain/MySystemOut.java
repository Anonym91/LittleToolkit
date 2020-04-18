package com.anonymous.swing.logAbtain;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.swing.*;

/**
 * @ClassName: System
 * @Author: DLF
 * @Version: 1.0v
 * @Date: 2020/4/12 0012
 * @Description: System.out.println method overwritten for print log in the JTextArea Panel
 */
@Data
@AllArgsConstructor
public class MySystemOut {
    public static class System{
        public static class out{
            public static void println(JTextArea logArea, String out){
                logArea.append(out+"\n");
            }
        }
    }
}