package com.jacinto.storage_4itb;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MainActivity extends Activity {
    SharedPreferences sharedpreferences;
    EditText textmsg;
    public static final String Textmsg = "nameKey";
    static final int READ_BLOCK_SIZE = 100;
    public static final String mypreference = "mypref";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedpreferences = getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);
        textmsg = (EditText) findViewById(R.id.editText1);
    }

    public void SaveMessage(View v) {
        try {
            FileOutputStream fileoutput = openFileOutput("text.txt", MODE_PRIVATE);
            OutputStreamWriter outputWriter = new OutputStreamWriter(fileoutput);
            outputWriter.write(textmsg.getText().toString());
            outputWriter.close();
            Toast.makeText(getBaseContext(), "Text saved",
                    Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public void DisplayMessage(View v) {

        try {
            FileInputStream fileIn = openFileInput("text.txt");
            InputStreamReader InputRead = new InputStreamReader(fileIn);

            char[] inputBuffer = new char[READ_BLOCK_SIZE];
            String s = "";
            int Read;
            while ((Read = InputRead.read(inputBuffer)) > 0) {

                String readstring = String.copyValueOf(inputBuffer, 0, Read);
                s += readstring;
            }
            InputRead.close();
            textmsg.setText(s);
            Toast.makeText(getBaseContext(), "Text Displayed",
                    Toast.LENGTH_SHORT).show();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void clear(View view) {
        textmsg = (EditText) findViewById(R.id.editText1);
        textmsg.setText("");


    }

    public void Save(View view) {
        String n = textmsg.getText().toString();
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(Textmsg, n);
        editor.commit();
    }

    public void Get(View view) {
        textmsg = (EditText) findViewById(R.id.editText1);

        sharedpreferences = getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);

        if (sharedpreferences.contains(Textmsg)) {
            textmsg.setText(sharedpreferences.getString(Textmsg, ""));
        }

    }
}