package chenjox.util.table.mono

import chenjox.util.table.Table

public fun <E> MonoTable(columns: Int, rows: Int, func: (column: Int, row: Int) -> E): MonoTable<E>{
    return ArrayMonoTable(columns, rows, func)
}

/** Defines a contract of how a MonoTable should behave and be built, this implementation leaves room fo the table to be immutable */
public interface MonoTable<out E> : Table {

    /** Returns the Content at the specified [row] and [column] */
    public operator fun get( column : Int, row : Int): E

    /** Returns the column at the specified [column] index in a List format */
    public fun getColumn(column : Int) : List<E>

    /** Returns the column at the specified [row] index in in a List format */
    public fun getRow(row: Int) : List<E>

    //// Collection Based

    public operator fun contains(element: @UnsafeVariance E) : Boolean

    public fun isEmpty() : Boolean

}


