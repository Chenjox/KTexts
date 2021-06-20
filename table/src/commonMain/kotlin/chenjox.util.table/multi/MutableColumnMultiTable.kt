package chenjox.util.table.multi

import kotlin.reflect.KClass

interface MutableColumnMultiTable : ColumnMultiTable, MutableMultiTable {

    fun <T : Any> setColumn(column: Int, new : List<T>, clazz: KClass<T>)

    fun <T : Any> addColumn(position : Int, new : List<T>, clazz: KClass<T>)

    fun <T : Any> addColumn(new : List<T>, clazz: KClass<T>){
        addColumn(getColumns(), new, clazz)
    }

    fun addRow(new : List<Any>, position: Int)
}