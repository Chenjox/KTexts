package chenjox.util.table.templates

import chenjox.util.table.mono.*

/**
 * A Template Table that restricts access to the setting of certain fields.
 * its getters refer to the table as a whole, while the setters only refer to the specified fields
 */
public class ColumnFunctionalTable<E>(
    list: List<(MonoTableAccessor<E>.(currentCol : Int, currentRow : Int) -> E)?>,
    delegate: MutableFunctionalMonoTable<E>,
    private val initializer: () -> E
) : MutableRowMonoTable<E> by delegate
{

    private val backingTable: MutableFunctionalMonoTable<E>
    private val funcArray: Array<MonoTableAccessor<E>.(currentCol : Int, currentRow : Int) -> E>
    private val funcIndeces: IntArray
    private val readWriteIndeces: IntArray

    init {
        val rwI = MutableList(0) { 0 }
        val fI = MutableList(0 ) { 0 }
        for ((i ,e ) in list.withIndex()){
            if(e != null){
                fI.add( i )
            } else {
                rwI.add( i )
            }
        }
        funcArray = list.filterNotNull().toTypedArray()
        funcIndeces = fI.toIntArray()
        readWriteIndeces = rwI.toIntArray()
        backingTable = delegate
    }

    override fun set(column: Int, row: Int, element: E) {
        backingTable[readWriteIndeces[column], row] = element
    }

    public fun setColumn(column: Int, new: List<E>) {
        for (i in 0 until backingTable.getRows()){
            backingTable[readWriteIndeces[column], i] = new[i]
        }
    }

    override fun setRow(row: Int, new: List<E>) {
        if(new.size != readWriteIndeces.size) throw IllegalArgumentException("Size of the supplied List does not match the input size!")
        for (i in readWriteIndeces.indices){
            backingTable[readWriteIndeces[i], row] = new[i]
        }
    }

    override fun addRow(row: Int, new: List<E>) {
        if(new.size != readWriteIndeces.size) throw IllegalArgumentException("Size of the supplied List does not match the input size!")
        val finalList: List<E> = List( readWriteIndeces.size+funcIndeces.size ) {
            initializer.invoke()
        }
        backingTable.addRow(row, finalList)

        // Now the override
        for (i in readWriteIndeces.indices){
            backingTable[readWriteIndeces[i], row] = new[i]
        }
        // Add the functions
        for (i in funcIndeces.indices){
            backingTable(funcIndeces[i], row, funcArray[i])
        }
    }

    /** Important!, behaviour is very different here */
    override fun addRow(new: List<E>) {
        this.addRow(backingTable.getRows(), new)
    }

}