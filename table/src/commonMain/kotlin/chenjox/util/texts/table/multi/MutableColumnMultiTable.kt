package chenjox.util.table.multi

import kotlin.reflect.KClass

public interface MutableColumnMultiTable : ColumnMultiTable, MutableMultiTable {

    public fun <T : Any> setColumn(column: Int, new : List<T>, clazz: KClass<T>)

    public fun <T : Any> addColumn(position : Int, new : List<T>, clazz: KClass<T>)

    public fun <T : Any> addColumn(new : List<T>, clazz: KClass<T>){
        addColumn(getColumns(), new, clazz)
    }

    public fun addRow(new : List<Any>, position: Int)
}