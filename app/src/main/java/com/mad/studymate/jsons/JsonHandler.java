package com.mad.studymate.jsons;

import android.content.Context;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class JsonHandler {

    private static final String FILE_NAME = "quiz.json";
    FileReader fileReader = null;
    FileWriter fileWriter = null;
    BufferedReader bufferedReader = null;
    BufferedWriter bufferedWriter = null;
    String response = null;
    private Context parent;

    public JsonHandler(Context c) {
        parent = c;
    }

    public void writeJsonFile(String content) {

        File file = new File(parent.getFilesDir(), FILE_NAME);
        if (!file.exists()) {
            try {
                file.createNewFile();
                fileWriter = new FileWriter(file.getAbsoluteFile());
                bufferedWriter = new BufferedWriter(fileWriter);
                bufferedWriter.write(content);
                bufferedWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    public String readJsonFile() {

        File file = new File(parent.getFilesDir(), FILE_NAME);
        StringBuffer output = new StringBuffer();

        try {
            fileReader = new FileReader(file.getAbsolutePath());
            bufferedReader = new BufferedReader(fileReader);
            String line = "";

            while ((line = bufferedReader.readLine()) != null) {
                output.append(line + "\n");
            }

            response = output.toString();
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return response;
    }

    public void deleteFile() {
        new File(parent.getFilesDir(), FILE_NAME).delete();
    }
}