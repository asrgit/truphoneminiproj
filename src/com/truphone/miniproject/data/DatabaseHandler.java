package com.truphone.miniproject.data;
 
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;
 
public class DatabaseHandler extends SQLiteOpenHelper {
 
    // for our logs
    public static final String TAG = "DatabaseHandler.java";
 
    // database version
    private static final int DATABASE_VERSION = 7;
 
    // database name
    protected static final String DATABASE_NAME = "TruphoneDB";
 
    // table details
    public String tableName = "counterticks";
    public String fieldObjectId = "id";
    public String fieldObjectValue = "value";
     
    // constructor
    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
 
    // creating table
    @Override
    public void onCreate(SQLiteDatabase db) {
 
    	StringBuilder sb = new StringBuilder("");
    	sb.append("CREATE TABLE ");
    	sb.append(tableName);
    	sb.append(" ( ");
    	sb.append(fieldObjectId);
    	sb.append(" INTEGER PRIMARY KEY AUTOINCREMENT, ");
    	sb.append(fieldObjectValue);
    	sb.append(" TEXT ");
    	sb.append(" ) ");
 
        db.execSQL(sb.toString());
 
        // create the index for our INSERT OR REPLACE INTO statement.
        // this acts as the WHERE name="name input" AND description="description input"
        // if that WHERE clause is true, I mean, it finds the same name and description in the database,
        // it will be REPLACEd. 
        // ELSE, what's in the database will remain and the input will be INSERTed (new record)
		String INDEX = new StringBuilder(
				"CREATE UNIQUE INDEX locations_index ON ").append(tableName)
				.append(" (name)").toString();
        db.execSQL(INDEX);
    }
 
     
    // When upgrading the database, it will drop the current table and recreate.
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
 
        String sql = new StringBuilder("DROP TABLE IF EXISTS ").append(tableName).toString();
        db.execSQL(sql);
 
        onCreate(db);
    }
 
    // insert data using transaction and prepared statement
    public void insertFast(String counterString) {
 
        // you can use INSERT only
		String sql = new StringBuilder("INSERT OR REPLACE INTO ")
				.append(tableName)
				.append(" ( name ) VALUES ( ? )").toString();
         
        SQLiteDatabase db = this.getWritableDatabase();
         
//       db.beginTransactionNonExclusive();
         db.beginTransaction();
         
        SQLiteStatement stmt = db.compileStatement(sql);
         
        stmt.bindString(1, counterString);
         
        stmt.execute();
        stmt.clearBindings();
 
        db.setTransactionSuccessful();
        db.endTransaction();
         
        db.close();
    }
     
    // inserts the record without using transaction and prepare statement
    public void insertNormal(String counterString){
        try{
             
            SQLiteDatabase db = this.getWritableDatabase();
             
                 
            ContentValues values = new ContentValues();
            values.put(fieldObjectValue, counterString);
            db.insert(tableName, null, values);                
            db.close();
             
        }catch(Exception e){
            Log.e(TAG, e.getMessage());
        } 
    }
     
    // deletes all records
    public void deleteRecords(){
    	
        SQLiteDatabase db = this.getWritableDatabase();
        StringBuilder sb = new StringBuilder("delete from "). append(tableName);
        db.execSQL(sb.toString());
        db.close();
    }
     
    // count records
    public int countRecords(){
         
        SQLiteDatabase db = this.getWritableDatabase();
         
        Cursor cursor = db.rawQuery("SELECT count(*) from " + tableName, null);
        cursor.moveToFirst();
         
        int recCount = cursor.getInt(0);
         
        cursor.close();
        db.close();
         
        return recCount;
    }
     
    //TODO - to query all elements and populate in listview
    public int getAllRecords() {
    	
        SQLiteDatabase db = this.getReadableDatabase();
        
        Cursor cursor = db.rawQuery("SELECT * from " + tableName, null);
        cursor.moveToFirst();
         
        int recCount = cursor.getInt(0);
         
        cursor.close();
        db.close();
         
        return recCount;
    }
    
    public String getLastCounterEntry(){
    	
        SQLiteDatabase db = this.getReadableDatabase();
        
        Cursor cursor = db.rawQuery("SELECT * from " + tableName, null);
        
        String recentCounter = null; //default value set to -1, if table is empty (ie, app runs for the very first time)
        
        if(cursor.moveToLast()) {
         
	        recentCounter = cursor.getString(1); //get the data from "value" column
        }
        cursor.close();
        db.close();
         
        return recentCounter;
    }    
}