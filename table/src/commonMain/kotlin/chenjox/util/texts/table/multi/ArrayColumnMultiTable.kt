package chenjox.util.table.multi

import chenjox.util.table.TableException
import kotlin.reflect.KClass
import kotlin.reflect.cast

public class ArrayColumnMultiTable : MutableColumnMultiTable {

    private var contentClasses : MutableList<KClass<*>> = ArrayList()

    /** column, row */
    private var content : MutableList<MutableList<Any>> = ArrayList()

    private fun checkBounds(column: Int, row: Int){
        checkColumnBounds(column)
        checkRowBounds(row)
    }
    private fun checkColumnBounds(columnIndex: Int){
        if(columnIndex >= getColumns() || columnIndex < 0) throw IndexOutOfBoundsException()
    }
    private fun checkRowBounds(rowIndex: Int){
        if(rowIndex >= getRows() && rowIndex < 0) throw IndexOutOfBoundsException()
    }

    override fun <T : Any> setColumn(column: Int, new: List<T>, clazz: KClass<T>) {
        checkColumnBounds(column)
        if(contentClasses[column] != clazz) throw TableException("${TableException.CAUSE_STORE} Expected Class was ${contentClasses[column].simpleName}, Actual Class was ${clazz.simpleName}")
        content[column] = new.map { e -> Any::class.cast( e ) }.toMutableList()
    }

    override fun <T : Any> addColumn(position: Int, new: List<T>, clazz: KClass<T>) {
        //checkColumnBounds(position) FIXME validation
        contentClasses.add(position, clazz)
        content.add(position, new.toMutableList())
    }

    override fun addRow(new: List<Any>, position: Int) {
        //checkRowBounds(position) FIXME validation
        for (i in 0 until getColumns()){
            if(!contentClasses[i].isInstance( new[i] ) ) throw TableException("${TableException.CAUSE_STORE} Expected Class was ${contentClasses[i].simpleName} at index $i, Actual Class was ${new[i]::class.simpleName}")
        }
        for (i in 0 until getColumns()){
            this.content[i][position] = new[i]
        }
    }

    override fun <T : Any> getColumn(column: Int, clazz: KClass<T>): List<T> {
        checkColumnBounds(column)
        if(contentClasses[column] == clazz){
            return content[column].map { e -> clazz.cast(e) }
        }else throw TableException("${TableException.CAUSE_RETRIEVE} Expected Class was ${contentClasses[column].simpleName}, Actual Class was ${clazz.simpleName}")
    }

    override fun getRow(row: Int): List<Any> {
        checkRowBounds(row)
        val mutList : MutableList<Any> = ArrayList(getColumns())
        for (i in 0 until getColumns()){
            mutList.add( this.getUnsafe(i, row) )
        }
        return mutList
    }

    private fun getUnsafe(column: Int, row: Int): Any {
        return Any::class.cast( content[column][row] )
    }

    override operator fun <T : Any> get(column: Int, row: Int, clazz: KClass<T>): T {
        checkBounds(column, row)
        if(contentClasses[column] == clazz){
            return clazz.cast( content[column][row] )
        }else throw TableException("${TableException.CAUSE_RETRIEVE} Expected Class was ${contentClasses[column].simpleName}, Actual Class was ${clazz.simpleName}")
    }

    override fun getAsString(column: Int, row: Int): String {
        checkBounds(column, row)
        return content[column][row].toString()
    }

    override fun getColumns(): Int {
        return if(content.isEmpty()) 0
        else content.size
    }

    override fun getRows(): Int {
        return if(content.isEmpty()) 0
        else content[0].size
    }

    override operator fun <T : Any> set(column: Int, row: Int, element : T, clazz: KClass<T>) {
        checkBounds(column, row)
        if(contentClasses[column] == clazz){
            content[column][row] = clazz.cast( element )
        }else throw TableException("${TableException.CAUSE_STORE} Expected Class was ${contentClasses[column].simpleName}, Actual Class was ${clazz.simpleName}")
    }
}