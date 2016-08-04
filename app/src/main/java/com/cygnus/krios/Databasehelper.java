package com.cygnus.krios;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class Databasehelper extends SQLiteOpenHelper {

    @SuppressLint("SdCardPath")
    public final static String DATABASE_PATH = "/data/data/com.cygnus.krios/databases/";
    public static final int DATABASE_VERSION = 1;
    //    static final String TABAL_ENQ = "responce";
    static final String TABAL_PRODUCT = "Product_categories";
    static final String version = "version";
    static final String order = "ordersss";
    private static final String DATABASE_NAME = "Krios";// "db.sqlite";
    private final Context myContext;
    String Parents_id;
    String Level;
    private SQLiteDatabase myDataBase;
    @SuppressLint("SimpleDateFormat")
    private SimpleDateFormat dateformate = new SimpleDateFormat(
            "yyyy-MM-dd HH:mm:ss");

    public Databasehelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.myContext = context;
    }

    // Create a empty database on the system
    public void createDataBase() throws IOException {

        boolean dbExist = checkDataBase();

        if (dbExist) {
            Log.v("DB Exists", "db exists");

        } else {

            this.getReadableDatabase();
            try {
                this.close();
                copyDataBase();
            } catch (IOException e) {
                throw new Error("Error copying database");
            }

        }

    }

    // Check database already exist or not
    private boolean checkDataBase() throws IOException {
        boolean checkDB = false;
        try {
            String myPath = DATABASE_PATH + DATABASE_NAME;
            File dbfile = new File(myPath);
            checkDB = dbfile.exists();
        } catch (SQLiteException e) {
            System.out.println("delete database file.");
        }
        return checkDB;
    }

    // Copies your database from your local assets-folder to the just created
    // empty database in the system folder
    private void copyDataBase() throws IOException {

        String outFileName = DATABASE_PATH + DATABASE_NAME;

        OutputStream myOutput = new FileOutputStream(outFileName);
        InputStream myInput = myContext.getAssets().open(DATABASE_NAME);

        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }
        myInput.close();
        myOutput.flush();
        myOutput.close();
    }

    // delete database
    public void db_delete() {
        File file = new File(DATABASE_PATH + DATABASE_NAME);
        if (file.exists()) {
            file.delete();
            System.out.println("delete database file.");
        }
    }

    // Open database
    public void openDatabase() throws SQLException {
        String myPath = DATABASE_PATH + DATABASE_NAME;
        myDataBase = SQLiteDatabase.openDatabase(myPath, null,
                SQLiteDatabase.OPEN_READWRITE);
    }

    public synchronized void closeDataBase() throws SQLException {
        if (myDataBase != null)
            myDataBase.close();
        super.close();
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion > oldVersion) {
            Log.v("Database Upgrade", "Database version higher than old.");
            db_delete();
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }


    public void SetApkVersion(String id, String ver) throws IOException {

        openDatabase();

        String insert_artical = "insert into  " + version + " (ver,id)"
                + "values('" + ver + "','" + id + "')";

        try {
            myDataBase.execSQL(insert_artical);
            Log.d("insert", "Insert Succesfully");
        } catch (Exception e) {

            e.printStackTrace();
            Log.d("insert", "Insert Succesfully" + e);
        }

        closeDataBase();

    }

    public String GetApkVersion() throws IOException {

        openDatabase();
        String eno = "1";
        String versions = "";

        String query = "select * From " + version + " Where id='" + eno + "'";

        Cursor cursor = myDataBase.rawQuery(query, null);
        List<UserData> floor = new ArrayList<UserData>();
        if (cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {

                    versions = cursor.getString(0);

                } while (cursor.moveToNext());
            }
        }
        Log.i("query", "total data : " + floor.size());

        closeDataBase();

        return versions;

    }


    public void InsertProduct_categories(List<UserData> data)
            throws IOException {
        openDatabase();

        for (int i = 0; i < data.size(); i++) {

            String insert = "insert into  " + TABAL_PRODUCT + " (entity_id,meta_title,description,parent_id,meta_description,position,level,name)"
                    + "values('"
                    + data.get(i).getEntity_id()
                    + "','"
                    + data.get(i).getMeta_title()
                    + "','"
                    + data.get(i).getDescription()
                    + "','"
                    + data.get(i).getParent_id()
                    + "','"
                    + data.get(i).getMeta_description()
                    + "','"
                    + data.get(i).getPosition()
                    + "','"
                    + data.get(i).getLevel()
                    + "','"
                    + data.get(i).getName() + "')";
            try {
                myDataBase.execSQL(insert);
                Log.v("quiry", "" + insert);
            } catch (Exception e) {

                Log.e("InsertProduct_cat", "Insert unSuccesfully" + e.toString());
            }

        }
        closeDataBase();
    }


    public List<UserData> Get_InsertProduct_categories() {
        openDatabase();

        String GetTABAL_PRODUCT = "select * From " + TABAL_PRODUCT;
        Cursor cursor = myDataBase.rawQuery(GetTABAL_PRODUCT, null);
        List<UserData> Return_List = new ArrayList<UserData>();
        if (cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    UserData Data = new UserData();
                    Data.setEntity_id(cursor.getString(cursor
                            .getColumnIndex("entity_id")));
                    Data.setMeta_title(cursor.getString(cursor
                            .getColumnIndex("meta_title")));
                    Data.setDescription(cursor.getString(cursor
                            .getColumnIndex("description")));
                    Data.setParent_id(cursor.getString(cursor
                            .getColumnIndex("parent_id")));
                    Data.setPosition(cursor.getString(cursor
                            .getColumnIndex("position")));
                    Data.setLevel(cursor.getString(cursor
                            .getColumnIndex("level")));
                    Data.setMeta_description(cursor.getString(cursor
                            .getColumnIndex("meta_description")));
                    Data.setName(cursor.getString(cursor
                            .getColumnIndex("name")));


                    Return_List.add(Data);
                } while (cursor.moveToNext());
            }
        }

        Log.i("TABAL_PRODUCT  :  ", "Finish : " + Return_List.size());

        closeDataBase();
        return Return_List;
    }

    public List<UserData> Get_Menu_Level_2() {
        openDatabase();

        String GetTABAL_PRODUCT = "select * from product_categories where level = 2  and is_active=1";
        Cursor cursor = myDataBase.rawQuery(GetTABAL_PRODUCT, null);
        List<UserData> Return_List = new ArrayList<UserData>();
        if (cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    UserData Data = new UserData();
                    Data.setEntity_id(cursor.getString(cursor
                            .getColumnIndex("entity_id")));
                    Data.setEntity_type_id(cursor.getString(cursor
                            .getColumnIndex("entity_type_id")));
                    Data.setAttribute_set_id(cursor.getString(cursor
                            .getColumnIndex("attribute_set_id")));
                    Data.setParent_id(cursor.getString(cursor
                            .getColumnIndex("parent_id")));
                    Data.setPosition(cursor.getString(cursor
                            .getColumnIndex("position")));
                    Data.setLevel(cursor.getString(cursor
                            .getColumnIndex("level")));
                    Data.setChildren_count(cursor.getString(cursor
                            .getColumnIndex("children_count")));
                    Data.setIs_active(cursor.getString(cursor
                            .getColumnIndex("is_active")));
                    Data.setName(cursor.getString(cursor
                            .getColumnIndex("name")));


                    Return_List.add(Data);
                } while (cursor.moveToNext());
            }
        }

        Log.i("TABAL_PRODUCT  :  ", "Finish : " + Return_List.size());

        closeDataBase();
        return Return_List;
    }

    public List<UserData> Get_Menu_Level_3(String Level, String EntryID) {
        openDatabase();

        String GetTABAL_PRODUCT = "select * from product_categories where level =" + Level + " and parent_id =" + EntryID + "";
        Cursor cursor = myDataBase.rawQuery(GetTABAL_PRODUCT, null);
        List<UserData> Return_List = new ArrayList<UserData>();
        if (cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    UserData Data = new UserData();
                    Data.setEntity_id(cursor.getString(cursor
                            .getColumnIndex("entity_id")));
                    Data.setEntity_type_id(cursor.getString(cursor
                            .getColumnIndex("entity_type_id")));
                    Data.setAttribute_set_id(cursor.getString(cursor
                            .getColumnIndex("attribute_set_id")));
                    Data.setParent_id(cursor.getString(cursor
                            .getColumnIndex("parent_id")));
                    Data.setPosition(cursor.getString(cursor
                            .getColumnIndex("position")));
                    Data.setLevel(cursor.getString(cursor
                            .getColumnIndex("level")));
                    Data.setChildren_count(cursor.getString(cursor
                            .getColumnIndex("children_count")));
                    Data.setIs_active(cursor.getString(cursor
                            .getColumnIndex("is_active")));
                    Data.setName(cursor.getString(cursor
                            .getColumnIndex("name")));


                    Return_List.add(Data);
                } while (cursor.moveToNext());
            }
        }


        Log.i("TABAL_PRODUCT  :  ", "Finish Sub Menu: " + Return_List.size());
        Log.i("GetTABAL_PRODUCT  :  ", "Finish GetTABAL_PRODUCT Sub : " + GetTABAL_PRODUCT);


        closeDataBase();
        return Return_List;
    }


}