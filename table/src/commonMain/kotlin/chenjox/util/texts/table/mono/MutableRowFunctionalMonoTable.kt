package chenjox.util.table.mono

public interface MutableRowFunctionalMonoTable<E> : MutableRowMonoTable<E>, FunctionalMonoTable<E> {

    public fun setRowFunc(row: Int, func: MonoTableAccessor<E>.(currentCol: Int, currentRow: Int) -> E)

    public fun addRowFunc(row: Int, func: MonoTableAccessor<E>.(currentCol: Int, currentRow: Int) -> E)
}