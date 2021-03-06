package chenjox.util.table.multi

import kotlin.reflect.KClass

public interface MutableMultiTable : MultiTable {

    /** Sets the specified value with the given [clazz] at the position [column] [row].
     * @throws chenjox.util.table.TableException if the Element does not match the given class */
    public operator fun <T : Any> set(column : Int, row: Int, element : T ,clazz: KClass<T>)

}