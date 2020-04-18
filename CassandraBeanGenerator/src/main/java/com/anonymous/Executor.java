package com.anonymous;

import com.anonymous.swing.BCFrame;
import lombok.extern.slf4j.Slf4j;

import javax.swing.*;


/**
 * @ClassName: Executor
 * @Author: DLF
 * @Version: 1.0v
 * @Date: 2020/4/11 0011
 * @Description:
 */
@Slf4j
public class Executor{
    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try{
                    BCFrame bcFrame = new BCFrame();
                    bcFrame.setTitle("Cassandra Java Bean Generator");
                    bcFrame.setSize(1000,800);
                    bcFrame.setVisible(true);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

    }
}
