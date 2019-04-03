package com.example.mrzhang.smarttraffic.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.mrzhang.smarttraffic.bean.SenseBean;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

public class OrmHelper extends OrmLiteSqliteOpenHelper {

    private static OrmHelper instanse;

    public OrmHelper(Context context) {
        super(context, "mydb", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, SenseBean.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource, int i, int i1) {

    }

    public static OrmHelper getInstanse(Context mContext) {
        if (instanse == null) {
            instanse = new OrmHelper(mContext);
        }
        return instanse;
    }

}
