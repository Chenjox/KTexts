package chenjox.util.table.multi.qol

import chenjox.util.table.multi.MutableColumnMultiTable

fun MutableColumnMultiTable.addStringColumn(new : List<String>, position : Int = getColumns() ){
    addColumn(
        position = position,
        new = new,
        clazz = String::class
    )
}

fun MutableColumnMultiTable.addIntColumn(new : List<Int>, position: Int = getColumns() ){
    addColumn(
        position = position,
        new = new,
        clazz = Int::class
    )
}

fun MutableColumnMultiTable.addDoubleColumn(new : List<Double>, position: Int = getColumns() ){
    addColumn(
        position = position,
        new = new,
        clazz = Double::class
    )
}

fun MutableColumnMultiTable.addLongColumn(new : List<Long>, position: Int = getColumns() ){
    addColumn(
        position = position,
        new = new,
        clazz = Long::class
    )
}