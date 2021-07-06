package chenjox.util.table

/**
 * An Exception that occurs when anything goes wrong. Will be used for Table specific things, such as storing Illegal values or retrieving them.
 *
 */
public class TableException(message: String) : Throwable(message) {
    public companion object {
        public const val CAUSE_RETRIEVE : String = "Trying to retrieve a value that does not match the type!"
        public const val CAUSE_ARGUMENT : String = "Illegal Arguments!"
        public const val CAUSE_STORE : String = "Trying to store an illegal argument in a table!"
    }
}