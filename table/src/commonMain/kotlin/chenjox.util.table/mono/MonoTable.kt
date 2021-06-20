package chenjox.util.table.mono

import chenjox.util.table.Table

/** Defines a contract of how a MonoTable should behave an dbe built, this implementation leaves room fo the table to be immutable */
interface MonoTable<out E> : Table {

    /** Returns the Content at the specified [row] and [column] */
    operator fun get( column : Int, row : Int): E

    /** Returns the column at the specified [column] index in a List format */
    fun getColumn(column : Int) : List<E>

    /** Returns the column at the specified [row] index in in a List format */
    fun getRow(row: Int) : List<E>

    //// Collection Based

    operator fun contains(element: @UnsafeVariance E) : Boolean

    fun isEmpty() : Boolean

}


