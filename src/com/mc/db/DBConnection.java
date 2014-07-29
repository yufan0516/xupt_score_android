package com.mc.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBConnection extends SQLiteOpenHelper {

	static SQLiteDatabase sqLiteDatabase;
	// ����γ̺ͽ��ҵı�
	public interface UserSchema {
		String TABLE_NAME = "Users";// �������֣������а˸�����

		String ID = "_id";

		String USERNAME = "username";
		String PASSWORD = "password";
	}

	private static final String DATABASE_NAME = "users";// ���ݿ�
	private static final int DATABASE_VERSION = 1;

	public DBConnection(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		// TODO Auto-generated constructor stub
	}

	// ������
	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		String sql = "CREATE TABLE "
				+ UserSchema.TABLE_NAME
				+ " ("
				+ UserSchema.ID
				+ " INTEGER primary key autoincrement, " // ����
				+ UserSchema.USERNAME + " text not null, "
				+ UserSchema.PASSWORD + " text not null " + ");";

/*		String[] sqlStrings = { sql, sqll };
		for (int i = 0; i < sqlStrings.length; i++) {
			db.execSQL(sqlStrings[i]);

		}*/
		 db.execSQL(sql);
		System.out.println("****************�����ɹ�");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}
	
	public static String getPassword(String username,Context context){
		String password = "";
		sqLiteDatabase = new DBConnection(context).getWritableDatabase();
		String[] USERSFROM = { UserSchema.ID,
				UserSchema.USERNAME, UserSchema.PASSWORD,
		};
		Cursor c = sqLiteDatabase
				.query(UserSchema.TABLE_NAME, USERSFROM,
						"username='"+username+"'", null, null,
						null, null);
		c.moveToFirst();
		if (c!=null) {
			password = c.getString(2);//��ȡ����
		}
		
		return password;
	}

}