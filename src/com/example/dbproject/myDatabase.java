package com.example.dbproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.CharArrayBuffer;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class myDatabase extends SQLiteOpenHelper {

	//name of db
	public static final String dbName = "myDB";
	//tables
	public static final String weapons = "Weapons";
	public static final String armor = "Armor";
	//columns
	public static final String wName = "Name";
	public static final String wMaterial = "Material";
	public static final String wNum = "Number";
	//
	public static final String aName = "Name";
	public static final String aMaterial = "Material";
	public static final String aNum = "Number";
	
	

	//create the database
	public myDatabase(Context context) {
		super(context, dbName, null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		//Create the tables
		db.execSQL("CREATE TABLE Weapons (" + wNum + " INTEGER PRIMARY KEY, " + wName + " TEXT, " + wMaterial + " TEXT)");
		db.execSQL("CREATE TABLE Armor (" + aNum + " INTEGER PRIMARY KEY, " + aName + " TEXT, " + aMaterial + " TEXT)");
		db.close();
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		//if the database has been upgraded the existing tables are dropped and onCreate is called
		//this is not a super good way of handling it because user may have precious data
		db.execSQL("DROP TABLE IF EXISTS " + weapons);
		db.execSQL("DROP TABLE IF EXISTS " + armor);
		onCreate(db);
	}
	
	public void addWeap(String type, String mat, int i) {
		//add a weapon to the weapons database
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(wName, type);
		values.put(wMaterial, mat);
		values.put(wNum, i);
		db.insert(weapons, null, values);
		db.close();
	}
	
	public void addArmor(String type, String mat, int i) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(aName, type);
		values.put(aMaterial, mat);
		values.put(aNum, i);
		db.insert(armor, null, values);
		db.close();
	}

	public int getWeapCount() {
		//find out how many weapons are in the db.
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor myCursor = db.rawQuery("SELECT * FROM " + weapons, null);
		int weapCnt = myCursor.getCount();
		myCursor.close();
		db.close();
		return weapCnt;
	}
	
	public int getArmorCount() {
		//find out how many weapons are in the db.
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor myCursor = db.rawQuery("SELECT * FROM " + armor, null);
		int armorCnt = myCursor.getCount();
		myCursor.close();
		db.close();
		return armorCnt;
	}
	
	public void emptyInventory()
	{
		//delete weapons table and recreate without any items.
		SQLiteDatabase db = this.getWritableDatabase();
		db.execSQL("DROP TABLE IF EXISTS " + weapons);
		db.execSQL("DROP TABLE IF EXISTS " + armor);
		onCreate(db);
	}
	

	public String returnWeapName(int ID) {                          
		SQLiteDatabase myDB = this.getReadableDatabase();
         String[] mySearch = new String[]{String.valueOf(ID)};
         Cursor myCursor = myDB.rawQuery("SELECT "+ wName +" FROM "+ weapons +" WHERE "+ wNum +"=?",mySearch);
         myCursor.moveToFirst();
         int index = myCursor.getColumnIndex(wName);
         String myAnswer = myCursor.getString(index);
         myCursor.close();
         myDB.close();
         return myAnswer;
 }
	
	public String returnWeapMat(int ID) {                          
		SQLiteDatabase myDB = this.getReadableDatabase();
         String[] mySearch = new String[]{String.valueOf(ID)};
         Cursor myCursor = myDB.rawQuery("SELECT "+ wMaterial +" FROM "+ weapons +" WHERE "+ wNum +"=?",mySearch);
         myCursor.moveToFirst();
         int index = myCursor.getColumnIndex(wMaterial);
         String myAnswer = myCursor.getString(index);
         myCursor.close();
         myDB.close();
         return myAnswer;
 }
	
	public String returnArmorName(int ID) {                          
		SQLiteDatabase myDB = this.getReadableDatabase();
         String[] mySearch = new String[]{String.valueOf(ID)};
         Cursor myCursor = myDB.rawQuery("SELECT "+ aName +" FROM "+ armor +" WHERE "+ aNum +"=?",mySearch);
         myCursor.moveToFirst();
         int index = myCursor.getColumnIndex(aName);
         String myAnswer = myCursor.getString(index);
         myCursor.close();
         myDB.close();
         return myAnswer;
 }
	
	public String returnArmorMat(int ID) {      
		//send back the material of the armor with aNum == ID
		SQLiteDatabase myDB = this.getReadableDatabase();
         String[] mySearch = new String[]{String.valueOf(ID)};
         Cursor myCursor = myDB.rawQuery("SELECT "+ aMaterial +" FROM "+ armor +" WHERE "+ aNum +"=?",mySearch);
         myCursor.moveToFirst();
         int index = myCursor.getColumnIndex(aMaterial);
         String myAnswer = myCursor.getString(index);
         myCursor.close();
         myDB.close();
         return myAnswer;
 }

}