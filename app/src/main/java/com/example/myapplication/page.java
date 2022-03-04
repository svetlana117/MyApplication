package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class page extends AppCompatActivity implements View.OnClickListener{

    TextView LoginTxt, PasswordTxt;
    Button profile;

    DBHelper dbHelper;
    SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page);

        LoginTxt=findViewById(R.id.LoginTxt);
        PasswordTxt=findViewById(R.id.PasswordTxt);
        profile=findViewById(R.id.profile);
        profile.setOnClickListener(this);
        dbHelper = new DBHelper(this);
        database = dbHelper.getWritableDatabase();
        UpdateTable();
    }

    public void UpdateTable(){

        Cursor cursor = database.query(DBHelper.TABLE_USERS, null, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            int nickIndex = cursor.getColumnIndex(DBHelper.KEY_NICK);
            int ageIndex = cursor.getColumnIndex(DBHelper.KEY_AGE);
            int idIndex = cursor.getColumnIndex(DBHelper.KEY_ID);
            //int loginIndex = cursor.getColumnIndex(DBHelper.KEY_LOGIN);
            //int passwordIndex = cursor.getColumnIndex(DBHelper.KEY_PASSWORD);
            TableLayout dbOutPut = findViewById(R.id.dpOutput);
            dbOutPut.removeAllViews();
            do{
                TableRow dbOutPutRow = new TableRow (this);
                dbOutPutRow.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT));
                LinearLayout.LayoutParams params = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                        TableRow.LayoutParams.MATCH_PARENT);

                ImageView imageView = new ImageView(this);
                params.weight=3.0f;
                imageView.setImageResource(R.drawable.user);
                dbOutPutRow.addView(imageView);

                TextView outputNick = new TextView(this);
                params.weight=3.0f;
                outputNick.setLayoutParams(params);
                outputNick.setText(cursor.getString(nickIndex));
                dbOutPutRow.addView(outputNick);

                TextView outputAge = new TextView(this);
                params.weight=3.0f;
                outputAge.setLayoutParams(params);
                outputAge.setText(cursor.getString(ageIndex));
                dbOutPutRow.addView(outputAge);

                Button PageUserBtn = new Button(this);
                PageUserBtn.setOnClickListener(this);
                params.weight=1.0f;
                PageUserBtn.setLayoutParams(params);
                PageUserBtn.setText("перейти");
                PageUserBtn.setId(cursor.getInt(idIndex));
                dbOutPutRow.addView(PageUserBtn);

                dbOutPut.addView(dbOutPutRow);
            }while(cursor.moveToNext());
        }

        cursor.close();

    }
    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.profile:
                startActivity(new Intent(this, pageUser.class));
                break;
            default:
                Intent intent = new Intent(this, pageOtherUsers.class);
                intent.putExtra("userID",String.valueOf(view.getId()));
                startActivity(intent);
                break;

        }

    }
}