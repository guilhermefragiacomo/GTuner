package br.edu.ifsp.dmo.gtuner.ui.listener

import br.edu.ifsp.dmo.gtuner.data.model.Sheet

interface SheetItemListener {
    fun onSheetClick(sheet: Sheet);
}