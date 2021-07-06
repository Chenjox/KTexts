package chenjox.util.table.mono

public interface MutableMonoTable<E> : MutableColumnMonoTable<E>,MutableRowMonoTable<E> {

    /** sets the element at the designated [column] and [row] to the [element] */
    public override operator fun set(column : Int, row : Int, element : E)

}