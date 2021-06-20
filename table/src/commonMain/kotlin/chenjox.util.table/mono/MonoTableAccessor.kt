package chenjox.util.table.mono

interface MonoTableAccessor<E> {

    operator fun get(column: Int, row: Int) : E

}