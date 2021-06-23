package chenjox.util.table.transformer.mono

import chenjox.util.table.mono.MonoTable
import kotlin.math.max

fun calculateColumnWidths(table: MonoTable<String>) : IntArray{
    val result = IntArray(table.getColumns())
    for ( i in 0 until table.getColumns()){
        result[i] = max( calculateStringWidthOfColumn(table, i), 1 )
    }
    return result
}

fun calculateStringWidthOfColumn(table: MonoTable<String>, column : Int) : Int{
    var result = 0
    for (row in 0 until table.getRows()){
        val size = table[column, row].length
        if( size > result ) result = size
    }
    return result
}

fun <E> calculateColumnWidths(table: MonoTable<E>, mapper: (element : E) -> String) : IntArray{
    val result = IntArray(table.getColumns())
    for ( i in 0 until table.getColumns()){
        result[i] = max( calculateStringWidthOfColumn(table, mapper, i), 1 )
    }
    return result
}

fun <E> calculateStringWidthOfColumn(table: MonoTable<E>, mapper: (cell: E) -> String, column : Int) : Int{
    var result = 0
    for (row in 0 until table.getRows()){
        val size = mapper.invoke( table[column, row] ).length
        if( size > result ) result = size
    }
    return result
}