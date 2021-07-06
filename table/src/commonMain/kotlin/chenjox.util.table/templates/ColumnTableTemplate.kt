package chenjox.util.table.templates

import chenjox.util.table.mono.*

public fun <E> ColumnTable( list: List<(MonoTableRelativeAccessor<E>.() -> E)?>, initializer: E, renderer: (MonoTable<E>) -> String ) : ColumnTableTemplate<E> {
    return ColumnTableTemplate(
        ColumnFunctionalTable(
            list.map {
                if( it != null ) {
                    val t: (MonoTableAccessor<E>.(currentCol: Int, currentRow: Int) -> E) = { currentCol, currentRow ->
                        relative(currentCol, currentRow, null, it)
                    }
                    return@map t
                } else null
            },
            ArrayFunctionalMonoTable { _, _ -> initializer }
        ) { initializer },
        renderer
    )
}

public fun DoubleColumnTable(list: List<(MonoTableRelativeAccessor<Double>.() -> Double)?>, initializer: Double = 0.0, renderer: (MonoTable<Double>) -> String ): ColumnTableTemplate<Double>
    = ColumnTable( list, initializer, renderer)

public class ColumnTableTemplate<E>(private val delegate: MutableMonoTable<E>, private val renderer: (MonoTable<E>) -> String): MutableMonoTable<E> by delegate {

    public fun render(): String = renderer(this)

}