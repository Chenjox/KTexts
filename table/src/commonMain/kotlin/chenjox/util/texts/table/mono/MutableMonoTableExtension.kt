package chenjox.util.table.mono

public fun <E> MutableMonoTable<E>.addRow(vararg new: E){
    this.addRow(new.asList())
}

public fun <E> MutableMonoTable<E>.addRow(row: Int, vararg new: E){
    this.addRow(row, new.asList())
}

public fun <E> MutableMonoTable<E>.setRow(row: Int, vararg new: E){
    this.setRow(row, new.asList())
}

public fun <E> MutableMonoTable<E>.addColumn(vararg new: E){
    this.addColumn(new = new.asList())
}

public fun <E> MutableMonoTable<E>.addColumn(column: Int, vararg new: E){
    this.addColumn(column, new.asList())
}

public fun <E> MutableMonoTable<E>.setColumn(column: Int, vararg new: E){
    this.setColumn(column, new.asList())
}

public fun MutableMonoTable<Int>.addIntColumn(vararg new: Int){
    this.addColumn(new.asList())
}
