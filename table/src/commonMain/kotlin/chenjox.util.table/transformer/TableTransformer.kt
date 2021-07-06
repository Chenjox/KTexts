package chenjox.util.table.transformer

import chenjox.util.table.Table

@Deprecated("Table Transformer do not adhere to the Singular Responsibility Principle, therefore they should not be used.")
interface TableTransformer<in T : Table, out R> : (T) -> R {

    override operator fun invoke(table: T) : R

}