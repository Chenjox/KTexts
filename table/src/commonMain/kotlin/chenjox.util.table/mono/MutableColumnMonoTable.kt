package chenjox.util.table.mono

public interface MutableColumnMonoTable<E> : MonoTable<E> {

    public operator fun set(column: Int, row: Int, element: E)

    public fun setColumn(column: Int, new : List<E>)

    public fun addColumn(column: Int, new: List<E>)

    public fun addColumn(new: List<E>){
        addColumn(getColumns(), new)
    }

}