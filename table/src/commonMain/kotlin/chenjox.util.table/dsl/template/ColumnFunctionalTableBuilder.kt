package chenjox.util.table.dsl.template

import chenjox.util.table.dsl.TableBuilder
import chenjox.util.table.dsl.TableDsl
import chenjox.util.table.mono.MonoTable
import chenjox.util.table.mono.MonoTableAccessor
import chenjox.util.table.mono.MonoTableRelativeAccessor
import chenjox.util.table.templates.ColumnTableTemplate
import chenjox.util.table.transformer.mono.MonoTableTransformer
import chenjox.util.table.transformer.mono.PaddingTransformer
import chenjox.util.table.transformer.mono.SimpleTransformer
import chenjox.util.table.transformer.mono.ascii.AsciiMonoTransformer
import chenjox.util.table.transformer.mono.then
import kotlin.math.pow
import kotlin.math.roundToInt

fun <E> ColumnTable(init: ColumnTableBuilder<E>.() -> Unit){
    TODO()
}

@TableDsl
interface ColumnTableBuilder<E> : TableBuilder<ColumnTableTemplate<E>>{

    fun column(header: String? = null, index: Int? = null) : ColumnSpec<E>
    fun column(index: Int? = null) : ColumnSpec<E>

    fun outputConfig() : outputConfigSpec<E>

}

@TableDsl
interface ColumnSpec<E> {
    infix fun withFunction(func: MonoTableRelativeAccessor<E>.() -> E)
    infix fun withAccessor(func: MonoTableAccessor<E>.(curCol: Int, curRow: Int) -> E)
}

@TableDsl
interface outputConfigSpec<E> {
    fun <T : PresetBuilder<E>> preset(pr: PresetBuilder<E>, init: T.() -> Unit)
    fun renderer(init: (RendererSpec<E>) -> FinalRenderer)
}

@TableDsl
interface RendererSpec<E> {
    fun <N> next( stage: MonoTableTransformer<E, MonoTable<N>> ) : RendererSpec<N>
    fun finally( stage: MonoTableTransformer<E, String> ) : FinalRenderer
}
interface FinalRenderer

fun <E,N> RendererSpec<E>.mapper(mapper: (E) -> N) : RendererSpec<N> = next( SimpleTransformer(mapper) )

fun RendererSpec<Double>.decimalPlaces(places: Int) : RendererSpec<String> = next(
    SimpleTransformer<Double, String> { (it * 10.0.pow( places ) ).roundToInt().toString() }
    .then( SimpleTransformer { it.dropLast(places)+"."+it.drop( maxOf(it.length-places, 0 )) } ))

fun RendererSpec<String>.padding(padLeft: Int, padRight: Int) : RendererSpec<String> = next( PaddingTransformer(padLeft, padRight) )
fun RendererSpec<String>.ascii() : FinalRenderer = finally( AsciiMonoTransformer() )

interface PresetBuilder<E> {

    fun getTransformer() : MonoTableTransformer<E, String>

}