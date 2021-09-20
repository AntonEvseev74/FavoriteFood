package ru.evant.favoritefood.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    // создать БД
    DBHelper(Context context) {
        super(context, DBConstants.DB_NAME, null, DBConstants.DB_VERSION);
    }

    // создать таблицу
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DBConstants.TABLE_STRUCTURE);
    }

    // удалить таблицу, и создать новую
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DBConstants.DROP_TABLE);
        onCreate(db);
    }
}
