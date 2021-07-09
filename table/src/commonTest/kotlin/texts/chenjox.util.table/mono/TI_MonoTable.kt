package chenjox.util.table.mono

import chenjox.util.table.TI_Table
import chenjox.util.table.TI_Table_InstanceSupplier
import chenjox.util.table.Table
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.todo

interface TI_MonoTable<T> : TI_Table {

    override fun createSupplier(): TI_MonoTable_InstanceSupplier<T>

    @Test
    fun `get_Exceptions`(){
        val s = createSupplier()
        assertFailsWith(IndexOutOfBoundsException::class, "A Index higher-or-equal than getRows() should throw an Error!") {
            val t = s.getInstance()
            val r = t[0, s.getRows()]
        }
        assertFailsWith(IndexOutOfBoundsException::class, "A Index higher-or-equal than getColumns() should throw an Error!"){
            val t = s.getInstance()
            val r = t[s.getColumns(), 0]
        }
        assertFailsWith(IndexOutOfBoundsException::class, "A Index less than -1 should throw an Error!"){
            val t = s.getInstance()
            val r = t[-1, 0]
        }
        assertFailsWith(IndexOutOfBoundsException::class, "A Index less than -1 should throw an Error!"){
            val t = s.getInstance()
            val r = t[0, -1]
        }
    }

    @Test
    fun `get_Correctness`(){
        val s = createSupplier()
        val t = s.getInstance()
        for (i in 0 until s.getColumns())
            for (j in 0 until s.getRows()){
                assertEquals(s.getCorrect(i,j), t[i,j])
            }
    }

    @Test
    fun `getRow_Correctness`(){
        todo {
            TODO()
        }
    }

    @Test
    fun `getRow_Exceptions`(){
        val s = createSupplier()
        assertFailsWith(IndexOutOfBoundsException::class, "A Index higher-or-equal than getRows() should throw an Error!") {
            val t = s.getInstance()
            val r = t.getRow( s.getRows() )
        }
        assertFailsWith(IndexOutOfBoundsException::class, "A Index less than -1 should throw an Error!"){
            val t = s.getInstance()
            val r = t.getRow( -1 )
        }
    }

    @Test
    fun `getColumn_Correctness`(){
        todo {
            TODO()
        }
    }

    @Test
    fun `getColumn_Exceptions`(){
        todo {
            TODO()
        }
    }

    @Test
    fun `contains_Correctness`(){
        todo {
            TODO()
        }
    }

    @Test
    fun `isEmpty_Correctness`(){
        todo {
            TODO()
        }
    }
}

interface TI_MonoTable_InstanceSupplier<T> : TI_Table_InstanceSupplier {
    override fun getInstance() : MonoTable<T>

    fun getCorrect(col: Int, row: Int) : T
    fun getCorrectColumn(col : Int) : List<T>
    fun getCorrectRow(row : Int) : List<T>
}
