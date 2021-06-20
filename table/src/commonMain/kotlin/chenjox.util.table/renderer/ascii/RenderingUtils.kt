package chenjox.util.table.renderer.ascii

import chenjox.util.table.Table

internal fun calculateStringWidthOfColumn(table: Table, column : Int) : Int{
    var result = 0
    for (row in 0 until table.getRows()){
        val size = table.getAsString(column, row).length
        if( size > result ) result = size
    }
    return result
}

internal fun extractRowAsStringList(table: Table, row : Int): List<String>{
    val result : MutableList<String> = ArrayList(table.getColumns()-1)
    for (i in 0 until table.getColumns()){
        result.add( table.getAsString(i, row) )
    }
    return result
}