package chenjox.util.table.multi

import chenjox.util.table.Table
import kotlin.reflect.KClass

/** A contract specifying how a MultiTable should behave, this implementation leaves room for a immutable implementation
 *  This Interface should not be used directly, instead a row-based table or a column-based table should be used.
 * */
public interface MultiTable : Table{

    /** Returns the specified Element with the given [clazz] at the position [column] [row].
     * @throws chenjox.util.table.TableException if the Element does not match the given class */
    public operator fun <T : Any> get(column : Int, row : Int, clazz : KClass<T>) : T

    // TODO MultiTable muss vllt noch ausgebaut werden, vorallem was #contains() etc an geht.
}