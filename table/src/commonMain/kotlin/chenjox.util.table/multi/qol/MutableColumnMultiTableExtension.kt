package chenjox.util.table.multi.qol

import chenjox.util.table.mono.MonoTable
import chenjox.util.table.multi.MutableColumnMultiTable
import kotlin.reflect.KClass

fun <T : Any,E : Any> MutableColumnMultiTable.computeAndAddColumn(function: (T) -> E, originClazz : KClass<T>, mappingClazz : KClass<E>, column : Int, position : Int = this.getColumns()){
    val result : MutableList<E> = ArrayList(getRows())
    for (i in 0 until getRows()){
        val e = this.get( column, i, originClazz )
        result.add( function.invoke(e) )
    }
    addColumn( position, result, mappingClazz )
}

fun MutableColumnMultiTable.mapToStringMonoTable() : MonoTable<String> {
    TODO("MonoTable package must be built!")
}