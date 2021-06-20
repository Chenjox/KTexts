package chenjox.util.table

/**
 * An Exception that occurs when anything goes wrong. Will be used for Table specific things, such as storing Illegal values or retrieving them.
 *
 */
class TableException(message: String) : Throwable(message) {
    companion object {
        const val CAUSE_RETRIEVE : String = "Trying to retrieve a value that does not match the type!"
        const val CAUSE_ARGUMENT : String = "Illegal Arguments!"
        const val CAUSE_STORE : String = "Trying to store an illegal argument in a table!"
    }
}