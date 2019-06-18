package com.puan.demo;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import java.awt.*;

/**
 * @author puan
 * @date 2019-06-17 9:26
 **/
public class MyFrame {

    private static String url;

    public static void main(String[] args) {
        JFrame frame = createFrame();
        buildFrame(frame);
        frame.setVisible(true);
    }

    private static void buildFrame(JFrame frame) {
        JPanel panel = new JPanel();
        frame.add(panel);
        panel.setLayout(null);
        setLable(panel);
        setInput(panel);
        setButton(panel, frame);
    }

    private static void setButton(Container container, JFrame frame) {
        JButton button = new JButton();
        button.setText("导出");
        button.setBounds(200, 200, 80, 30);
        container.add(button);

        button.addActionListener(e -> {
            try {
                Demo.generateAsciiDocsToFile(url);
                Demo.executeMavenCmd();
                success(frame);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });
    }

    private static void setInput(Container container) {
        JTextField textField = new JTextField();
        textField.setBounds(110, 100, 300, 30);
        Document doc = textField.getDocument();
        doc.addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                getText(e.getDocument());
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                getText(e.getDocument());
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                getText(e.getDocument());
            }
        });
        container.add(textField);
    }

    private static void getText(Document document) {
        try {
            url = document.getText(0, document.getLength());
        } catch (BadLocationException e1) {
            e1.printStackTrace();
        }
    }

    private static void setLable(Container container) {
        JLabel label = new JLabel();
        label.setText("URL:");
        label.setBounds(50, 100, 50, 30);
        container.add(label);
    }

    private static void success(JFrame frame) {
        JOptionPane.showMessageDialog(frame, "导出成功", "信息", JOptionPane.INFORMATION_MESSAGE);
    }

    private static JFrame createFrame() {
        JFrame frame = new JFrame();
        frame.setSize(500, 350);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setTitle("导出API");
        Image icon = Toolkit.getDefaultToolkit().getImage("./src/main/resources/logo.png");
        frame.setIconImage(icon);
        return frame;
    }
}
