package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.os.Bundle;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.PreparedStatement;

public class pageUser extends AppCompatActivity implements View.OnClickListener {

    EditText etLog,etPass,etAge, etNick, etInfo;
    Button btnSave;
    DBHelper dbHelper;
    SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_user);

        etLog = findViewById(R.id.etLogin);
        etPass = findViewById(R.id.etPass);
        etAge = findViewById(R.id.etAge);
        etNick = findViewById(R.id.etNick);
        etInfo = findViewById(R.id.etInfo);

        btnSave=findViewById(R.id.btnSave);
        btnSave.setOnClickListener(this);

        dbHelper = new DBHelper(this);
        database = dbHelper.getWritableDatabase();

        Cursor cursor=database.query(DBHelper.TABLE_USERS, null,null,null,null,null,null);

        if (cursor.moveToFirst()) {
            int loginIndex = cursor.getColumnIndex(DBHelper.KEY_LOGIN);
            int passwordIndex = cursor.getColumnIndex(DBHelper.KEY_PASSWORD);
            int ageIndex = cursor.getColumnIndex(DBHelper.KEY_AGE);
            int nickIndex = cursor.getColumnIndex(DBHelper.KEY_NICK);
            int infoIndex = cursor.getColumnIndex(DBHelper.KEY_INFO);
            do{
                if(param.Login.equals(cursor.getString(loginIndex)))
                {

                    etLog.setText(cursor.getString(loginIndex));
                    etPass.setText(cursor.getString(passwordIndex));
                    if(!cursor.isNull(ageIndex))
                    {
                        etAge.setText(cursor.getString(ageIndex));
                    }
                    if(!cursor.isNull(nickIndex))
                    {
                        etNick.setText(cursor.getString(nickIndex));
                    }
                    if(!cursor.isNull(infoIndex))
                    {
                        etInfo.setText(cursor.getString(infoIndex));
                    }
                    break;
                }

            }while(cursor.moveToNext());
        }

        cursor.close();

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnSave:
                ContentValues ct = new ContentValues();
                ct.put("password",etPass.getText().toString());
                ct.put("nick",etNick.getText().toString());
                ct.put("age",etAge.getText().toString());
                ct.put("info",etInfo.getText().toString());
                database.update(DBHelper.TABLE_USERS,ct,"login = ?", new String[]{etLog.getText().toString()});
//                database.execSQL("Update "+DBHelper.TABLE_USERS+" Set "+ DBHelper.KEY_PASSWORD+ " = "+etPass.getText().toString()
//                        + DBHelper.KEY_NICK + " = "+etNick.getText().toString() + DBHelper.KEY_AGE + " = "+etAge.getText().toString()
//                        + DBHelper.KEY_INFO + " = "+etInfo.getText().toString()+" Where " +DBHelper.KEY_LOGIN +" Like( " +etLog.getText().toString()+"); ");

                Toast.makeText(this,"Изменения сохранены", Toast.LENGTH_LONG).show();

                break;
            default:

                break;
        }
    }
}