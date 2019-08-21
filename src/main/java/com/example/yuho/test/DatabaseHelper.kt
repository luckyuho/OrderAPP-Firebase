package com.example.yuho.test

import android.content.Context
import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.security.AccessControlContext


class DatabaseHelper(context: Context):SQLiteOpenHelper(context, dbname, factory, version){
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("create table if not exists user(id integer primary key autoincrement, username varchar, account varchar, password varchar)")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun insertUserData(username: String, account: String, password: String){
        val db: SQLiteDatabase = writableDatabase
        val values: ContentValues = ContentValues()
        values.put("username", username)
        values.put("accout", account)
        values.put("password", password)

        db.insert("user", null, values)
        db.close()

    }

    fun userPresent(account: String, password: String): Boolean{
        val db = readableDatabase
        val query = "select * from user where accout = '$account' and password = '$password'"
        val cursor = db.rawQuery(query, null)

        println(cursor.count)

        if (cursor.count <=0 ) {
            cursor.close()
            return false
        }

        cursor.close()
        return true
    }

    companion object{
        internal val dbname = "userDB"
        internal val factory = null
        internal val version = 1
    }
}
