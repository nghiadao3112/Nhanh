package cc.ccds.testsql.Data;

import android.content.Context;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

public class DBOpenHelper extends SQLiteAssetHelper {
    private final String DATABASE_NAME;

    public DBOpenHelper(Context context,String database_name) {
        super(context, database_name, null, 1);
        DATABASE_NAME = database_name;
    }
}
