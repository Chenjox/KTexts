package chenjox.util.table.transformer.mono

import chenjox.util.table.mono.MonoTable
import chenjox.util.table.mono.map

class PaddingTransformer(val padLeft: Int = 1, val padRight: Int = 1) : MonoTableTransformer<String, MonoTable<String>> {
    override fun invoke(table: MonoTable<String>): MonoTable<String> {
        return table.map { it.padEnd( it.length+padRight, ' ' ).padStart( it.length+padRight+padLeft, ' ' ) }
    }
}