package cn.fanrunqi.materiallogin.mysqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by cmk on 2018/3/24.
 * 保存登录用的密码和手机号
 */

public class MyLoginDaseHelper extends SQLiteOpenHelper {

    private final String TAG = "MyLoginDaseHelper";

    private Context mContext;

    public static final String CREATE_PERSON_INFO = "create table Person_Info (" +
            "id integer primary key," +
            "password text," +
            "checked integer)";

    public MyLoginDaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_PERSON_INFO);
        Log.d(TAG,"数据库初次建立");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists Person_Info");
        onCreate(db);
    }
}
