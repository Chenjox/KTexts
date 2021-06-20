package chenjox.util.table.renderer.ascii

internal class RowRenderingAgent(val columns : List<String>, val columnWidths : IntArray) {

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