package chenjox.util.table.transformer.mono.ascii

import chenjox.util.table.mono.MonoTable
import chenjox.util.table.transformer.mono.MonoTableTransformer
import chenjox.util.table.transformer.mono.calculateColumnWidths
import kotlin.math.max

@Deprecated("TableTransformer are deprecated! For Output use a renderer instead!")
class AsciiMonoTransformer<E>(val mapper: (cell: E) -> String = { it.toString() }) : MonoTableTransformer<E, String> {

    private fun calculateWidths(table: MonoTable<E>) : IntArray = calculateColumnWidths(table, mapper)

    override fun invoke(table: MonoTable<E>): String {
        val widths = calculateWidths(table)
        val renderingAgents : MutableList<RowRenderingAgent> = ArrayList(table.getRows())
        for (i in 0 until table.getRows()){
            renderingAgents.add( RowRenderingAgent(extractRowAsStringList(table, mapper, i), widths) )
        }

        val bu = StringBuilder()
        bu.run {
            for( agent in renderingAgents){
                append(agent.renderRow())
                appendLine()
            }
        }
        return bu.toString()
    }
}

internal class RowRenderingAgent(val columns : List<String>, private val columnWidths : IntArray) {

    init {
        if(columns.size!=columnWidths.size) throw IllegalArgumentException("")
    }

    fun renderRow() : String{
        val bu = StringBuilder()
        val lastEntry = columns.size-1
        bu.run {
            append("|")
            for (i in 0 until columns.size-1){
                append( columns[i].padEnd( columnWidths[i] ) )
                append("|")
            }
            append( columns[lastEntry].padEnd( columnWidths[lastEntry] ) )
            append("|")
        }
        return bu.toString()
    }

}

internal fun <E> extractRowAsStringList(table: MonoTable<E>,mapper: (cell: E) -> String, row : Int): List<String>{
    val result : MutableList<String> = ArrayList(table.getColumns()-1)
    for (i in 0 until table.getColumns()){
        result.add( mapper.invoke( table[i, row] ) )
    }
    return result
}