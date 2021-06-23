package chenjox.util.table.transformer.mono

import chenjox.util.table.mono.MonoTable
import chenjox.util.table.mono.asMutableTable
import chenjox.util.table.mono.map


fun <E> HeaderTransformer(vararg headers: String) : HeaderTransformer<E>{
    return HeaderTransformer(headers.asList())
}

class HeaderTransformer<E>(val headers: List<String>, val mapper: (element : E) -> String = { it.toString() }, val fallback: String = "" ) : MonoTableTransformer<E, MonoTable<String>>{
    override fun invoke(table: MonoTable<E>): MonoTable<String> {
        val t = table.map(mapper).asMutableTable()
        if(headers.size < t.getColumns()){
            val trueHeaders = headers + List(t.getColumns()-headers.size) { fallback }
            t.addRow(0, trueHeaders)
        } else {
            val trueHeaders = headers.dropLast(headers.size-t.getColumns())
            t.addRow(0, trueHeaders)
        }
        return t
    }
}

