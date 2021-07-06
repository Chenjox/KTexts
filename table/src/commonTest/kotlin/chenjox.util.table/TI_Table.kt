package chenjox.util.table

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.todo

/** Test Interface for a Table! */
interface TI_Table {

    fun createSupplier() : TI_Table_InstanceSupplier

    @Test
    fun `GetColumns_Correctness`(){
        val s = createSupplier()
        assertEquals( s.getColumns(), s.getInstance().getColumns() )
    }

    @Test
    fun `GetRows_Correctness`(){
        val s = createSupplier()
        assertEquals( s.getRows(), s.getInstance().getRows())
    }


    @Test
    fun `GetAsString_Correctness`(){
        todo {
            TODO("Where is the correct result?")
        }
    }

    @Test
    fun `GetAsString_Exceptions`(){
        val s = createSupplier()
        assertFailsWith(IndexOutOfBoundsException::class, "A Index higher-or-equal than getRows() should throw an Error!") {
            val t = s.getInstance()
            val r = t.getAsString( 0, s.getRows() )
        }
        assertFailsWith(IndexOutOfBoundsException::class, "A Index higher-or-equal than getColumns() should throw an Error!"){
            val t = s.getInstance()
            val r = t.getAsString( s.getColumns(), 0 )
        }
        assertFailsWith(IndexOutOfBoundsException::class, "A Index less than -1 should throw an Error!"){
            val t = s.getInstance()
            val r = t.getAsString( -1, 0 )
        }
        assertFailsWith(IndexOutOfBoundsException::class, "A Index less than -1 should throw an Error!"){
            val t = s.getInstance()
            val r = t.getAsString( 0, -1 )
        }
    }

}

interface TI_Table_InstanceSupplier {
    fun getInstance() : Table

    fun getColumns() : Int
    fun getRows() : Int
}