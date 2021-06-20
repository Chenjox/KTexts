package chenjox.util.table.renderer

import chenjox.util.table.Table

abstract class TableRenderer(val table: Table) {

    /** Returns the underlying table in a rendered state */
    abstract fun render() : String

}