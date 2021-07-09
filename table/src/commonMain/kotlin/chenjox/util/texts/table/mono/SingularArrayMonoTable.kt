package chenjox.util.table.mono

public fun <E> ArrayMonoTable(columns: Int, rows: Int, func: (column: Int, row: Int) -> E): SingularArrayMonoTable<E>{

    val a : Array<in Any> = Array(columns*rows){
        val row = it % columns
        val col = it / columns
        func.invoke(col, row)
    }
    val res = a.unsafestCast<E>() // Hacky Stuff
    return SingularArrayMonoTable(columns,rows, res)

}

@Suppress("UNCHECKED_CAST")
private fun <E> Array<*>.unsafestCast() = this as Array<E>

public class SingularArrayMonoTable<E>(columns: Int, rows: Int, startArray: Array<E>) : MutableMonoTable<E> {

    private var backingArray: Array<E> = startArray
    private var width: Int = columns
    private var height: Int = rows

    private fun getFromArray(column: Int, row: Int): E {
        val index = row * width + column
        return backingArray[index]
    }

    private fun getColumnFromArray(column: Int): List<E> {
        return List(height) {
            backingArray[it * width + column]
        }
    }

    private fun getRowFromArray(row: Int): List<E>{
        return List(width) {
            backingArray[row * width + it]
        }
    }

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

    override fun getColumns(): Int {
        return width
    }

    override fun getRows(): Int {
        return height
    }

    override fun getAsString(column: Int, row: Int): String {
        checkBounds(column, row)
        return getFromArray(column, row).toString()
    }

    override fun get(column: Int, row: Int): E {
        checkBounds(column, row)
        return getFromArray(column, row)
    }

    override fun getColumn(column: Int): List<E> {
        checkColumnBounds(column)
        return getColumnFromArray(column)
    }

    override fun getRow(row: Int): List<E> {
        checkRowBounds(row)
        return getRowFromArray(row)
    }

    override fun contains(element: E): Boolean = backingArray.contains(element)

    override fun isEmpty(): Boolean = backingArray.isEmpty()

    override fun set(column: Int, row: Int, element: E) {
        checkBounds(column, row)
        val index = row * width + column
        backingArray[index] = element
    }

    override fun setColumn(column: Int, new: List<E>) {
        checkColumnBounds(column)
        checkColumnSize(new)
        for (i in new.indices){
            backingArray[i * width + column] = new[i]
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
            if(j==0) new[k] else old[k]
        }

        width++
        backingArray = newArray.unsafestCast() // believe me, it is a Array of E, you just cant prove it, compiler!!
    }

    override fun setRow(row: Int, new: List<E>) {
        checkRowBounds(row)
        checkRowSize(new)
        for (i in new.indices){
            backingArray[row * width + i] = new[i]
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
            if(j==row) new[k] else old[k]
        }

        height++
        backingArray = newArray.unsafestCast() // believe me, it is a Array of E, you just cant prove it, compiler!!
    }
}
