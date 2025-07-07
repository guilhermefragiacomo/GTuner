package br.edu.ifsp.dmo.gtuner.data.repository

import android.content.Context
import br.edu.ifsp.dmo.gtuner.data.dao.SheetDao
import br.edu.ifsp.dmo.gtuner.data.database.DatabaseHelper
import br.edu.ifsp.dmo.gtuner.data.model.Sheet

class SheetRepository(context : Context) {
    private val dbHelper = DatabaseHelper(context)
    private val dao = SheetDao(dbHelper)

    fun add(sheet: Sheet): Long = dao.insert(sheet)

    fun get_by_id(id: Int): Sheet? = dao.get_by_id(id)

    fun get_all(): List<Sheet> = dao.get_all()
}