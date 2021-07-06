package chenjox.util.table.mono


class T_ArrayMonoTableTest<Int>: TI_MonoTable<kotlin.Int> {
    override fun createSupplier(): TI_MonoTable_InstanceSupplier<kotlin.Int> = object : TI_MonoTable_InstanceSupplier<kotlin.Int> {
        override fun getColumns(): kotlin.Int {
            return 3
        }

        override fun getRows(): kotlin.Int {
            return 4
        }

        override fun getInstance(): MonoTable<kotlin.Int> = ArrayMonoTable(3, 4) {
                column: kotlin.Int, row: kotlin.Int ->
                return@ArrayMonoTable column + row
            }


        override fun getCorrect(col: kotlin.Int, row: kotlin.Int): kotlin.Int {
            return col + row
        }

        override fun getCorrectColumn(col: kotlin.Int): List<kotlin.Int> {
            return List(4) { it + col}
        }

        override fun getCorrectRow(row: kotlin.Int): List<kotlin.Int> {
            return List(3) { it + row }
        }
    }
}