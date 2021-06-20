package chenjox.util.table.multi

import kotlin.reflect.KClass

interface ColumnMultiTable : MultiTable {

    fun <T : Any> getColumn(column: Int, clazz: KClass<T>) : List<T>

    fun getRow(row: Int) : List<*>


}