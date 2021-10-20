package com.romdon.formsregistrasi.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.romdon.formsregistrasi.model.RegisterData;

import java.util.ArrayList;
import java.util.List;

public class DbHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    static final String DATABASE_NAME = "formregist.db";

    public static final String TABLE_NAME = "peserta";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_ADDRESS = "address";
    public static final String COLUMN_PHONE_NUMBER = "nomor_hp";
    public static final String COLUMN_PHOTO = "foto";
    public static final String COLUMN_LOCATION = "lokasi_pendaftaran";
    public static final String COLUMN_GENDER = "jenis_kelamin";

    private DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    private volatile static DbHelper mInstance;

    public static synchronized DbHelper getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new DbHelper(context);
        }

        return mInstance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_MOVIE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_NAME + " TEXT NOT NULL," +
                COLUMN_ADDRESS + " TEXT NOT NULL, " +
                COLUMN_PHOTO + " TEXT NOT NULL, " +
                COLUMN_LOCATION + " TEXT NOT NULL, " +
                COLUMN_GENDER + " TEXT NOT NULL, " +
                COLUMN_PHONE_NUMBER + " TEXT NOT NULL" + ");";

        db.execSQL(SQL_CREATE_MOVIE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    // Save data
    public long insert(RegisterData data) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put(COLUMN_NAME, data.getName());
        cv.put(COLUMN_ADDRESS, data.getAddress());
        cv.put(COLUMN_PHONE_NUMBER, data.getPhoneNumber());
        cv.put(COLUMN_LOCATION, data.getLocation());
        cv.put(COLUMN_GENDER, data.getGender());
        cv.put(COLUMN_PHOTO, data.getPhoto());

        long result = db.insert(TABLE_NAME, null, cv);

        db.close();
        return result;
    }

    // Get data
    public List<RegisterData> findAllData() {
        ArrayList<RegisterData> list = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor c = db.rawQuery(query, null);

        if (c.moveToFirst()) {
            do {
                int id = c.getInt(c.getColumnIndexOrThrow(COLUMN_ID));
                String name = c.getString(c.getColumnIndexOrThrow(COLUMN_NAME));
                String address = c.getString(c.getColumnIndexOrThrow(COLUMN_ADDRESS));
                String phoneNumber = c.getString(c.getColumnIndexOrThrow(COLUMN_PHONE_NUMBER));
                String location = c.getString(c.getColumnIndexOrThrow(COLUMN_LOCATION));
                String photo = c.getString(c.getColumnIndexOrThrow(COLUMN_PHOTO));
                String gender = c.getString(c.getColumnIndexOrThrow(COLUMN_GENDER));

                list.add(
                        new RegisterData(
                                id, name, address, photo, location, gender, phoneNumber
                        ));
            } while (c.moveToNext());
        }

        c.close();
        db.close();
        return list;
    }

}
