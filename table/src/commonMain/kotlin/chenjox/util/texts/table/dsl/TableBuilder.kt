package chenjox.util.texts.table.dsl

import chenjox.util.table.Table

@TableDsl
public interface TableBuilder<T : Table> {

    public fun build() : T

}

@DslMarker
public annotation class TableDsl