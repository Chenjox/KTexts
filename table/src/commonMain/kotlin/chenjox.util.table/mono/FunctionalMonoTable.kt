package chenjox.util.table.mono

public interface FunctionalMonoTable<E> : MonoTable<E> {

    /** Returns the function that backs the cell. If no function is supplied, an identity function is returned */
    public fun getFunction(column : Int, row : Int) : MonoTableAccessor<E>.(currentColumn: Int, currentRow: Int) -> E

    public fun getAccessor() : MonoTableAccessor<E>

}