package chenjox.util.table.mono

interface MutableMonoTable<E> : MonoTable<E> {

    /** sets the element at the designated [column] and [row] to the [element] */
    operator fun set(column : Int, row : Int, element : E)

    fun setColumn(column: Int, new : List<E>)

    fun addColumn(column: Int, new: List<E>)

    fun addColumn(new: List<E>){
        addColumn(getColumns(), new)
    }

    fun setRow(row: Int, new: List<E>)

    fun addRow(row: Int, new: List<E>)

    fun addRow(new: List<E>){
        addRow(getRows(), new)
    }

}