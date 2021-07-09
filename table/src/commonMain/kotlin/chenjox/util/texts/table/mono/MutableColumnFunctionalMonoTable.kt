package chenjox.util.table.mono

public interface MutableColumnFunctionalMonoTable<E> : MutableColumnMonoTable<E>, FunctionalMonoTable<E> {

    public operator fun invoke(column: Int, row: Int, func: MonoTableAccessor<E>.(currentCol : Int, currentRow : Int) -> E)

    public operator fun set(column: Int, row: Int, func: MonoTableRelativeAccessor<E>.() -> E){
        this(column, row) { relColumn, relRow ->
            getAccessor().relative(relColumn, relRow, null, func)
        }
    }

    public fun setColumnFunc(column: Int, func: MonoTableAccessor<E>.(currentCol: Int, currentRow: Int) -> E)

    public fun addColumnFunc(column: Int, func: MonoTableAccessor<E>.(currentCol: Int, currentRow: Int) -> E)

}