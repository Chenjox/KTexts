import chenjox.util.table.dsl.template.ColumnTable
import chenjox.util.table.dsl.template.ascii
import chenjox.util.table.dsl.template.mapper
import chenjox.util.table.dsl.template.padding
import chenjox.util.table.mono.ArrayFunctionalMonoTable
import chenjox.util.table.mono.left
import chenjox.util.table.mono.qol.addRow
import chenjox.util.table.mono.right
import chenjox.util.table.templates.ColumnFunctionalTable
import chenjox.util.table.templates.ColumnTableTemplate
import chenjox.util.table.templates.DoubleColumnTable
import chenjox.util.table.transformer.mono.*
import chenjox.util.table.transformer.mono.ascii.AsciiMonoTransformer
import kotlin.math.roundToInt

fun main(){

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

fun dsl() {


}