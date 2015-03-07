package com.example.prajakta.notes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by prajakta on 2/8/15.
 */
public class DBAdapter {

    public static final String KEY_ROW_ID = "_id";
    public static final String KEY_CAPTION = "caption";
    public static final String KEY_PATH = "path";
    public static final String TAG = "DBAdapter";

    public static final String DATABASE_NAME = "ASSIGNMENT";
    public static final String DATABASE_TABLE = "notes";
    public static final int DATABASE_VERSION = 1;

    public static final String DATABASE_CREATE = "create table notes (_id integer primary key autoincrement, " + "caption text not null, path text not null);";

    Context context;

    DatabaseHelper DBHelper;
    SQLiteDatabase db;

    public DBAdapter(Context ctx) {

        {
            this.context = ctx;
            DBHelper = new DatabaseHelper(context);
        }
    }

    private static class DatabaseHelper extends SQLiteOpenHelper {

        DatabaseHelper(Context context) {

            super(context, DATABASE_NAME,null,DATABASE_VERSION );

        }


        @Override
        public void onCreate(SQLiteDatabase db) {
            try {
                db.execSQL(DATABASE_CREATE);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w(TAG, "UPGRADING database from version" + oldVersion + " to " + newVersion + ",which will destroy old data ");
            db.execSQL("DROP TABLE IF EXISTS notes" );
            onCreate(db);

        }
    }
    //** Open the database */

    public DBAdapter open() throws  SQLException
    {
        db= DBHelper.getWritableDatabase();
        return this;
    }

    public void close()
    {
        DBHelper.close();
    }

    //** Insert a note in the table */
    public long insertNote(String caption, String path )
    {
        ContentValues intialValues  = new ContentValues();
        intialValues.put(KEY_CAPTION,caption);
        intialValues.put(KEY_PATH,path);
        long r= db.insert(DATABASE_TABLE,null,intialValues);
        return r;

    }
    //**Delete a particular note

    public boolean deleteNote(long rowId)
    {
        return db.delete(DATABASE_TABLE,KEY_ROW_ID +"="+ rowId,null) >0;
    }

    //**retrieves all the Notes */

    public Cursor getAllNotes()
    {

        return db.query(DATABASE_TABLE,new String[]{ KEY_ROW_ID,KEY_CAPTION,KEY_PATH},null,null,null,null,null);
    }

    //**Retrieves a particular note */

    public Cursor getNote(long rowID ) throws  SQLException
    {

        Cursor mcursor = db.query(true,DATABASE_TABLE,new String[]{ KEY_ROW_ID,KEY_CAPTION,KEY_PATH},KEY_ROW_ID + "=" +rowID ,null,null,null,null,null);
        if(mcursor !=null)
        {
            mcursor.moveToFirst();
        }
        return  mcursor;
    }

    /**update a note */
    public boolean updateNote(long rowID, String caption , String path)
    {

        ContentValues args = new ContentValues();
        args.put(KEY_CAPTION,caption);
        args.put(KEY_PATH,path);
        return db.update(DATABASE_TABLE,args,KEY_ROW_ID +"="+ rowID,null)>0;
    }

}


