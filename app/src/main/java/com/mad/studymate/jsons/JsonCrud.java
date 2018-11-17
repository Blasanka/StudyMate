package com.mad.studymate.jsons;

import android.content.Context;

import com.google.gson.Gson;
import com.mad.studymate.cardview.model.Question;

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

    public void insert(String title, List<Question> questionList) {

        JSONObject parent = new JSONObject();
        try {
            //array of questions to parent node
            parent.put(title, addQuizData(questionList));

        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonHandler json = new JsonHandler(context);
//                json.writeObject(object, "quiz1");
        json.writeJsonFile(parent.toString(), title);

    }

    private JSONArray addQuizData(List<Question> questionList) {
        JSONArray questionsArray = new JSONArray();

        for (Question q : questionList) {

            Gson gson = new Gson();
            String json = gson.toJson(q);
            try {
                JSONObject questionObj = new JSONObject(json);
                //question obj
                questionsArray.put(questionObj);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return questionsArray;
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
