package com.example.jsion_samples;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "Mainactivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        parseSingleElement();
        buildTest1();
        parseEntrieJson();
    }

    public void parseSingleElement() {
        Gson gson = new Gson();
        int i = gson.fromJson("100", int.class);              //100
        double d = gson.fromJson("\"99.99\"", double.class);  //99.99
        boolean b = gson.fromJson("true", boolean.class);     // true
        String str = gson.fromJson("String", String.class);   // String
        Log.i(TAG, " i:" + i + " d:" + d + " b:" + b + " str:" + str);
    }

    public void buildTest1() {
        Gson gson = new Gson();
        String jsonNumber = gson.toJson(100);       // 100
        String jsonBoolean = gson.toJson(false);    // false
        String jsonString = gson.toJson("String"); //"String"
        Log.i(TAG, " jsonNumber:" + jsonNumber + " jsonBoolean:" + jsonBoolean + " jsonString:" + jsonString);
    }

    public void parseEntrieJson() {
        Gson gson = new Gson();
        String jsonString = "{\"name\":\"张三\",\"age\":24}";
        User user = gson.fromJson(jsonString, User.class);
        Log.i(TAG, user.getName() + user.age);
    }

    public class User {
        //省略其它
        public String name;
        public int age;
        public String emailAddress;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public String getEmailAddress() {
            return emailAddress;
        }

        public void setEmailAddress(String emailAddress) {
            this.emailAddress = emailAddress;
        }
    }
}
