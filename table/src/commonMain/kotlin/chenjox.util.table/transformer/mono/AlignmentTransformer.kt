package chenjox.util.table.transformer.mono

import chenjox.util.table.mono.MonoTable
import chenjox.util.table.mono.asMutableTable
import chenjox.util.table.mono.map

@Deprecated("See TableTransformer")
class AlignmentTransformer<E>(val alignment: Alignment, val mapper: (element: E) -> String = { it.toString() }) : MonoTableTransformer<E, MonoTable<String>> {

    override fun invoke(table: MonoTable<E>): MonoTable<String> {
        val worker = table.map(mapper).asMutableTable()
        val widths = calculateColumnWidths(worker)
        return when(alignment){
            Alignment.LEFT -> worker
            Alignment.RIGHT -> alignRight(worker, widths)
            Alignment.CENTER -> TODO()
        }
    }

    private fun alignRight(table: MonoTable<String>, widths: IntArray): MonoTable<String>{
        val result = table.asMutableTable() // copying the table...
        for (column in 0 until table.getColumns())
            for (row in 0 until table.getRows()){
                result[column, row] = table[column, row].padStart( widths[column] , ' ' )
            }
        return result
    }
}

enum class Alignment{
    LEFT,
    RIGHT,
    CENTER
}