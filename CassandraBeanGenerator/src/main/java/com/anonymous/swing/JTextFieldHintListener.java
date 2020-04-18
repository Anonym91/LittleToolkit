package com.anonymous.swing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

/**
 * @ClassName: JTextFieldHintListener
 * @Author: DLF
 * @Version: 1.0v
 * @Date: 2020/4/11 0011
 * @Description: JText field hinter
 */
public class JTextFieldHintListener implements FocusListener {
    private String hintText;
    private JTextField jTextField;

    public JTextFieldHintListener(JTextField jTextField, String hintText){
        this.hintText = hintText;
        this.jTextField = jTextField;
        jTextField.setText(hintText);
        jTextField.setForeground(Color.RED);
    }
    @Override
    public void focusGained(FocusEvent e) {
        //获取焦点时，清空提示内容
        String temp = jTextField.getText();
        if(temp.equals(hintText)) {
            jTextField.setText("");
            jTextField.setForeground(Color.RED);
        }
    }

    @Override
    public void focusLost(FocusEvent e) {
        //失去焦点时，没有输入内容，显示提示内容
        String temp = jTextField.getText();
        if(temp.equals("")) {
            jTextField.setForeground(Color.RED);
            jTextField.setText(hintText);
        }
    }
}
