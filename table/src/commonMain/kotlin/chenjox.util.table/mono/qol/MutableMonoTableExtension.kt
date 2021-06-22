package chenjox.util.table.mono.qol

import chenjox.util.table.mono.MutableMonoTable

fun <E> MutableMonoTable<E>.addRow(vararg new: E){
    this.addRow(new.asList())
}

fun <E> MutableMonoTable<E>.addRow(row: Int, vararg new: E){
    this.addRow(row, new.asList())
}

fun <E> MutableMonoTable<E>.setRow(row: Int, vararg new: E){
    this.setRow(row, new.asList())
}

fun <E> MutableMonoTable<E>.addColumn(vararg new: E){
    this.addColumn(new = new.asList())
}

fun <E> MutableMonoTable<E>.addColumn(column: Int, vararg new: E){
    this.addColumn(column, new.asList())
}

fun <E> MutableMonoTable<E>.setColumn(column: Int, vararg new: E){
    this.setColumn(column, new.asList())
}

fun MutableMonoTable<Int>.addIntColumn(vararg new: Int){
    this.addColumn(new.asList())
}

infix fun <E> MutableMonoTable<E>.holds(other: Int){

}