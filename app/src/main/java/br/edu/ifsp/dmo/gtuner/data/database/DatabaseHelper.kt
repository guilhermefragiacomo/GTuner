package br.edu.ifsp.dmo.gtuner.data.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(
    context,
    DATABASE_KEYS.DATABASE_NAME,
    null,
    DATABASE_KEYS.DATABASE_VERSION
) {
    object DATABASE_KEYS {
        const val DATABASE_NAME = "gtuner.db"
        const val DATABASE_VERSION = 2

        const val TABLE_SHEET_NAME = "sheet"
        const val COLUMN_SHEET_NAME = "name"
        const val COLUMN_SHEET_ID = "sheet_id"
        const val COLUMN_SHEET_URL = "url"
        const val COLUMN_SHEET_AUTHOR = "author"
        const val COLUMN_SHEET_ARRANGMENT = "arrangment"
    }

    private companion object {
        const val CREATE_SHEET_TABLE = "CREATE TABLE ${DATABASE_KEYS.TABLE_SHEET_NAME} (" +
                "${DATABASE_KEYS.COLUMN_SHEET_ID} TEXT PRIMARY KEY NOT NULL, " +
                "${DATABASE_KEYS.COLUMN_SHEET_NAME} TEXT NOT NULL, " +
                "${DATABASE_KEYS.COLUMN_SHEET_URL} TEXT NOT NULL," +
                "${DATABASE_KEYS.COLUMN_SHEET_AUTHOR} TEXT NOT NULL," +
                "${DATABASE_KEYS.COLUMN_SHEET_ARRANGMENT} TEXT NOT NULL)"


        const val SEARCH_SHEET_ID = "SELECT * " +
                "FROM ${DATABASE_KEYS.TABLE_SHEET_NAME}" +
                "WHERE ${DATABASE_KEYS.COLUMN_SHEET_ID} = ?"

        const val SEARCH_ALL_SHEET = "SELECT * " +
                "FROM ${DATABASE_KEYS.TABLE_SHEET_NAME}"

        const val DROP_TABLE_SHEET = "DROP TABLE IF EXISTS ${DATABASE_KEYS.TABLE_SHEET_NAME}"
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(CREATE_SHEET_TABLE)
    }
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL(DROP_TABLE_SHEET)
        onCreate(db)
    }
}
