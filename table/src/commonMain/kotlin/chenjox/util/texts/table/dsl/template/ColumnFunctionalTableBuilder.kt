package chenjox.util.texts.table.dsl.template

import chenjox.util.texts.table.dsl.TableBuilder
import chenjox.util.texts.table.dsl.TableDsl
import chenjox.util.table.mono.MonoTableAccessor
import chenjox.util.table.mono.MonoTableRelativeAccessor
import chenjox.util.table.templates.ColumnTableTemplate

public fun <E> ColumnTable(init: ColumnTableBuilder<E>.() -> Unit){
    TODO()
}

@TableDsl
public interface ColumnTableBuilder<E> : TableBuilder<ColumnTableTemplate<E>> {

    public fun column(header: String? = null, index: Int? = null) : ColumnSpec<E>
    public fun column(index: Int? = null) : ColumnSpec<E>

}

@TableDsl
public interface ColumnSpec<E> {
    public infix fun withFunction(func: MonoTableRelativeAccessor<E>.() -> E)
    public infix fun withAccessor(func: MonoTableAccessor<E>.(curCol: Int, curRow: Int) -> E)
}

