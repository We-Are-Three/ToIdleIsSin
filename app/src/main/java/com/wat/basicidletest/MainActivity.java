package com.wat.basicidletest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CopyOnWriteArrayList;

public class MainActivity extends AppCompatActivity {
    Program program;
    TextView text;
    EditText com;
    Button button;
    static CopyOnWriteArrayList<String> outputs = new CopyOnWriteArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final SharedPreferences mPrefs = getPreferences(MODE_PRIVATE);
        setContentView(R.layout.activity_main);
        text = (TextView) findViewById(R.id.text);
        com = (EditText) findViewById(R.id.edit);
        outputs.add("");
        outputs.add("");
        outputs.add("");
        outputs.add("");
        program = new Program();
        AsyncTask<Void, String, Void> a = new AsyncTask<Void, String, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                program.mainl(text, mPrefs);
                return null;
            }

            @Override
            protected void onProgressUpdate(String... values) {
                super.onProgressUpdate(values);
            }
        };

        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s = com.getText().toString();
                program.c = s;
                com.setText("");
            }
        });
        a.execute();


    }

    public static void addString(String s, final TextView textView){
        outputs.set(3, s);
        final String one = outputs.get(0);
        final String two = outputs.get(1);
        final String three = outputs.get(2);
        final String four = outputs.get(3);
        textView.post(new Runnable() {
            @Override
            public void run() {
                textView.setText(one + two + three + four);
            }
        });
        outputs.set(0, two);
        outputs.set(1, three);
        outputs.set(2, four);
        outputs.set(3, "");
    }

}
