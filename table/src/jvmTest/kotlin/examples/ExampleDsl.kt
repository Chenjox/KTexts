package examples

import chenjox.util.table.mono.left
import chenjox.util.table.mono.right
import chenjox.util.texts.table.dsl.mono.Mono

fun main() {
    val t = Mono(0.0) {
        cell(2, 3) {
            withFunctional {
                left()+right()
            }
        }
    }
}