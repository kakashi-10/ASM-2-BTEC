package com.example.myapplication2.SQLite;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.myapplication2.Model.User;

public class UserDB extends SQLiteOpenHelper {
    private static  final String DB_NAME = "asm_expense";

    private static final int DB_VERSION = 1;
    private static final String TABLE_NAME = "user";

    private static final  String ID_COL ="id";
    private static final String USERNAME_COL ="username";
    private static  final String PASSWORD_COL = "password";
    private static final String EMAIL_COL = "email";
    private static final String PHONE_COL ="phone";
    public UserDB(@Nullable Context context) {
        super(context,  DB_NAME,null , DB_VERSION );
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // tao bang du lieu
        String query = "CREATE TABLE " + TABLE_NAME + " ("
                + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + USERNAME_COL + " VARCHAR(60), "
                + PASSWORD_COL + " VARCHAR(200), "
                + EMAIL_COL + " VARCHAR(60), "
                + PHONE_COL + " VARCHAR(30))";


        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public long addNewUser(String username, String password, String email, String phone){
        // xu li luu thong tin nguoi dung dang ky tai khoan
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(USERNAME_COL, username);
        values.put(PASSWORD_COL, password);
        values.put(EMAIL_COL, email);
        values.put(PHONE_COL, phone);
        long insert = db.insert(TABLE_NAME, null,values);
        db.close();
        return insert; // - 1 insert that bai
    }

    @SuppressLint("Range")
    public User getSingleUser(String username, String password){
        Cursor cursor = null;
        User user = new User();
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            String[] columns = {ID_COL, USERNAME_COL, EMAIL_COL, PHONE_COL };
            String conditions = USERNAME_COL + " = ? " + " AND " + PASSWORD_COL + " = ? ";
            String[] params = { username, password };
            cursor = db.query(
                    TABLE_NAME,
                    columns,
                    conditions,
                    params,
                    null,
                    null,
                    null
            );
            // select id, username, email, phone where username = ? AND password = ?; (cau nay tuong duong voi cau tren)
            if (cursor.getCount() > 0){
                cursor.moveToFirst();
                // do du lieu vao User Model
                user.setId(cursor.getInt(cursor.getColumnIndex(ID_COL)));
                user.setUsername(cursor.getString(cursor.getColumnIndex(USERNAME_COL)));
                user.setEmail(cursor.getString(cursor.getColumnIndex(EMAIL_COL)));
                user.setPhone(cursor.getString(cursor.getColumnIndex(PHONE_COL)));
            }
            db.close();
        } finally {
            assert cursor != null;
            cursor.close();
        }
        return user;
    }
}
