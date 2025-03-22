package com.example.registeration

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class donord(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(CREATE_TABLE_DONOR)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }


    companion object {
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "BloodBank2.db"
        const val TABLE_DONOR2 = "donors"
        const val COLUMN_ID = "_id"
        const val COLUMN_NAME = "name"
        const val COLUMN_LOCATION = "address"
        const val COLUMN_CONTACT = "contact"
        const val COLUMN_BLOOD_TYPE = "bloodGroup"

        const val CREATE_TABLE_DONOR = ("CREATE TABLE $TABLE_DONOR2 ("
                + "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "$COLUMN_NAME TEXT, "
                + "$COLUMN_LOCATION TEXT, "
                + "$COLUMN_CONTACT TEXT, "
                + "$COLUMN_BLOOD_TYPE TEXT)")
    }
}
