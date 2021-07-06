package chenjox.util.table.mono

import chenjox.util.table.TableException
import chenjox.util.table.TableException.Companion.CAUSE_ARGUMENT
import chenjox.util.table.TableException.Companion.CAUSE_RETRIEVE

public interface MonoTableAccessor<E> {

    public fun getColumns() : Int

    public fun getRows() : Int

    public operator fun get(column: Int, row: Int) : E

    public fun relative(column: Int, row: Int, fallback: E? = null, func: MonoTableRelativeAccessor<E>.() -> E) : E{
        val r = RelAccessor(fallback, this, column, row)
        return r.func()
    }

    public class RelAccessor<E> internal constructor(private var f : E?, public val accessor: MonoTableAccessor<E>, public val curCol: Int, public val curRow: Int) : MonoTableRelativeAccessor<E> {

        override fun getCurrentRow(): Int = curRow
        override fun getCurrentColumn(): Int = curCol

        override fun get(relColumn: Int, relRow: Int): E {
            return if(curCol + relColumn >= accessor.getColumns() || curCol + relColumn < 0
                || curRow + relRow >= accessor.getRows() || curRow + relRow < 0
            ) {
                fallback ?: throw TableException("$CAUSE_RETRIEVE: Index out of Bounds without a fallback value!")
            }else if(relColumn != 0 || relRow != 0) accessor[curCol + relColumn, curRow + relRow] else throw TableException("$CAUSE_ARGUMENT: A Cell cannot reference itself!")
        }

        override fun getLastValueInRow(row: Int): E {
            return accessor.get(accessor.getColumns()-1, row)
        }

        override fun getLastValueInColumn(column: Int): E {
            return accessor.get(column, accessor.getRows())
        }

        override fun getFirstValueInRow(row: Int): E {
            return accessor.get(0, row)
        }

        override fun getFirstValueInColumn(column: Int): E {
            return accessor.get(column, 0)
        }

        override var fallback: E?
            get() = f
            set(value) { f = value}
    }


}

public interface MonoTableRelativeAccessor<E> {
    public var fallback : E?

    public fun getCurrentRow(): Int
    public fun getCurrentColumn(): Int

    public operator fun get(relColumn: Int, relRow: Int) : E

    public fun getLastValueInRow(row: Int): E
    public fun getLastValueInColumn(column: Int): E

    public fun getFirstValueInRow(row: Int): E
    public fun getFirstValueInColumn(column: Int): E

}

public fun <E> MonoTableRelativeAccessor<E>.left(columns: Int = 1): E = get(-columns, 0)
public fun <E> MonoTableRelativeAccessor<E>.up(rows: Int = 1): E = get(0, -rows)
public fun <E> MonoTableRelativeAccessor<E>.down(rows: Int = 1): E = get(0, rows)
public fun <E> MonoTableRelativeAccessor<E>.right(columns: Int = 1): E = get(columns, 0)
