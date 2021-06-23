package chenjox.util.table.templates

import chenjox.util.table.mono.ArrayFunctionalMonoTable
import chenjox.util.table.mono.MonoTableAccessor
import chenjox.util.table.mono.MonoTableRelativeAccessor
import chenjox.util.table.mono.MutableMonoTable
import chenjox.util.table.transformer.mono.*

fun <E> ColumnTable( list: List<(MonoTableRelativeAccessor<E>.() -> E)?>, initializer: E, transformer: MonoTableTransformer<E, String> ) : ColumnTableTemplate<E> {
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
            ArrayFunctionalMonoTable()
        ) { initializer },
        transformer
    )
}

fun DoubleColumnTable(list: List<(MonoTableRelativeAccessor<Double>.() -> Double)?>, transformer: MonoTableTransformer<Double, String>, initializer: Double = 0.0 )
    = ColumnTable( list, initializer, transformer)

class ColumnTableTemplate<E>(private val delegate: MutableMonoTable<E>,private val outputter: MonoTableTransformer<E, String>): MutableMonoTable<E> by delegate {

    fun render(): String = outputter(delegate)

}