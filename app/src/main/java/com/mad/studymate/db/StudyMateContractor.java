package com.mad.studymate.db;

import android.provider.BaseColumns;

public class StudyMateContractor {
    private StudyMateContractor() {}

    //note
    public static class NoteEntry implements BaseColumns {
        public static final String TABLE_NAME = "notes";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_TAG = "tags";
        public static final String COLUMN_NAME_PARAGRAPH_COUNT = "paragraphs_count";
        public static final String COLUMN_NAME_PARAGRAPH_1 = "paragraph_1";
        public static final String COLUMN_NAME_PARAGRAPH_2 = "paragraph_2";
        public static final String COLUMN_NAME_PARAGRAPH_3 = "paragraph_3";
        public static final String COLUMN_NAME_PARAGRAPH_4 = "paragraph_4";
        public static final String COLUMN_NAME_PARAGRAPH_5 = "paragraph_5";
    }

    public static class TaskEntry implements BaseColumns {
        public static final String TABLE_NAME = "tasks";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_TIME_PERIOD = "time_period";
        public static final String COLUMN_NAME_PRIORITY_NO = "priority_no";
        public static final String COLUMN_NAME_DESCRIPTION = "description";
        public static final String COLUMN_NAME_IS_DONE = "is_done";
    }

    //TODO: QuizEntry not completed
    public static class QuizEntry implements BaseColumns {
        public static final String TABLE_NAME = "quizes";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_TAG = "tag";
        public static final String COLUMN_NAME_TYPE = "type";
        public static final String COLUMN_NAME_QUESTIONS_COUNT = "questions_count";
        public static final String COLUMN_NAME_ATTEMPT_COUNT = "attempt_count";
        public static final String COLUMN_NAME_QUIZ_SCORES = "quiz_scores";
    }
}
