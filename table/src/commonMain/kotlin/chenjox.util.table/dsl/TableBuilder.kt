package chenjox.util.table.dsl

import chenjox.util.table.Table

@TableDsl
interface TableBuilder<T : Table> {

    fun build() : T

}

@DslMarker
annotation class TableDsl