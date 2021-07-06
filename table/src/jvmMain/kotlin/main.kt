import chenjox.util.table.mono.*
import chenjox.util.table.mono.addRow
import chenjox.util.table.templates.DoubleColumnTable
import chenjox.util.table.transformer.mono.*
import chenjox.util.table.transformer.mono.ascii.AsciiMonoTransformer
import kotlin.math.roundToInt

fun main(){

    val t = ArrayMonoTable(5, 3) {
        column: Int, row: Int ->
        (column+1)+(row+1)*5
    }

    println(AsciiMonoTransformer<Int>{ it.toString() }.invoke(t))

    t.addColumn(column = 5, new = listOf(100, 100, 100))
    t.addRow(row = 3, new = listOf(200, 200, 200, 200, 200, 200))

    println(AsciiMonoTransformer<Int>{ it.toString() }.invoke(t))

}

fun dsl() {

    val t = DoubleColumnTable(
        listOf(
            null,
            null,
            null,
            { left(1)+left(2) },
            { left(2)*left(3) },
            { left(4)-left(5) }
        )
        ,
        SimpleTransformer<Double, String> { (it * 100).roundToInt().toString() }
            .then( SimpleTransformer { it.dropLast(2)+"."+it.drop( maxOf(it.length-2, 0 )) } )
            .then( HeaderTransformer( "H1", "H2", "H3", "H4", "H5" ) )
            .then( AlignmentTransformer(Alignment.RIGHT) )
            .then( PaddingTransformer() )
            .then( AsciiMonoTransformer() )
    )

    t.addRow( 2.0, 3.0, 4.0)
    t.addRow(3.0, -20.0, 40.0)

    //TODO HeaderTransformer
    //TODO Padding on left and right

    println( t.render() )
}