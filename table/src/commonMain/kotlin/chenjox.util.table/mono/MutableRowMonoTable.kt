package chenjox.util.table.mono

public interface MutableRowMonoTable<E> : MonoTable<E> {

    public operator fun set(column: Int, row: Int, element: E)

    public fun setRow(row: Int, new: List<E>)

    public fun addRow(row: Int, new: List<E>)

    public fun addRow(new: List<E>){
        addRow(getRows(), new)
    }

}