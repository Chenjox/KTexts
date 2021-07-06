package chenjox.util.table.mono

/*
inline fun <reified E> ArrayMonoTable(columns: Int, rows: Int, func: (column: Int, row: Int) -> E){
}
*/

public class ArrayMonoTable<E>(columns: Int = 1, rows: Int = 1) : MutableMonoTable<E> {

    //private val backingArray: Array<E> = Array<E>(columns*rows) {}

    private val backingList: MutableList<MutableList<E>> = ArrayList()

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
        return if(backingList.isEmpty()) 0
        else backingList.size
    }

    override fun getRows(): Int {
        return if(backingList.isEmpty()) 0
        else backingList[0].size
    }

    override fun getAsString(column: Int, row: Int): String {
        return backingList[column][row].toString()
    }

    override fun get(column: Int, row: Int): E {
        checkBounds(column, row)
        return backingList[column][row]
    }

    override fun getColumn(column: Int): List<E> {
        checkColumnBounds(column)
        return List(getRows()) {
            this@ArrayMonoTable[column, it]
        }
    }

    override fun getRow(row: Int): List<E> {
        checkRowBounds(row)
        TODO("Copying mechanism")
    }

    override fun contains(element: E): Boolean {
        TODO("Not yet implemented")
    }

    override fun isEmpty(): Boolean {
        TODO("Not yet implemented")
    }

    override fun set(column: Int, row: Int, element: E) {
        checkBounds(column, row)
        backingList[column][row] = element
    }

    override fun setColumn(column: Int, new: List<E>) {
        checkColumnBounds(column)
        checkColumnSize(new)
        for (i in 0 until getRows()){
            backingList[column][i] = new[i]
        }
    }

    //should work with addColumn( getColumns(), newlist())

    override fun addColumn(column: Int, new: List<E>) {
        checkColumnSize(new)
        val columnList : MutableList<E> = ArrayList()
        columnList.addAll( new )
        backingList.add(column, columnList)
    }

    override fun setRow(row: Int, new: List<E>) {
        checkRowBounds(row)
        checkRowSize(new)
        for (i in 0 until getColumns()){
            backingList[i][row] = new[i]
        }
    }

    override fun addRow(row: Int, new: List<E>) {
        checkRowSize(new)
        for (i in 0 until getColumns()){
            backingList[i].add(row, new[i])
        }
    }

}