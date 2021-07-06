package chenjox.util.table.mono

public interface MutableMonoTable<E> : MonoTable<E> {

    /** sets the element at the designated [column] and [row] to the [element] */
    public operator fun set(column : Int, row : Int, element : E)

    public fun setColumn(column: Int, new : List<E>)

    public fun addColumn(column: Int, new: List<E>)

    public fun addColumn(new: List<E>){
        addColumn(getColumns(), new)
    }

    public fun setRow(row: Int, new: List<E>)

    public fun addRow(row: Int, new: List<E>)

    public fun addRow(new: List<E>){
        addRow(getRows(), new)
    }

}