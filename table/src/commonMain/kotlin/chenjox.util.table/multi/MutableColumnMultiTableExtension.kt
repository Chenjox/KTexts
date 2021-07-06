package chenjox.util.table.multi

import chenjox.util.table.mono.ArrayMonoTable
import chenjox.util.table.mono.MonoTable
import chenjox.util.table.mono.MutableMonoTable
import kotlin.reflect.KClass

public fun <T : Any,E : Any> MutableColumnMultiTable.computeAndAddColumn(function: (T) -> E, originClazz : KClass<T>, mappingClazz : KClass<E>, column : Int, position : Int = this.getColumns()){
    val result : MutableList<E> = ArrayList(getRows())
    for (i in 0 until getRows()){
        val e = this.get( column, i, originClazz )
        result.add( function.invoke(e) )
    }
    addColumn( position, result, mappingClazz )
}

public fun MutableColumnMultiTable.asStringMonoTable() : MonoTable<String> {
    val t : MutableMonoTable<String> = ArrayMonoTable()
    for (i in 0 until getRows()) t.addRow( this.getRow(i).map { it.toString() } )
    return t
}