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
import android.widget.Toast;

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
            super(context, "Email",null , 1);
        }
        @Override
        public void onCreate(SQLiteDatabase db) {
            // TODO 创建数据库后，对数据库的操作
            db.execSQL("create table Email(name varchar(20),email varchar(20) primary key,phone_num varchar(20))");
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

    public void onClickWrite(View view) {
        Cursor cursor = db.rawQuery("select * from Email", null);
        boolean i = false;
        while (cursor.moveToNext()) {
            String email1 = cursor.getString(cursor.getColumnIndex("email"));
            if (email1.equals(email.getText().toString())) {
                Toast.makeText(getApplicationContext(), "This email address has been signed up.", Toast.LENGTH_LONG).show();
                i = true;
            }
        }
        if (i == false) {
            db.execSQL("insert into Email values(" + "'" + name.getText() + "','" + email.getText() + "','" + num.getText() + "'" + ")");
            Toast.makeText(getApplicationContext(), "Writed.", Toast.LENGTH_LONG).show();
        }
    }

    public void onClickRead(View view){
        Cursor cursor=db.rawQuery("select * from Email",null);
        String str="";
        while(cursor.moveToNext()){
            String name=cursor.getString(cursor.getColumnIndex("name"));
            String email=cursor.getString(cursor.getColumnIndex("email"));
            String num=cursor.getString(cursor.getColumnIndex("phone_num"));
            str=str+"name:"+name+"\nemail:"+email+"\nphone number:"+num+"\n\n";
        }
        text.setText(str);
    }

    public void onClickUpdate(View view) {
        Cursor cursor = db.rawQuery("select * from Email", null);
        boolean i = false;
        while (cursor.moveToNext()) {
            String email1 = cursor.getString(cursor.getColumnIndex("email"));
            if (email1.equals(email.getText().toString())) {
                i = true;
            }
        }
        if (i == false) {
            Toast.makeText(getApplicationContext(), "This email address doesn't exist.", Toast.LENGTH_LONG).show();
        }
        else {
            db.execSQL("update Email set name='" + name.getText() + "',phone_num='" + num.getText() + "' where email='" + email.getText() + "'");
            Toast.makeText(getApplicationContext(), "Updated.", Toast.LENGTH_LONG).show();
        }
    }

    public void onClickRemove(View view){
        Cursor cursor = db.rawQuery("select * from Email", null);
        boolean i = false;
        while (cursor.moveToNext()) {
            String email1 = cursor.getString(cursor.getColumnIndex("email"));
            if (email1.equals(email.getText().toString())) {
                i = true;
            }
        }
        if (i == false) {
            Toast.makeText(getApplicationContext(), "This email address doesn't exist.", Toast.LENGTH_LONG).show();
        }
        else {
            db.execSQL("delete from Email where email='"+email.getText()+"'");
            Toast.makeText(getApplicationContext(), "Deleted.", Toast.LENGTH_LONG).show();
        }
    }
}
