package com.artyomgeta.GTest;

import javax.swing.*;
import javax.swing.text.Document;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.StyleSheet;
import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

@SuppressWarnings({"FieldCanBeLocal"})
public class Viewer extends JFrame {
    private JPanel panel1;
    private JList<String> list1;
    private JButton butAddQuestion;
    private JEditorPane editorPane1;
    private DefaultListModel<String> defaultListModel = new DefaultListModel<String>();
    private String projectName = "Test";

    public Viewer() {

    }

    public void run() {
        this.setBounds(new Rectangle(0, 0, Toolkit.getDefaultToolkit().getScreenSize().width, Toolkit.getDefaultToolkit().getScreenSize().height));
        this.setTitle("GTest - создать тест");
        this.setContentPane(panel1);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setContent();
        this.editorPane1.setContentType("text/html");
        try {
            StringBuilder sb = new StringBuilder();
            BufferedReader br = new BufferedReader(new FileReader("/home/artyom/IdeaProjects/GTest/Projects/Test/index-presentable.html"));
            String line = br.readLine();
            while (line != null) {
                sb.append(line);
                line = br.readLine();
            }
            br.close();
            this.editorPane1.setText(GTest.makePresentableHTML(projectName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.setUI();
    }

    private void setContent() {
        for (int i = 0; i < GTest.returnQuestionsLength(projectName, 0); i++) {
            defaultListModel.addElement(String.format("%d: " + GTest.returnQuestion(projectName, 0, i, 0), i + 1));
        }
        list1.setModel(defaultListModel);

        HTMLEditorKit kit = new HTMLEditorKit();
        editorPane1.setEditorKit(kit);
        StyleSheet styleSheet = kit.getStyleSheet();
        try {
            styleSheet.importStyleSheet(new URL("file:///" + new File("Projects/" + projectName + "/style.css").getAbsolutePath()));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        Document doc = kit.createDefaultDocument();
        editorPane1.setDocument(doc);
        GTest.makePresentableHTML(projectName);
    }

    private void setUI() {
        this.butAddQuestion.addActionListener(e -> new Question(this).run());
    }

}
