package com.PongZhong.querycreditcardofccb;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by peng on 13-9-11.
 */
class dbHelper {

    private static final String USERTABLENAME = "card";
    private SQLiteDatabase cardDatabase;
    private Context mContext;

    public dbHelper(Context context) {
        mContext = context;
        cardDatabase = getDatabase();
    }

    private SQLiteDatabase getDatabase() {
        SqliteHelper db = new SqliteHelper(mContext, USERTABLENAME, null, 1);
        return db.getWritableDatabase();

    }

    private void clearDatabase() {
        cardDatabase.execSQL("delete from card");
    }

    public void insert(String cardId) {
        clearDatabase();
        ContentValues cv = new ContentValues();
        cv.put("card_id", cardId);
        cardDatabase.insert(USERTABLENAME, null, cv);
    }

    private Cursor simpleQuery() {
        return cardDatabase.rawQuery("select * from card", null);
    }

    public int update(String cardid, String newId) {
        ContentValues cv = new ContentValues();
        cv.put("card_id", newId);
        return cardDatabase.update(USERTABLENAME, cv, "card_id=?", new String[]{cardid});
    }

    public String getCardId() {
        Cursor c = simpleQuery();
        if (c.getCount() > 0) {
            c.moveToFirst();
            return c.getString(0);
        } else {
            return "";
        }
    }

}
