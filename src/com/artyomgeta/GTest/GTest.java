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

    public static String makePresentableHTML(String projectName) {
        StringBuilder returnable = new StringBuilder();
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


            returnable = new StringBuilder("<!DOCTYPE html>\n" +
                    "<html lang=\"en\">\n" +
                    "<head>\n" +
                    "    <meta charset=\"UTF-8\">\n" +
                    "    <title>Title</title>\n" +
                    "</head>\n" +
                    "<body style=\"background-color: #DCDCDC\">\n" +
                    "<div style=\"margin: 50pt; background-color: white; padding: 20pt\">\n" +
                    "    <div style=\"text-align: center; margin-bottom: 30pt\"><h1>" + testName + "</h1><p>" + testDescription + "</p></div>\n" +
                    "    <hr>\n");
        returnable.append("<div class=\"question\">\n");
        for (int i = 0; i < GTest.returnQuestionsLength(projectName, 1); i++) {
            returnable.append("<div class=\"name\"><span style=\"font-weight: normal\">" + String.format("%d) ", i + 1) + "</span> ").append(questionName[i]).append("</div>\n<div class=\"extra\">").append(questionExtra[i]).append("</div>\n").append("<div class=\"answers\">\n");
            for (int j = 0; j < GTest.returnQuestionAnswersLength(projectName, 1, i); j++) {
            returnable.append(String.format("%d) ", j + 1)).append(questionAnswers[i][j]).append("<br>");
            }
            returnable.append("</div>");
        }
        returnable.append("</div></div></body></html>");


        return returnable.toString();
    }

    private String returnHTMLedQuestions(String projectName, String[] questionExtra, String[] questionName, String[][] questionAnswers) {
        try {
            StringBuilder sb = new StringBuilder();
            BufferedReader br = new BufferedReader(new FileReader("Projects/" + projectName + "/data-presentable.json"));
            String line = br.readLine();
            while (line != null) {
                sb.append(line);
                line = br.readLine();
            }
            br.close();
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
        String returnable = "" +
                "" +
                "" +
                "";
        return null;
    }

}
