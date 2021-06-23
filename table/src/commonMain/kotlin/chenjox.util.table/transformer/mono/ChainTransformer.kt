package chenjox.util.table.transformer.mono

import chenjox.util.table.mono.MonoTable

fun <E,I,R> ((E) -> I).then(func: (I) -> R): (E) -> R{
    return {
        func(this(it))
    }
}

fun <E,I,R> MonoTableTransformer<E, MonoTable<I>>.then(func: MonoTableTransformer<I, R>) : MonoTableTransformer<E,R>{
    return object : MonoTableTransformer<E,R>{
        override fun invoke(table: MonoTable<E>): R = func( this@then(table) )
    }
}