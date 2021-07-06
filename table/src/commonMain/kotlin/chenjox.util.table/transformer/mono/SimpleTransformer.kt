package chenjox.util.table.transformer.mono

import chenjox.util.table.mono.MonoTable
import chenjox.util.table.mono.map

@Deprecated("See TableTransformer")
class SimpleTransformer<E,R>(private val mapper: (element: E) -> R) : MonoTableTransformer<E, MonoTable<R>> {
    override fun invoke(table: MonoTable<E>): MonoTable<R> = table.map(mapper)
}