import chenjox.util.table.mono.*
import chenjox.util.table.mono.qol.*
import chenjox.util.table.multi.*
import chenjox.util.table.multi.qol.addIntColumn
import chenjox.util.table.multi.qol.addStringColumn
import chenjox.util.table.renderer.ascii.AsciiRenderer

fun main(){
    funcTable()
}

fun MultiTable(){
    val t : MutableColumnMultiTable = ArrayColumnMultiTable()
    t.addStringColumn( listOf("Test", "OtherTest", "Really a other Test", "What about this") )
    t.addIntColumn( listOf( 3, 4, 5, 7) )

    println( AsciiRenderer(t).render() )

    val a : MutableList<String> = mutableListOf( "Some", "Thing" )
    a.add( a.size, "Third one" )
    println( a.toString() )
}

fun MonoTable(){
    val t : MutableMonoTable<String> = ArrayMonoTable()
    t.addColumn( "Test1", "Test2", "Test4", "Test6" )
    t.addColumn( "Test2", "Test3", "Test5", "Test7" )
    t.addRow( "3", "4" )
    t.setRow( 0, "Another", "One" )
    t.setColumn( 0, "Bites", "The", "Dust", "!", "!")
    println( AsciiRenderer(t).render() )
}

fun funcTable(){
    val t : MutableFunctionalMonoTable<Int> = ArrayFunctionalMonoTable()
    t.addColumn(listOf(2,6,7,8))
    t.addIntColumn(7,8,9,6)
    t.addIntColumn(8, 9, 7, 9)
    val f : MonoTableRelativeAccessor<Int>.() -> Int = {
        fallback = 1
        this[-1,0]*this[0,-1]
    }
    t[1,0] = f
    t[1,1] = f
    t[1,2] = f
    t[1,3] = f
    t[2,0] = f
    t[2,1] = f
    t[2,2] = f
    t[2,3] = f
    println( AsciiRenderer(t).render() )
}