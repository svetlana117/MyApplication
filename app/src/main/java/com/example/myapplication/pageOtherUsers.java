package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;

import java.util.ArrayList;
import java.util.List;

public class pageOtherUsers extends AppCompatActivity implements View.OnClickListener{


    TextView etAge, etNick, etInfo;
    DBHelper dbHelper;
    SQLiteDatabase database;
    String  userID;

    private RatingBar ratingBarYours;
    private RatingBar ratingBarAll;

    private Button buttonSubmit;


    private TextView textViewYourCurrentRating;
    private TextView textViewRatingCount;
    private TextView textViewSumAllRating;
    private TextView textViewAverageAllRating;

    private List<Float> allRatings = new ArrayList<Float>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_other_users);
        Intent intent =getIntent();
        userID=intent.getStringExtra("userID");
        etNick=findViewById(R.id.etNick);
        etAge=findViewById(R.id.etAge);
        etInfo=findViewById(R.id.etInfo);
        buttonSubmit=findViewById(R.id.button_submit);
        buttonSubmit.setOnClickListener(this);
        dbHelper = new DBHelper(this);
        database = dbHelper.getWritableDatabase();
        UpdateTable();

        this.buttonSubmit = (Button) this.findViewById(R.id.button_submit);
        this.ratingBarYours = (RatingBar) this.findViewById(R.id.ratingBar_yours);
        this.ratingBarAll = (RatingBar) this.findViewById(R.id.ratingBar_all);

        this.textViewYourCurrentRating = (TextView) this.findViewById(R.id.textView_yourCurrentRating);
        this.textViewRatingCount= (TextView) this.findViewById(R.id.textView_ratingCount);
        this.textViewSumAllRating= (TextView) this.findViewById(R.id.textView_sumAllRating);
        this.textViewAverageAllRating= (TextView) this.findViewById(R.id.textView_averageAllRating);
        this.ratingBarYours.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                textViewYourCurrentRating.setText("Текущая оценка:  " + rating);
            }
        });

    }



    public void UpdateTable() {

        Cursor cursor = database.query(DBHelper.TABLE_USERS, null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            int idIndex=cursor.getColumnIndex(DBHelper.KEY_ID);
            int nickIndex = cursor.getColumnIndex(DBHelper.KEY_NICK);
            int ageIndex = cursor.getColumnIndex(DBHelper.KEY_AGE);
            int infoIndex = cursor.getColumnIndex(DBHelper.KEY_INFO);
                TableLayout dbRUTout = findViewById(R.id.dbOutPut);
                dbRUTout.removeAllViews();
                do{
                    if (cursor.getString(idIndex).equals(userID))
                    {
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
                    outputNick.setLayoutParams(params);
                    params.width=5;
                    outputNick.setText(cursor.getString(nickIndex));
                    dbOutPutRow.addView(outputNick);

                    TextView outputAge = new TextView(this);
                    outputAge.setLayoutParams(params);
                    params.weight=5.0f;
                    outputAge.setText(cursor.getString(ageIndex));
                    dbOutPutRow.addView(outputAge);


                    TextView outputInfo = new TextView(this);
                    outputInfo.setLayoutParams(params);
                    params.weight=5.0f;
                    outputInfo.setText(cursor.getString(infoIndex));
                    dbOutPutRow.addView(outputInfo);

                    dbRUTout.addView(dbOutPutRow);
                    }
                }while(cursor.moveToNext());

        }

        cursor.close();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_submit:
                float rating = this.ratingBarYours.getRating();
                this.allRatings.add(rating);

                int ratingCount = this.allRatings.size();
                float ratingSum = 0f;
                for(Float r: this.allRatings)  {
                    ratingSum += r;
                }
                float averageRating = ratingSum / ratingCount;


                this.textViewRatingCount.setText("Количество оценок:  " + ratingCount);
                this.textViewSumAllRating.setText("Сумма всех оценок:  " + ratingSum);
                this.textViewAverageAllRating.setText("Среднее значение всех оценок:  " + averageRating);

                float ratingAll = this.ratingBarAll.getNumStars() * averageRating / this.ratingBarYours.getNumStars() ;
                this.ratingBarAll.setRating(ratingAll);
            break;

        }

    }

}