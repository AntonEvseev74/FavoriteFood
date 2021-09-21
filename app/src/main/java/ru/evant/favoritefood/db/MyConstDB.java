package ru.evant.favoritefood.db;

public final class MyConstDB {

    public static final String LIST_ITEM_INTENT = "list_item_intent";
    public static final String EDIT_STATE = "edit_state";

    static final String DB_NAME = "my_db.db";    // имя базы данных (далее - БД)
    static final int DB_VERSION = 2;             // версия БД

    static final String TABLE_NAME = "my_table";     // имя таблицы
    static final String _ID = "_id";                 // имя столбца, хранит: идентификационный номер
    static final String TITLE = "title";             // имя столбца, хранит: заголовок
    static final String RECIPE = "recipe";           // имя столбца, хранит рецепт из ингридиетов
    static final String DESCRIPTION = "description"; // имя столбца, хранит: описание, способ приготовления
    static final String URI_IMAGE = "uri_image";     // имя столбца, хранит: ссылку на картинку

    /* для создания таблицы
     * CREATE TABLE IF NOT EXIST - создать таблицу, если она не существует
     */
    static final String TABLE_STRUCTURE =
            "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
                    _ID + " INTEGER PRIMARY KEY," +
                    TITLE + " TEXT," +
                    RECIPE + " TEXT," +
                    DESCRIPTION + " TEXT," +
                    URI_IMAGE + " TEXT);";

    /* для удаления таблицы
     * DROP TABLE IF EXISTS - удалить таблицу, если она существует
     */
    static final String DROP_TABLE =
            "DROP TABLE IF EXISTS " + TABLE_NAME;

}
