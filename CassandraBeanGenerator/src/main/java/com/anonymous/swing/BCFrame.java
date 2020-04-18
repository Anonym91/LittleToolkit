package com.anonymous.swing;

import com.anonymous.cassandra.CasConfig;
import com.anonymous.exeMethod.BeanCreator;
import com.anonymous.swing.logAbtain.MySystemOut;
import lombok.extern.slf4j.Slf4j;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @ClassName: BC
 * @Author: DLF
 * @Version: 1.0v
 * @Date: 2020/4/11 0011
 * @Description: Java swing JFrame
 */
@Slf4j
public class BCFrame extends JFrame implements ActionListener {

    private static boolean isConn = false;

    Toolkit kit = Toolkit.getDefaultToolkit();
    Dimension screenSize = kit.getScreenSize();
    int screenWidth = screenSize.width;
    int screenHeight = screenSize.height;

    private JLabel casHostLabel = null;
    private JTextField casHostText = null;
    private JLabel casKeySpaceLabel = null;
    private JTextField casKeySpaceText = null;
    private JLabel casTablesLabel = null;
    private JTextField casTablesText = null;
    private JLabel filePathLable = null;
    private JTextField filePathText = null;

    private JButton exeButton = null;
    private JButton clcButton = null;
    private JButton initCluster = null;

    private JTextArea logArea = null;
    private JScrollPane logScrollPanel = null;
    private JPanel logPanel = null;

    private JPanel labelPanel = null;

    public BCFrame() {
        super();
        initialize();
    }


    private void initialize() {
        setDefaultLookAndFeelDecorated(true);
        try{
            UIManager.setLookAndFeel("org.pushingpixels.substance.api.skin.SubstanceMistSilverLookAndFeel");
        } catch (IllegalAccessException | UnsupportedLookAndFeelException | InstantiationException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        InitGlobalFont.InitGlobalFont(new Font("alias", Font.CENTER_BASELINE, 16));//统一设置字体
        setLocation(screenWidth / 2 - 390 / 2, screenHeight / 2 - 580 / 2);
        filePathLable = new JLabel();
        filePathLable.setBounds(new Rectangle(10, 80, 180, 22));
        filePathLable.setText("File Path:");
        filePathLable.setForeground(Color.magenta);
        casHostLabel = new JLabel();
        casHostLabel.setBounds(new Rectangle(10, 30, 180, 22));
        casHostLabel.setText("Cassandra Hosts: ");
        casHostLabel.setForeground(Color.magenta);
        casKeySpaceLabel = new JLabel();
        casKeySpaceLabel.setBounds(new Rectangle(10, 130, 180, 22));
        casKeySpaceLabel.setText("Cassandra Keyspace: ");
        casKeySpaceLabel.setForeground(Color.magenta);
        casTablesLabel = new JLabel();
        casTablesLabel.setBounds(new Rectangle(10, 180, 180, 22));
        casTablesLabel.setText("Cassandra Tables: ");
        casTablesLabel.setForeground(Color.magenta);

        getContentPane().setLayout(new BorderLayout());

        labelPanel = new JPanel();
        labelPanel.setBackground(Color.WHITE);
        labelPanel.setBorder(BorderFactory.createTitledBorder("Properties"));
        labelPanel.setPreferredSize(new Dimension(900, 300));
        labelPanel.setLayout(null);
        labelPanel.add(filePathLable, null);
        labelPanel.add(getFilePathText(), null);
        labelPanel.add(casHostLabel, null);
        labelPanel.add(getCasHostText(), null);
        labelPanel.add(casKeySpaceLabel, null);
        labelPanel.add(getCasKeySpaceText(), null);
        labelPanel.add(casTablesLabel, null);
        labelPanel.add(getCasTablesText(), null);
        labelPanel.add(getExeButton(), null);
        labelPanel.add(getInitCluster(), null);


        logPanel = new JPanel();
        logPanel.setBackground(Color.WHITE);
        logPanel.setBorder(BorderFactory.createTitledBorder("Execution Log"));
        logPanel.setPreferredSize(new Dimension(900, 500));
        logScrollPanel = new JScrollPane(getLogPane());
        logScrollPanel.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        logScrollPanel.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        logScrollPanel.setPreferredSize(new Dimension(800, 450));
        logPanel.add(logScrollPanel, null);
        logPanel.add(getClcButton(), null);
        logPanel.setVisible(true);

        getContentPane().add(logPanel, BorderLayout.SOUTH);
        getContentPane().add(labelPanel, BorderLayout.NORTH);

        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    /**
     * Listened events of buttons
     *
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        String buttonName = e.getActionCommand();
        if (buttonName.equals("C* Cluster Connect")) {
            if (!isConn) {
                String casHost = casHostText.getText();
                try {
                    logArea.setEditable(true);
                    CasConfig.getInstance().initCluster(logArea, casHost);
                    isConn = true;
                } catch (Exception ex) {
                    logArea.setEditable(true);
                    MySystemOut.System.out.println(logArea, ex.toString());
                }
            }else{
                log.warn("Cluster Connected! Please forwarding...");
                MySystemOut.System.out.println(logArea, "Cluster Connected! Please forwarding...");
            }
        }

        if (buttonName.equals("Bean Generate")) {
            if (isConn) {
                String keyspace = casKeySpaceText.getText();
                String tables = casTablesText.getText();
                String filePath = filePathText.getText();
                try {
                    logArea.setEditable(true);
                    BeanCreator bc = new BeanCreator(logArea, filePath, keyspace, tables);
                    bc.execute();
                } catch (Exception ex) {
                    logArea.setEditable(true);
                    MySystemOut.System.out.println(logArea, ex.toString());
                }
            }else{
                log.warn("Please connect to C* Cluster first...");
                MySystemOut.System.out.println(logArea, "Please connect to C* Cluster first...");
            }
        }

        if (buttonName.equals("clear log")) {
            logArea.setText("");
            log.info("clearing log...");
        }
    }

    /**
     * C* cluster connection button
     *
     * @return
     */
    private JButton getInitCluster() {
        if (initCluster == null) {
            initCluster = new JButton("C* Cluster Connect");
            initCluster.setBounds(new Rectangle(743, 55, 180, 32));
            initCluster.addActionListener(this);
        }
        return initCluster;
    }


    /**
     * Execution button
     *
     * @return
     */
    private JButton getExeButton() {
        if (exeButton == null) {
            exeButton = new JButton("Bean Generate");
            exeButton.setBounds(new Rectangle(750, 155, 165, 32));
            exeButton.addActionListener(this);
        }
        return exeButton;
    }

    /**
     * Log clear button
     *
     * @return
     */
    private JButton getClcButton() {
        if (clcButton == null) {
            clcButton = new JButton("clear log");
            clcButton.setBounds(new Rectangle(10, 350, 120, 32));
            clcButton.addActionListener(this);
        }
        return clcButton;
    }

    /**
     * Get text of file path
     *
     * @return
     */
    private JTextField getFilePathText() {
        if (filePathText == null) {
            filePathText = new JTextField("./");
            filePathText.setBounds(new Rectangle(200, 80, 500, 32));
            filePathText.addFocusListener(new JTextFieldHintListener(filePathText, "./src/main/java/com/anonymous"));
        }
        return filePathText;
    }

    /**
     * Get text of C* connect point
     *
     * @return
     */
    private JTextField getCasHostText() {
        if (casHostText == null) {
            casHostText = new JTextField("192.168.1.190");
            casHostText.setBounds(new Rectangle(200, 30, 500, 32));
            casHostText.addFocusListener(new JTextFieldHintListener(casHostText, "192.168.1.190"));
        }
        return casHostText;
    }

    /**
     * Get text of C* keyspace
     *
     * @return
     */
    private JTextField getCasKeySpaceText() {
        if (casKeySpaceText == null) {
            casKeySpaceText = new JTextField("ods");
            casKeySpaceText.setBounds(new Rectangle(200, 130, 500, 32));
            casKeySpaceText.addFocusListener(new JTextFieldHintListener(casKeySpaceText, "ods"));
        }
        return casKeySpaceText;
    }

    /**
     * Get text of C* tables
     *
     * @return
     */
    private JTextField getCasTablesText() {
        if (casTablesText == null) {
            casTablesText = new JTextField("user");
            casTablesText.setBounds(new Rectangle(200, 180, 500, 32));
            casTablesText.addFocusListener(new JTextFieldHintListener(casTablesText, "user,district"));
        }
        return casTablesText;
    }

    /**
     * Log printer
     *
     * @return
     */
    private JTextArea getLogPane() {
        if (logArea == null) {
            logArea = new JTextArea();
            logArea.setEditable(false);
            logArea.setColumns(20);
            logArea.setRows(100);
            logArea.setBounds(new Rectangle(150, 200, 800, 500));
            logArea.setLineWrap(true);        //激活自动换行功能
            logArea.setWrapStyleWord(true);
            logArea.setBackground(Color.WHITE);
            logArea.setForeground(Color.blue);
        }
        return logArea;
    }


}
