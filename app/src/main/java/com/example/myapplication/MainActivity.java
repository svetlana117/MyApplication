package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView LoginTxt, PasswordTxt;
    Button SingInBtn, LogInBtn;

    DBHelper dbHelper;
    SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LoginTxt=findViewById(R.id.LoginTxt);
        PasswordTxt=findViewById(R.id.PasswordTxt);

        LogInBtn=findViewById(R.id.SingInBtn);
        LogInBtn.setOnClickListener(this);
        SingInBtn=findViewById(R.id.LogInBtn);
        SingInBtn.setOnClickListener(this);

        dbHelper = new DBHelper(this);
        database = dbHelper.getWritableDatabase();

    }
    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.SingInBtn:
                Cursor logCursor=database.query(DBHelper.TABLE_USERS, null,null,null,null,null,null);

                boolean logged=false;
                if(logCursor.moveToFirst()) {
                    int usernameIndex = logCursor.getColumnIndex(DBHelper.KEY_LOGIN);
                    int passwordIndex = logCursor.getColumnIndex(DBHelper.KEY_PASSWORD);
                    do {
                        if (LoginTxt.getText().toString().equals(logCursor.getString(usernameIndex)) && PasswordTxt.getText().toString().equals(logCursor.getString(passwordIndex))) {
                                param.Login=LoginTxt.getText().toString();
                                startActivity(new Intent(this, page.class));
                                logged = true;
                                break;
                        }
                    } while (logCursor.moveToNext());
                }
                logCursor.close();
                if(!logged) Toast.makeText(this,"?????????????? ???????????????????????? ???? ????????????????????", Toast.LENGTH_LONG).show();
                break;

            case R.id.LogInBtn:
                Cursor signCursor=database.query(DBHelper.TABLE_USERS, null,null,null,null,null,null);
                boolean finded = false;
                if(signCursor.moveToFirst()){
                    int usernameIndex=signCursor.getColumnIndex(DBHelper.KEY_LOGIN);
                    do{
                        if(LoginTxt.getText().toString().equals(signCursor.getString(usernameIndex))){
                            Toast.makeText(this,"???????? ???????????????????????? ?????? ??????????????????????????????", Toast.LENGTH_LONG).show();
                            finded=true;
                            break;
                        }
                    }while (signCursor.moveToNext());
                }
                if(!finded){
                    ContentValues contentValues=new ContentValues();
                    contentValues.put(DBHelper.KEY_LOGIN, LoginTxt.getText().toString());
                    contentValues.put(DBHelper.KEY_PASSWORD, PasswordTxt.getText().toString());
                    contentValues.put(DBHelper.KEY_NICK,"user nick");
                    contentValues.put(DBHelper.KEY_AGE,"user age");
                    contentValues.put(DBHelper.KEY_INFO,"user info");
                    database.insert(DBHelper.TABLE_USERS,null, contentValues);
                    Toast.makeText(this,"?????????????????????? ???????????? ??????????????", Toast.LENGTH_SHORT).show();
                }
                signCursor.close();
                break;


        }


    }


}
