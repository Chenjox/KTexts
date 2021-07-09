package chenjox.util.table.renderer

import chenjox.util.table.Table
import kotlin.math.max

public class SimpleAsciiRenderer : TableRenderer<String>{

    private fun calculateWidths(table: Table) : IntArray = calculateColumnWidths(table)

    public override fun invoke(table: Table): String {
        val widths = calculateWidths(table)
        val renderingAgents : MutableList<RowRenderingAgent> = ArrayList(table.getRows())
        for (i in 0 until table.getRows()){
            renderingAgents.add( RowRenderingAgent(extractRowAsStringList(table, i), widths) )
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

internal fun extractRowAsStringList(table: Table, row : Int): List<String>{
    val result : MutableList<String> = ArrayList(table.getColumns()-1)
    for (i in 0 until table.getColumns()){
        result.add( table.getAsString(i, row) )
    }
    return result
}

private fun calculateColumnWidths(table: Table) : IntArray{
    val result = IntArray(table.getColumns())
    for ( i in 0 until table.getColumns()){
        result[i] = max( calculateStringWidthOfColumn(table, i), 1 )
    }
    return result
}

private fun calculateStringWidthOfColumn(table: Table, column : Int) : Int{
    var result = 0
    for (row in 0 until table.getRows()){
        val size = table.getAsString(column, row).length
        if( size > result ) result = size
    }
    return result
}