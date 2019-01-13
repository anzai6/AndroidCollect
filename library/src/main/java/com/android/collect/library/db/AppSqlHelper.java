package com.android.collect.library.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 应用的数据库
 * 
 * @author anzai
 * 
 */
public class AppSqlHelper extends SQLiteOpenHelper {

	private static AppSqlHelper appSqlHelper;
	private static final String DATABASE_NAME = "anzai";
	private static final int DATABASE_VERSION = 1;

	public static AppSqlHelper getInstance(Context context) {
		if (appSqlHelper == null)
			appSqlHelper = new AppSqlHelper(context);
		return appSqlHelper;
	}

	public AppSqlHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	public AppSqlHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
//		db.execSQL(HtmlZipFileTable.CREATE_TABLE_SQL);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//		db.execSQL(HtmlZipFileTable.DROP_TABLE_SQL);
		onCreate(db);
	}

}
