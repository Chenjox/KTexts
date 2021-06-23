package chenjox.util.table.dsl.mono

import chenjox.util.table.dsl.TableBuilder
import chenjox.util.table.dsl.TableDsl
import chenjox.util.table.mono.*

fun <E> Mono(initialValue: E, init: MonoTableBuilder<E>.() -> Unit) : MonoTable<E>{
    val m = MonoTableBuilder(initialValue)
    m.init()
    return m.build()
}

class MonoTableBuilder<E>internal constructor(val intializer: E) : TableBuilder<MonoTable<E>> {

    private val columnBuilders : MutableList<MonoColumnBuilder<E>> = ArrayList()
    private val cellBuilders : MutableList<MonoCellBuilder<E>> = ArrayList()

    private var minColumns = 0
    private var minRows = 0

    fun dimensions(columns: Int, rows: Int){
        minColumns = columns
        minRows = rows
    }

    fun column(index: Int = columnBuilders.lastIndex, init: MonoColumnBuilder<E>.() -> Unit){
        val c = MonoColumnBuilder<E>(index)
        c.init()
        columnBuilders.add( c )
    }

    fun cell(column: Int, row: Int, init: MonoCellBuilder<E>.() -> Unit){
        val c = MonoCellBuilder<E>(column, row)
        c.init()
        cellBuilders.add( c )
    }

    override fun build(): MonoTable<E> {
        val isFunctionalTable = columnBuilders.any { it.func != null } || cellBuilders.any { it.func != null }
        if(isFunctionalTable){
            val maxCols = maxOf( columnBuilders.maxOf { it.index }+1, cellBuilders.maxOf { it.column }+1, minColumns )
            val maxRows = maxOf(cellBuilders.maxOf { it.row }+1, minRows)
            val fmt : MutableFunctionalMonoTable<E> = ArrayFunctionalMonoTable(maxCols, maxRows)
            populateTable(maxCols, maxRows, fmt)
            for (i in 0 until fmt.getColumns()) {
                columnBuilders.firstOrNull { it.index == i }?.build(fmt)
            }
            for (cell in cellBuilders){
                cell.build(fmt)
            }
            return fmt
        }else{
            val maxCols = maxOf( columnBuilders.maxOf { it.index }+1, cellBuilders.maxOf { it.column }+1, minColumns )
            val maxRows = maxOf(cellBuilders.maxOf { it.row }+1, minRows)
            val fmt : MutableMonoTable<E> = ArrayMonoTable()
            populateTable(maxCols, maxRows, fmt)
            for (i in 0 until fmt.getColumns()) {
                columnBuilders.firstOrNull { it.index == i }?.build(fmt)
            }
            for (cell in cellBuilders){
                cell.build(fmt)
            }
            return fmt
        }
    }

    private fun populateTable(columns: Int, rows: Int ,t: MutableMonoTable<E>){
        for (i in 0 until columns){
            t.addColumn( List(rows) { intializer })
        }
    }

}

@TableDsl
class MonoColumnBuilder<E> internal constructor(val index: Int){
    public var value: E? = null

    internal var func: (MonoTableRelativeAccessor<E>.() -> E)? = null

    fun withFunctional( func: MonoTableRelativeAccessor<E>.() -> E ){
        this.func = func
    }
    internal fun build(table: MutableFunctionalMonoTable<E>){
        if(value != null && func != null) throw IllegalArgumentException("You can either define a function, or a value!")
        for (j in 0 until table.getRows()){
            if(value != null) table[index, j] = value!!
            else if(func != null) table[index, j] = func!!
        }
    }
    internal fun build(table: MutableMonoTable<E>){
        if(func != null) throw AssertionError() // technically unreachable...
        for (j in 0 until table.getRows()){
            if(value != null) table[index, j] = value!!
        }
    }
}

@TableDsl
class MonoCellBuilder<E> internal constructor(val column: Int, val row: Int){
    var value: E? = null

    internal var func: (MonoTableRelativeAccessor<E>.() -> E)? = null

    fun withFunctional( func: MonoTableRelativeAccessor<E>.() -> E ){
        this.func = func
    }
    internal fun build(table: MutableFunctionalMonoTable<E>){
        if(value != null && func != null) throw IllegalArgumentException("You can either define a function, or a value!")
        if(value != null) table[column, row] = value!!
        else if(func != null) table[column, row] = func!!
    }

    internal fun build(table: MutableMonoTable<E>){
        if(func != null) throw AssertionError() // technically unreachable...
        if(value != null) table[column, row] = value!!
    }
}