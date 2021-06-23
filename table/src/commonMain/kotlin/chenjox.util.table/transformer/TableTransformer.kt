package chenjox.util.table.transformer

import chenjox.util.table.Table

interface TableTransformer<in T : Table, out R> : (T) -> R {

    override operator fun invoke(table: T) : R

}