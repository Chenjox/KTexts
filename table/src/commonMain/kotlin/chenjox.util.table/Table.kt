package chenjox.util.table

/** A universal contract how _every_ table should behave. Especially helpful for rendering */
public interface Table {

    /** Returns the number of columns */
    public fun getColumns() : Int

    /** Returns the number of rows */
    public fun getRows() : Int

    public fun getAsString(column : Int, row : Int) : String

}