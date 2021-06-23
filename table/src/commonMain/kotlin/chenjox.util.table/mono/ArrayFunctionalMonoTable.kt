package chenjox.util.table.mono

class ArrayFunctionalMonoTable<E>(columns: Int = 1, rows: Int = 1) : MutableFunctionalMonoTable<E>{

    private val backingList: MutableList<MutableList<TableCell<E>>> = ArrayList()
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
        return if(backingList.isEmpty()) 0
        else backingList.size
    }

    override fun getRows(): Int {
        return if(backingList.isEmpty()) 0
        else backingList[0].size
    }

    override fun getAsString(column: Int, row: Int): String {
        return backingList[column][row].getValue(column, row).toString()
    }

    override fun get(column: Int, row: Int): E {
        checkBounds(column, row)
        return backingList[column][row].getValue(column, row)
    }

    override fun getFunction(column: Int, row: Int): MonoTableAccessor<E>.(currentColumn: Int, currentRow: Int) -> E {
        TODO("Not yet implemented")
    }

    override fun getAccessor(): MonoTableAccessor<E> {
        return access
    }

    override fun getColumn(column: Int): List<E> {
        checkColumnBounds(column)
        return List(getRows()) {
            this@ArrayFunctionalMonoTable[column, it]
        }
    }

    override fun getRow(row: Int): List<E> {
        TODO("Not yet implemented")
    }

    override fun contains(element: E): Boolean {
        TODO("Not yet implemented")
    }

    override fun isEmpty(): Boolean {
        TODO("Not yet implemented")
    }

    override fun invoke(column: Int, row: Int, func: MonoTableAccessor<E>.(currentCol: Int, currentRow: Int) -> E) {
        checkBounds(column, row)
        val e = backingList[column][row]
        if (e is ValueCell){
            backingList[column][row] = FunctionalCell(func, access)
        } else if (e is FunctionalCell){
            e.func = func
        }
    }
    override fun setColumnFunc(column: Int, func: MonoTableAccessor<E>.(currentCol: Int, currentRow: Int) -> E) {
        TODO("Not yet implemented")
    }

    override fun setRowFunc(row: Int, func: MonoTableAccessor<E>.(currentCol: Int, currentRow: Int) -> E) {
        TODO("Not yet implemented")
    }

    override fun set(column: Int, row: Int, element: E) {
        checkBounds(column, row)
        val e = backingList[column][row]
        if (e is FunctionalCell){
            backingList[column][row] = ValueCell(element)
        } else if (e is ValueCell){
            e.element = element
        }
    }

    override fun setColumn(column: Int, new: List<E>) {
        checkColumnBounds(column)
        checkColumnSize(new)
        for (i in 0 until getRows()){
            backingList[column][i] = ValueCell(new[i])
        }
    }


    override fun addColumn(column: Int, new: List<E>) {
        checkColumnSize(new)
        val columnList : MutableList<TableCell<E>> = ArrayList()
        columnList.addAll( new.map { ValueCell(it) } )
        backingList.add(column, columnList)
    }

    override fun setRow(row: Int, new: List<E>) {
        checkRowBounds(row)
        checkRowSize(new)
        for (i in 0 until getColumns()){
            backingList[i][row] = ValueCell(new[i])
        }
    }

    override fun addRow(row: Int, new: List<E>) {
        checkRowSize(new)
        if(getColumns() == 0) rowInit( new ) else
        for (i in 0 until getColumns()){
            backingList[i].add(row, ValueCell(new[i]))
        }
    }

    private fun rowInit( new: List<E>){
        for (e in new){
            backingList.add( MutableList(1) { ValueCell(e) })
        }
    }
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