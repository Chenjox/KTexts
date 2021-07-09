package chenjox.util.table.multi

import kotlin.reflect.KClass

public interface ColumnMultiTable : MultiTable {

    public fun <T : Any> getColumn(column: Int, clazz: KClass<T>) : List<T>

    public fun getRow(row: Int) : List<*>


}