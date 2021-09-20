package ru.evant.favoritefood.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import ru.evant.favoritefood.adapter.ListItem;

public class DBManager {
    private DBHelper dbHelper;
    private SQLiteDatabase db;

    public DBManager(Context context) {
        dbHelper = new DBHelper(context);
    }

    /* открыть БД в режиме записи */
    public void openDB() {
        db = dbHelper.getWritableDatabase();
    }

    /* записать данные в БД */
    public void insertDB(String title, String description, String uri_image) {
        // создать контент для помещения в БД
        // в контент поместить данные
        ContentValues values = new ContentValues();
        values.put(DBConstants.TITLE, title);
        values.put(DBConstants.DESCRIPTION, description);
        values.put(DBConstants.URI_IMAGE, uri_image);

        db.insert(DBConstants.TABLE_NAME, null, values); // поместить контент в БД
    }

    /* возвращает заголовки всех заметок из записной книжки */
    // public List<ListItem> getFromDB() {
    //     // создать list, который будем возвращать
    //     List<ListItem> list = new ArrayList<>();
    //     // создать курсор выборки из таблицы (запрос)
    //     Cursor cursor = db.query(DBConstants.TABLE_NAME, null, null, null, null, null, null);
    //     // считать данные с курсора
    //     while (cursor.moveToNext()) {
    //         ListItem item = new ListItem();
    //         String title = cursor.getString(cursor.getColumnIndex(DBConstants.TITLE));
    //         String description = cursor.getString(cursor.getColumnIndex(DBConstants.DESCRIPTION));
    //         String uri_image = cursor.getString(cursor.getColumnIndex(DBConstants.URI_IMAGE));
    //         item.setTitle(title);
    //         item.setDescription(description);
    //         item.setUriImage(uri_image);
    //         list.add(item);
    //     }
    //     cursor.close(); // закрыть курсор
    //     return list;
    // }


    /* выбрать из ДБ по заголовку (title) */
    public void getFromDB(String searchText, OnDataReceived onDataReceived) {
        // создать list, который будем возвращать
        List<ListItem> list = new ArrayList<>();
        // запрос: искать в  столбце title, что указано в аргументах (selectionArgs)
        String selection = DBConstants.TITLE + " like ?";
        // аргумент запроса для поиска в selection
        String[] selectionArgs = new String[]{"%" + searchText + "%"};
        // создать курсор выборки из таблицы (запрос)
        Cursor cursor = db.query(DBConstants.TABLE_NAME, null, selection, selectionArgs, null, null, null);
        // считать данные с курсора
        while (cursor.moveToNext()) {
            ListItem item = new ListItem();
            int _id = cursor.getInt(cursor.getColumnIndex(DBConstants._ID));
            String title = cursor.getString(cursor.getColumnIndex(DBConstants.TITLE));
            String description = cursor.getString(cursor.getColumnIndex(DBConstants.DESCRIPTION));
            String uriImage = cursor.getString(cursor.getColumnIndex(DBConstants.URI_IMAGE));
            item.setId(_id);
            item.setTitle(title);
            item.setDescription(description);
            item.setUriImage(uriImage);
            list.add(item);
        }
        cursor.close(); // закрыть курсор
        onDataReceived.onReceived(list);
    }

    // обновление (изменение заметки)
    public void update(int id, String title, String description, String uri_image) {
        String selection = DBConstants._ID + "=" + id;
        ContentValues values = new ContentValues();
        values.put(DBConstants.TITLE, title);
        values.put(DBConstants.DESCRIPTION, description);
        values.put(DBConstants.URI_IMAGE, uri_image);

        db.update(DBConstants.TABLE_NAME, values, selection, null);
    }

    // удаление
    public void delete(int id) {
        String selection = DBConstants._ID + "=" + id;
        db.delete(DBConstants.TABLE_NAME, selection, null);
    }

    /* закрыть БД */
    public void closeDB() {
        dbHelper.close();
    }
}
