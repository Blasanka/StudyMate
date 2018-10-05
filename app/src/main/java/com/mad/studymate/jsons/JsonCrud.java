package com.mad.studymate.jsons;

import android.content.Context;
import android.widget.EditText;
import android.widget.RadioButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonCrud {
    Context context;

    public JsonCrud(Context context) {
        this.context = context;
    }

    public void insert(String title, EditText questionEt1, EditText questionEt2,
                       RadioButton radioButtonTrue1, RadioButton radioButtonTrue2,
                       RadioButton radioButtonFalse1, RadioButton radioButtonFalse2) {
        //TODO: fixed size of components and json values are from fixed varables
        JSONObject parent = new JSONObject();

        JSONObject question = new JSONObject();

        JSONObject question2 = new JSONObject();

        JSONArray questionsArray = new JSONArray();

        JSONArray answersArray = new JSONArray();
        JSONArray correctAnswersArray = new JSONArray();

        JSONArray answersArray2 = new JSONArray();
        JSONArray correctAnswersArray2 = new JSONArray();
        try {

            //question 1


            //question
            question.put("question", questionEt1.getText().toString());

            //All answers to one question
            answersArray.put(radioButtonTrue1.isChecked());
            answersArray.put(radioButtonFalse1.isChecked());
            question.put("answers", answersArray);

            //correct answers to one question
            correctAnswersArray.put(true);
            question.put("correctAnswers", correctAnswersArray);


            //question 2

            ////question
            question2.put("question", questionEt2.getText().toString());

            //All answers to one question
            answersArray2.put(radioButtonTrue2.isChecked());
            answersArray2.put(radioButtonFalse2.isChecked());
            question2.put("answers", answersArray2);

            //correct answers to one question
            correctAnswersArray2.put(radioButtonFalse2.isChecked());
            question2.put("correctAnswers", correctAnswersArray2);

            //question
            questionsArray.put(question);
            questionsArray.put(question2);

            //array of questions to parent node
            parent.put(title, questionsArray);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonHandler json = new JsonHandler(context);
//                json.writeObject(object, "quiz1");
        json.writeJsonFile(parent.toString(), title);

    }

    public List<String> read(String quizTitle) {

        List<String> list = new ArrayList();
        JsonHandler json = new JsonHandler(context);
        JSONObject parentNode = null;
        try {
            parentNode = new JSONObject(json.readJsonFile(quizTitle));
            JSONObject jsonObj = parentNode;
            JSONArray jsonArray = jsonObj.getJSONArray(quizTitle);
            JSONObject parentObj = new JSONObject(jsonArray.get(0).toString());
            list.add(parentObj.get("question").toString());

            JSONObject parentObjQuiz2 = new JSONObject(jsonArray.get(1).toString());
            list.add(parentObjQuiz2.get("question").toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }
}
