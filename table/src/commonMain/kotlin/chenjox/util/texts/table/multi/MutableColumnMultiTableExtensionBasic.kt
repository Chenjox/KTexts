package chenjox.util.table.multi

public fun MutableColumnMultiTable.addStringColumn(new : List<String>, position : Int = getColumns() ){
    addColumn(
        position = position,
        new = new,
        clazz = String::class
    )
}

public fun MutableColumnMultiTable.addIntColumn(new : List<Int>, position: Int = getColumns() ){
    addColumn(
        position = position,
        new = new,
        clazz = Int::class
    )
}

public fun MutableColumnMultiTable.addDoubleColumn(new : List<Double>, position: Int = getColumns() ){
    addColumn(
        position = position,
        new = new,
        clazz = Double::class
    )
}

public fun MutableColumnMultiTable.addLongColumn(new : List<Long>, position: Int = getColumns() ){
    addColumn(
        position = position,
        new = new,
        clazz = Long::class
    )
}