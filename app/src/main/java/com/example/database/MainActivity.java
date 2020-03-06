package com.example.database;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Context context;
    SQLiteDatabase db;
    EditText name,email,num;
    TextView text;
    DatabaseHelper helper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context=this.getApplicationContext();
        helper=new DatabaseHelper();
        db=helper.getWritableDatabase();
        name=findViewById(R.id.name);
        email=findViewById(R.id.email);
        num=findViewById(R.id.num);
        text=findViewById(R.id.text);
    }

    public class DatabaseHelper extends SQLiteOpenHelper{
        DatabaseHelper()
        {
            super(context, "email",null , 1);
        }
        @Override
        public void onCreate(SQLiteDatabase db) {
            // TODO 创建数据库后，对数据库的操作
            db.execSQL("create table email(name varchar(20),email varchar(20) primary key,phone_num varchar(20))");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            // TODO 更改数据库版本的操作

        }

        @Override
        public void onOpen(SQLiteDatabase db) {
            super.onOpen(db);
            // TODO 每次成功打开数据库后首先被执行
        }
    }

    public void onClickWrite(View view){
        db.execSQL("insert into email values("+"'"+name.getText()+"','"+email.getText()+"','"+num.getText()+"'"+")");
    }

    public void onClickRead(View view){
        Cursor cursor=db.rawQuery("select * from email",null);
        while(cursor.moveToNext()){
            String name=cursor.getString(cursor.getColumnIndex("name"));
            String email=cursor.getString(cursor.getColumnIndex("email"));
            String num=cursor.getString(cursor.getColumnIndex("num"));
            text.setText(name+email+num);
        }
    }

    public void onClickUpdate(View view){
        db.execSQL("update email set name="+name.getText()+"num="+num.getText()+"where email="+email.getText());
    }

    public void onClickRemove(View view){
        db.execSQL("delete from email where email="+email.getText());
    }
}
