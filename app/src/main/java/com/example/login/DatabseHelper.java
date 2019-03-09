package com.example.login;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

public class DatabseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME="register.db";
    public static final String TABLE_NAME="registeruser";
    public static final String COL_1="id";
    public static final String COL_2="username";
    public static final String COL_3="password";
    public DatabseHelper( Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
          db.execSQL("CREATE TABLE registeruser(ID INTEGER PRIMARY KEY AUTOINCREMENT,username TEXT,password TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
                   db.execSQL(" DROP TABLE IF EXISTS "+TABLE_NAME);
                   onCreate(db);
    }
    public long adduser(String user,String password)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("username",user);
        contentValues.put("password",password);
        long res=db.insert("registeruser",null,contentValues);
        db.close();
        return res;
    }
    public boolean checkuser(String User ,String Password)
    {
         SQLiteDatabase db=this.getReadableDatabase();
         String[] coloums={COL_1};
         String selection=COL_2+"=?"+" and "+COL_3+"=?";
         String[] selectionargs={User,Password};
        Cursor cursor=db.query(TABLE_NAME,coloums,selection,selectionargs,null,null,null);
        int count=cursor.getCount();
        if(count>0)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
}
