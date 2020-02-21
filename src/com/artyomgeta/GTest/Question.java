package com.artyomgeta.GTest;

import javax.swing.*;
import java.awt.*;

public class Question extends JDialog {
    private JPanel panel1;
    private JTextField textField1;
    private JButton btnAddAnswer;
    private JButton btnEditAnswer;
    private JButton btnDeleteAnswer;
    private JTextArea textArea2;
    private JButton btnSave;
    private JButton btnCancel;
    private JTextField textField2;
    private Viewer mainClass;

    public Question(Viewer viewer) {
        mainClass = viewer;
        this.btnSave.addActionListener(e -> {
            this.dispose();
        });

        this.btnCancel.addActionListener(e -> this.dispose());
    }

    public void run() {
        this.setTitle("Вопрос");
        this.setContentPane(panel1);
        this.setBounds(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize().width / 2 - 320, Toolkit.getDefaultToolkit().getScreenSize().height / 2 - 240, 640, 480));
        this.setResizable(false);
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        this.setModal(true);
        this.setLocationRelativeTo(mainClass);
        this.setUndecorated(true);
        this.setVisible(true);
        this.setUI();
    }

    private void setUI() {
    }
}
