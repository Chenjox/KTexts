package chenjox.util.table.mono

fun <E> MonoTable<E>.asMutableTable() : MutableMonoTable<E> {
    val newTable: MutableMonoTable<E> = ArrayMonoTable()
    for (column in 0 until getColumns()){
        newTable.addColumn( getColumn( column ) )
    }
    return newTable
}

fun <E,R> MonoTable<E>.map(mapper: (element : E) -> R) : MonoTable<R> {
    val newTable: MutableMonoTable<R> = ArrayMonoTable()
    for (column in 0 until getColumns()){
        newTable.addColumn( getColumn( column ).map(mapper) )
    }
    return newTable
}