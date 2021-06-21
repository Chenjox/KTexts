package chenjox.util.table.mono

interface MutableFunctionalMonoTable<E> : FunctionalMonoTable<E>, MutableMonoTable<E> {

    operator fun invoke(column: Int, row: Int, func: MonoTableAccessor<E>.(currentCol : Int, currentRow : Int) -> E)

    operator fun set(column: Int, row: Int, func: MonoTableRelativeAccessor<E>.() -> E){
        this(column, row) { relColumn, relRow ->
            getAccessor().relative(relColumn, relRow, null, func)
        }
    }

    fun setColumnFunc(column: Int, func: MonoTableAccessor<E>.(currentCol: Int, currentRow: Int) -> E)

    fun setRowFunc(row: Int, func: MonoTableAccessor<E>.(currentCol: Int, currentRow: Int) -> E)

}