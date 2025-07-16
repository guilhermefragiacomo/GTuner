package br.edu.ifsp.dmo.gtuner.data.dao

import android.content.ContentValues
import br.edu.ifsp.dmo.gtuner.data.database.DatabaseHelper
import br.edu.ifsp.dmo.gtuner.data.model.Sheet
import android.graphics.Bitmap

class SheetDao(private val dbHelper: DatabaseHelper) {

    fun insert(sheet: Sheet): Long {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(DatabaseHelper.DATABASE_KEYS.COLUMN_SHEET_ID, sheet.id)
            put(DatabaseHelper.DATABASE_KEYS.COLUMN_SHEET_NAME, sheet.name)
            put(DatabaseHelper.DATABASE_KEYS.COLUMN_SHEET_URL, sheet.url)
            put(DatabaseHelper.DATABASE_KEYS.COLUMN_SHEET_AUTHOR, sheet.author)
            put(DatabaseHelper.DATABASE_KEYS.COLUMN_SHEET_ARRANGMENT, sheet.arrangment)
            put(DatabaseHelper.DATABASE_KEYS.COLUMN_SHEET_BITMAP, Sheet.BitMapToString(sheet.bitmap))
        }

        return db.insert(DatabaseHelper.DATABASE_KEYS.TABLE_SHEET_NAME, null, values)
    }

    fun get_by_id(id: Int): Sheet? {
        val sheet: Sheet?
        val db = dbHelper.readableDatabase
        val columns = arrayOf(
            DatabaseHelper.DATABASE_KEYS.COLUMN_SHEET_ID,
            DatabaseHelper.DATABASE_KEYS.COLUMN_SHEET_NAME,
            DatabaseHelper.DATABASE_KEYS.COLUMN_SHEET_URL,
            DatabaseHelper.DATABASE_KEYS.COLUMN_SHEET_AUTHOR,
            DatabaseHelper.DATABASE_KEYS.COLUMN_SHEET_ARRANGMENT,
            DatabaseHelper.DATABASE_KEYS.COLUMN_SHEET_BITMAP
        )

        val where = "${DatabaseHelper.DATABASE_KEYS.COLUMN_SHEET_ID} = ?"
        val whereArgs = arrayOf(id.toString())

        val cursor = db.query(
            DatabaseHelper.DATABASE_KEYS.TABLE_SHEET_NAME,
            columns,
            where,
            whereArgs,
            null,
            null,
            null
        )

        cursor.use {
            sheet = if (cursor.moveToNext()) {
                Sheet(cursor.getString(0), cursor.getString(1), if (it.getString(2).equals("0")) null else it.getString(2), cursor.getString(3), cursor.getString(4), Sheet.StringToBitmap(if (it.getString(5).equals("0")) null else it.getString(5)))
            } else {
                null
            }
        }

        return sheet
    }

    fun get_all(): List<Sheet> {
        val db = dbHelper.readableDatabase
        val columns = arrayOf(
            DatabaseHelper.DATABASE_KEYS.COLUMN_SHEET_ID,
            DatabaseHelper.DATABASE_KEYS.COLUMN_SHEET_NAME,
            DatabaseHelper.DATABASE_KEYS.COLUMN_SHEET_URL,
            DatabaseHelper.DATABASE_KEYS.COLUMN_SHEET_AUTHOR,
            DatabaseHelper.DATABASE_KEYS.COLUMN_SHEET_ARRANGMENT,
            DatabaseHelper.DATABASE_KEYS.COLUMN_SHEET_BITMAP
        )

        val cursor = db.query(
            DatabaseHelper.DATABASE_KEYS.TABLE_SHEET_NAME,
            columns,
            null,
            null,
            null,
            null,
            null
        )

        val sheets = mutableListOf<Sheet>()

        cursor.use {
            while (it.moveToNext()) {
                sheets.add(
                    Sheet(it.getString(0), it.getString(1), if (it.getString(2).equals("0")) null else it.getString(2), it.getString(3), it.getString(4), Sheet.StringToBitmap(if (it.getString(5).equals("0")) null else it.getString(5)))
                )
            }
        }

        System.out.println(sheets)

        return sheets
    }
}