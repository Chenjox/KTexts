package chenjox.util.table.mono


@Suppress("UNCHECKED_CAST")
private fun <E> Array<*>.unsafestCast() = this as Array<E>

public class ArrayFunctionalMonoTable<E>(columns: Int = 1, rows: Int = 1, func: (column: Int, row: Int) -> E) : MutableFunctionalMonoTable<E>{

    private var backingArray: Array<TableCell<E>>
    private var width: Int = columns
    private var height: Int = rows

    init {
        val a : Array<in Any> = Array(columns*rows){
            val row = it % columns
            val col = it / columns
            ValueCell(func.invoke(col, row))
        }
        val res = a.unsafestCast<ValueCell<E>>() // Hacky Stuff

        backingArray = res.unsafestCast()
    }

    //TODO Change to singular Array of E

    private fun setArrayAsCell(column: Int, row: Int, cell: TableCell<E>){
        val index = row * width + column
        backingArray[index] = cell
    }

    private fun getFromArrayAsCell(column: Int, row: Int): TableCell<E> {
        val index = row * width + column
        return backingArray[index]
    }

    private fun getFromArray(column: Int, row: Int): E {
        val index = row * width + column
        return backingArray[index].getValue(column, row)
    }

    private fun getColumnFromArray(column: Int): List<E> {
        return List(height) {
            backingArray[it * width + column].getValue( column, it )
        }
    }

    private fun getRowFromArray(row: Int): List<E>{
        return List(width) {
            backingArray[row * width + it].getValue( it, row)
        }
    }

    private val access: MonoTableAccessor<E> by lazy { Accessor() }

    private fun checkBounds(column: Int, row: Int){
        checkColumnBounds(column)
        checkRowBounds(row)
    }
    private fun checkColumnBounds(columnIndex: Int){
        if((columnIndex >= getColumns() || columnIndex < 0) && getColumns()!=0) throw IndexOutOfBoundsException("Size: ${getColumns()}, Actual: $columnIndex")
    }
    private fun checkRowBounds(rowIndex: Int){
        if((rowIndex >= getRows() || rowIndex < 0) && getRows()!=0) throw IndexOutOfBoundsException("Size: ${getRows()}, actual: $rowIndex")
    }
    private fun checkRowSize(newRow: List<E>){
        if(newRow.size!=getColumns() && getColumns()!=0) throw IllegalArgumentException("Size: ${getColumns()}, actual: $newRow")
    }
    private fun checkColumnSize(newColumn: List<E>){
        if(newColumn.size!=getRows() && getRows()!=0) throw IllegalArgumentException("Size: ${getRows()}, actual: $newColumn")
    }

    internal inner class Accessor : MonoTableAccessor<E> {
        override fun get(column: Int, row: Int): E {
            return this@ArrayFunctionalMonoTable[column, row]
        }

        override fun getColumns(): Int {
            return this@ArrayFunctionalMonoTable.getColumns()
        }

        override fun getRows(): Int {
            return this@ArrayFunctionalMonoTable.getRows()
        }
    }

    override fun getColumns(): Int {
        return width
    }

    override fun getRows(): Int {
        return height
    }

    override fun getAsString(column: Int, row: Int): String {
        return getFromArray(column, row).toString()
    }

    override fun get(column: Int, row: Int): E {
        checkBounds(column, row)
        return getFromArray(column, row)
    }

    override fun getFunction(column: Int, row: Int): MonoTableAccessor<E>.(currentColumn: Int, currentRow: Int) -> E {
        TODO("Not yet implemented")
    }

    override fun getAccessor(): MonoTableAccessor<E> {
        return access
    }

    override fun getColumn(column: Int): List<E> {
        checkColumnBounds(column)
        return getColumnFromArray(column)
    }

    override fun getRow(row: Int): List<E> {
        return getRowFromArray(row)
    }

    override fun contains(element: E): Boolean {
        TODO("Whatever contains means...")
    }

    override fun isEmpty(): Boolean {
        return backingArray.isEmpty()
    }

    override fun invoke(column: Int, row: Int, func: MonoTableAccessor<E>.(currentCol: Int, currentRow: Int) -> E) {
        checkBounds(column, row)
        val e = getFromArrayAsCell(column, row)
        if (e is ValueCell){
            setArrayAsCell(column ,row, FunctionalCell(func, access) )
        } else if (e is FunctionalCell){
            e.func = func
        }
    }

    override fun setColumnFunc(column: Int, func: MonoTableAccessor<E>.(currentCol: Int, currentRow: Int) -> E) {
        TODO("Not yet implemented")
    }

    override fun addColumnFunc(column: Int, func: MonoTableAccessor<E>.(currentCol: Int, currentRow: Int) -> E) {
        TODO("Not yet implemented")
    }

    override fun setRowFunc(row: Int, func: MonoTableAccessor<E>.(currentCol: Int, currentRow: Int) -> E) {
        TODO("Not yet implemented")
    }

    override fun addRowFunc(row: Int, func: MonoTableAccessor<E>.(currentCol: Int, currentRow: Int) -> E) {
        TODO("Not yet implemented")
    }

    override fun set(column: Int, row: Int, element: E) {
        checkBounds(column, row)
        val e = getFromArrayAsCell(column, row)
        if (e is FunctionalCell){
            setArrayAsCell(column ,row, ValueCell(element) )
        } else if (e is ValueCell){
            e.element = element
        }
    }

    override fun setColumn(column: Int, new: List<E>) {
        checkColumnBounds(column)
        checkColumnSize(new)
        for (i in new.indices){
            backingArray[i * width + column] = ValueCell(new[i])
        }
    }


    override fun addColumn(column: Int, new: List<E>) {
        checkColumnSize(new)
        val old: Array<*> = backingArray.copyOf()
        val newArray: Array<*>
        var counter = 0
        newArray = Array( (width+1)*height ) {
            val j = (it+(width+1-column)) % (width+1)
            val k = if(j==0) {
                counter++
            } else {
                it - counter
            }
            if(j==0) ValueCell<E>(new[k]) else old[k]
        }

        width++
        backingArray = newArray.unsafestCast()
    }

    override fun setRow(row: Int, new: List<E>) {
        checkRowBounds(row)
        checkRowSize(new)
        for (i in new.indices){
            backingArray[row * width + i] = ValueCell(new[i])
        }
    }

    override fun addRow(row: Int, new: List<E>) {
        checkRowSize(new)
        val old: Array<*> = backingArray.copyOf()
        val newArray: Array<*>
        var counter = 0
        newArray = Array( (width)*(height+1) ) {
            val j = it / (width)
            val k = if(j==row) {
                counter++
                it - j*width
            } else it - (counter)
            if(j==row) ValueCell(new[k]) else old[k]
        }

        height++
        backingArray = newArray.unsafestCast()
    }

    /*
    private fun rowInit( new: List<E>){
        for (e in new){
            backingList.add( MutableList(1) { ValueCell(e) })
        }
    }
     */
}

internal sealed class TableCell<E> {
    abstract fun getValue(currentCol: Int, currentRow: Int) : E
}

internal class ValueCell<E>(var element: E) : TableCell<E>(){
    override fun getValue(currentCol: Int, currentRow: Int): E {
        return element
    }
}

internal class FunctionalCell<E>(var func: MonoTableAccessor<E>.(currentCol: Int, currentRow: Int) -> E, var accessor: MonoTableAccessor<E>) : TableCell<E>(){
    override fun getValue(currentCol: Int, currentRow: Int): E {
        return accessor.func(currentCol, currentRow)
    }
}