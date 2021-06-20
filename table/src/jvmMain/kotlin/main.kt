import chenjox.util.table.mono.ArrayMonoTable
import chenjox.util.table.mono.MonoTable
import chenjox.util.table.mono.MutableMonoTable
import chenjox.util.table.mono.qol.*
import chenjox.util.table.multi.*
import chenjox.util.table.multi.qol.addIntColumn
import chenjox.util.table.multi.qol.addStringColumn
import chenjox.util.table.multi.qol.computeAndAddColumn
import chenjox.util.table.renderer.ascii.AsciiRenderer

fun main(){
    MonoTable()
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