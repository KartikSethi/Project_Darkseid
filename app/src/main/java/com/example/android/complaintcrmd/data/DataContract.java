package com.example.android.complaintcrmd.data;

import android.provider.BaseColumns;

/**
 * Created by ayush on 23/6/16.
 */
public class DataContract {
    public static final class Pending implements BaseColumns

    {
        public static final String TABLE_NAME = "pending";
        public static final String JSON = "json";
    }

    public static final class Completed implements BaseColumns{
        public static final String TABLE_NAME="completed";
        public static final String JSON="json";
        public static final String REPORT="report";
    }
}

