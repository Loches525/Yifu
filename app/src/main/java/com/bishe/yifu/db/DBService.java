package com.bishe.yifu.db;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.bishe.yifu.entity.Yifu;


public class DBService {

    private DBHelper helperss;

    public DBService(Context context) {
        helperss = new DBHelper(context);
    }

    /**
     * 保存数据
     *
     * @param
     */
    public boolean save(Yifu y) {
        //int id, String name, String image
        SQLiteDatabase db = helperss.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("image", y.getImage());
        values.put("name", y.getName());
        long i = db.insert("yifu", null, values); // 第一个参数是数据表名字，第二个字段是允许为空的字段的名字
        db.close();
        if (i == -1) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 查询数据库表
     *
     * @return
     */
    public ArrayList<Yifu> search() {
        ArrayList<Yifu> list = new ArrayList<Yifu>();
        SQLiteDatabase db = helperss.getReadableDatabase();
        Cursor cursor = db.query("yifu", null, null, null, null, null, null);// 第二个字段如果是null，返回数据库里所有属性
        while (cursor.moveToNext()) {
            int wid = cursor.getInt(cursor.getColumnIndex("id"));
            String content = cursor.getString(cursor.getColumnIndex("name"));
            String image = cursor.getString(cursor.getColumnIndex("image"));

            Yifu a = new Yifu(wid, content, image);
            list.add(a);

        }
        db.close();
        return list;
    }

    /**
     * 修改数据库表
     *
     * @param
     * @param
     */
    public boolean update(Yifu y) {
        SQLiteDatabase db = helperss.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("image", y.getImage());

        long i = db.update("yifu", values, "id=?", new String[]{y.getId() + ""});
        db.close();
        if (i != -1) {
            return true;
        } else {
            return false;
        }
    }


}
