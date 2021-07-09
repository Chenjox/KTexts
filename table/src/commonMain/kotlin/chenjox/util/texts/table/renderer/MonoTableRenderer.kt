package chenjox.util.table.renderer

import chenjox.util.table.mono.MonoTable

public interface MonoTableRenderer<E,R> : TableRenderer<R> {

    public fun invoke(table: MonoTable<E>): R

}