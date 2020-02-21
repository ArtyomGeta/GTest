package com.artyomgeta.GTest;

import org.json.JSONArray;

import javax.swing.*;
import java.io.*;
import java.util.Arrays;

@SuppressWarnings("DuplicatedCode")
public abstract class GTest {

    public static void main(String... args) {
        /*try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.gtk.GTKLookAndFeel");
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }*/
        try {
            new Viewer().run();
        } catch (java.lang.NullPointerException ex) {
            JOptionPane.showMessageDialog(null, "Не удалось загрузить один из файлов. Попробуйте ещё раз.", "Ошибка", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static int returnQuestionsLength(String projectName, int fileType) {
        StringBuilder sb = new StringBuilder();
        int returnable = 0;
        try {
            BufferedReader br = new BufferedReader(new FileReader("Projects/" + projectName + "/" + (fileType == 0 ? "data.json" : "data-presentable.json")));
            String line = br.readLine();
            while (line != null) {
                sb.append(line);
                line = br.readLine();
            }
            br.close();
            returnable = new JSONArray(sb.toString()).getJSONArray(1).length();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return returnable;
    }

    public static String returnQuestion(String projectName, int fileType, int id, int type) {
        StringBuilder sb = new StringBuilder();
        String returnable = null;
        try {
            BufferedReader br = new BufferedReader(new FileReader("Projects/" + projectName + "/" + (fileType == 0 ? "data.json" : "data-presentable.json")));
            String line = br.readLine();
            while (line != null) {
                sb.append(line);
                line = br.readLine();
            }
            br.close();
            returnable = new JSONArray(sb.toString()).getJSONArray(1).getJSONArray(id).getJSONObject(0).getString(type == 0 ? "name" : "description");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return returnable;
    }

    public static int returnQuestionAnswersLength(String projectName, int fileType, int id) {
        StringBuilder sb = new StringBuilder();
        int returnable = 0;
        try {
            BufferedReader br = new BufferedReader(new FileReader("Projects/" + projectName + "/" + (fileType == 0 ? "data.json" : "data-presentable.json")));
            String line = br.readLine();
            while (line != null) {
                sb.append(line);
                line = br.readLine();
            }
            br.close();
            returnable = new JSONArray(sb.toString()).getJSONArray(1).getJSONArray(id).length() - 1;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return returnable;
    }

    public static String returnQuestionAnswer(String projectName, int fileType, int questionId, int answerId) {
        StringBuilder sb = new StringBuilder();
        String returnable = null;
        try {
            BufferedReader br = new BufferedReader(new FileReader("Projects/" + projectName + "/" + (fileType == 0 ? "data.json" : "data-presentable.json")));
            String line = br.readLine();
            while (line != null) {
                sb.append(line);
                line = br.readLine();
            }
            br.close();
            returnable = new JSONArray(sb.toString()).getJSONArray(1).getJSONArray(questionId).getJSONObject(answerId + 1).getString("text");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return returnable;
    }

    public static void makePresentableHTML(String projectName) {
        String testName = "", testDescription = "";
        String[] questionName = new String[GTest.returnQuestionsLength(projectName, 1)];
        String[] questionExtra = new String[GTest.returnQuestionsLength(projectName, 1)];
        String[][] questionAnswers = new String[GTest.returnQuestionsLength(projectName, 1)][returnQuestionAnswersLength(projectName, 1, 0)];
        try {
            StringBuilder sb = new StringBuilder();
            BufferedReader br = new BufferedReader(new FileReader("Projects/" + projectName + "/data-presentable.json"));
            String line = br.readLine();
            while (line != null) {
                sb.append(line);
                line = br.readLine();
            }
            br.close();
            testName = new JSONArray(sb.toString()).getJSONArray(0).getJSONObject(1).getString("test-name");
            testDescription = new JSONArray(sb.toString()).getJSONArray(0).getJSONObject(2).getString("description");
            for (int i = 0; i < GTest.returnQuestionsLength(projectName, 1); i++) {
                questionName[i] = new JSONArray(sb.toString()).getJSONArray(1).getJSONArray(i).getJSONObject(0).getString("name");
                questionExtra[i] = new JSONArray(sb.toString()).getJSONArray(1).getJSONArray(i).getJSONObject(0).getString("description");
                for (int j = 0; j < GTest.returnQuestionAnswersLength(projectName, 1, i); j++) {
                    questionAnswers[i][j] = new JSONArray(sb.toString()).getJSONArray(1).getJSONArray(i).getJSONObject(j == 0 ? 1 : j + 1).getString("text");
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
