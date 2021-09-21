package ru.evant.favoritefood.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDBHelper extends SQLiteOpenHelper {

    // создать БД
    MyDBHelper(Context context) {
        super(context, MyConstDB.DB_NAME, null, MyConstDB.DB_VERSION);
    }

    // создать таблицу
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(MyConstDB.TABLE_STRUCTURE);
    }

    // удалить таблицу, и создать новую
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(MyConstDB.DROP_TABLE);
        onCreate(db);
    }
}
