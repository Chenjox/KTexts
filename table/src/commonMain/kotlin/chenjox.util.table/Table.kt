package chenjox.util.table

/** A universal contract how _every_ table should behave. Especially helpful for rendering */
interface Table {

    /** Returns the number of columns */
    fun getColumns() : Int

    /** Returns the number of rows */
    fun getRows() : Int

    fun getAsString(column : Int, row : Int) : String

}