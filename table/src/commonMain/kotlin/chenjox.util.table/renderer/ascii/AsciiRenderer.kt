package chenjox.util.table.renderer.ascii

import chenjox.util.table.Table
import chenjox.util.table.renderer.TableRenderer
import kotlin.math.max

class AsciiRenderer(table: Table) : TableRenderer(table) {

    private val widths : IntArray by lazy { calculateWidths() }
    private var minWidth : Int = 5

    private fun calculateWidths() : IntArray{
        val result = IntArray(table.getColumns())
        for ( i in 0 until table.getColumns()){
            result[i] = max(calculateStringWidthOfColumn(table, i), minWidth)
        }
        return result
    }

    override fun render(): String {
        val renderingAgents : MutableList<RowRenderingAgent> = ArrayList(table.getRows())
        for (i in 0 until table.getRows()){
            renderingAgents.add( RowRenderingAgent(extractRowAsStringList(table, i), widths) )
        }

        val bu = StringBuilder()
        bu.run {
            for( agent in renderingAgents){
                append(agent.renderRow())
                append("\n")
            }
        }
        return bu.toString()
    }


}