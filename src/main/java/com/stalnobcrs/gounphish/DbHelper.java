package com.stalnobcrs.gounphish;

/**
 * Created by JoHNnY on 01-03-2018.
 */

import  android.content.ContentValues;//This class is used to store a set of values that the ContentResolver can process
import  android.content.Context;//Interface to global information about an application environment.
import  android.database.Cursor;//This interface provides random read-write access to the result set returned by a database query.
import  android.database.sqlite.SQLiteDatabase;//SQLiteDatabase has methods to create, delete, execute SQL commands and perform other common database management tasks.
import  android.database.sqlite.SQLiteOpenHelper;//A helper class to manage database creation and version management and it uses subclass implementing onCreate(SQLiteDatabase), onUpgrade(SQLiteDatabase, int, int) and optionally onOpen(SQLiteDatabase),
import  android.util.Log;
public class DbHelper extends  SQLiteOpenHelper {
    public static final  String TAG  = DbHelper.class .getSimpleName();
    public static final  String DB_NAME  = "myapp.db" ;
    public static final int  DB_VERSION  = 1 ;

    public static final  String USER_TABLE  = "users" ;
    public static final  String COLUMN_ID  = "_id" ;
    public static final  String COLUMN_EMAIL  = "email" ;
    public static final  String COLUMN_PASS  = "password" ;

    /*
     create table users(
     id integer primary key autoincrement,
     email text,
     password text);
     */
    public static final  String CREATE_TABLE_USERS  = "CREATE TABLE "  + USER_TABLE  + "("
            + COLUMN_ID  + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_EMAIL  + " TEXT,"
            + COLUMN_PASS  + " TEXT);" ;

    public DbHelper(Context context ) {
        super (context , DB_NAME , null , DB_VERSION );
    }

    @Override
    public void  onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_USERS );
    }

    @Override
    public void  onUpgrade(SQLiteDatabase db, int  oldVersion, int  newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "  + USER_TABLE );
        onCreate(db);
    }

    /**
     * Storing user details in database
     * */
    public void  addUser(String email, String password) {
        SQLiteDatabase db = this .getWritableDatabase();

        ContentValues values = new  ContentValues();
        values.put(COLUMN_EMAIL , email);
        values.put(COLUMN_PASS , password);

        long  id = db.insert(USER_TABLE , null , values);
        db.close();

        Log.d (TAG , "user inserted"  + id);
    }



    public boolean  getUser(String email, String pass){
        //HashMap<String, String> user = new HashMap<String, String>();
        String selectQuery = "select * from "  + USER_TABLE  + " where "  +
                COLUMN_EMAIL  + " = "  + "'" +email+"'"  + " and "  + COLUMN_PASS  + " = "  + "'" +pass+"'" ;

        SQLiteDatabase db = this .getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null );
        // Move to first row
        cursor.moveToFirst();
        if  (cursor.getCount() > 0 ) {

            return true ;
        }
        cursor.close();
        db.close();

        return false ;
    }
}
