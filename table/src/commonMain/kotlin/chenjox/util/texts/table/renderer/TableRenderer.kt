package chenjox.util.table.renderer

import chenjox.util.table.Table

public interface TableRenderer<E> {

    public operator fun invoke(table: Table): E

}