package chenjox.util.table.mono

public interface MutableFunctionalMonoTable<E> : MutableColumnFunctionalMonoTable<E>, MutableRowFunctionalMonoTable<E>, MutableMonoTable<E> {

    public override operator fun invoke(column: Int, row: Int, func: MonoTableAccessor<E>.(currentCol : Int, currentRow : Int) -> E)

    public override operator fun set(column: Int, row: Int, func: MonoTableRelativeAccessor<E>.() -> E){
        this(column, row) { relColumn, relRow ->
            getAccessor().relative(relColumn, relRow, null, func)
        }
    }

}