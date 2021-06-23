package chenjox.util.table.transformer.mono

import chenjox.util.table.mono.MonoTable
import chenjox.util.table.transformer.TableTransformer

interface MonoTableTransformer<in E, out R> : TableTransformer<MonoTable<E>, R> {

    fun asSelf() : MonoTableTransformer<E,R> = this

}