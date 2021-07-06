package chenjox.util.table.templates

/** A contract on how _Templates_ should behave. Templates are Special kinds of Tables. therefore they are treated specially. */
public interface TableTemplate{

    public fun render() : String

}