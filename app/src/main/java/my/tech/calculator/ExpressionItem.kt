package my.tech.calculator

sealed class ExpressionItem(val type: ExpressionType, val value: String){
    object OperationMul: ExpressionItem(ExpressionType.Operation, "*")
    object OperationDiv: ExpressionItem(ExpressionType.Operation, "/")
    object OperationPlus: ExpressionItem(ExpressionType.Operation, "+")
    object OperationMinus: ExpressionItem(ExpressionType.Operation, "-")
    object OperationSqrt: ExpressionItem(ExpressionType.Operation, "√")
    object OperationSqr: ExpressionItem(ExpressionType.Operation, "^")
    object OperationPercent: ExpressionItem(ExpressionType.Operation, "%")

    object LeftBracket: ExpressionItem(ExpressionType.Bracket, "(")
    object RightBracket: ExpressionItem(ExpressionType.Bracket, ")")

    object Value0: ExpressionItem(ExpressionType.Value, "0")
    object Value1: ExpressionItem(ExpressionType.Value, "1")
    object Value2: ExpressionItem(ExpressionType.Value, "2")
    object Value3: ExpressionItem(ExpressionType.Value, "3")
    object Value4: ExpressionItem(ExpressionType.Value, "4")
    object Value5: ExpressionItem(ExpressionType.Value, "5")
    object Value6: ExpressionItem(ExpressionType.Value, "6")
    object Value7: ExpressionItem(ExpressionType.Value, "7")
    object Value8: ExpressionItem(ExpressionType.Value, "8")
    object Value9: ExpressionItem(ExpressionType.Value, "9")
    object ValuePoint: ExpressionItem(ExpressionType.Value, ".")

    object Empty: ExpressionItem(ExpressionType.Empty, "")

    companion object {
        fun convertToExpression(value: String): ExpressionItem {
            return when(value){
                OperationMul.value -> OperationMul
                OperationDiv.value -> OperationDiv
                OperationPlus.value -> OperationPlus
                OperationMinus.value -> OperationMinus
                OperationSqrt.value -> OperationSqrt
                OperationSqr.value -> OperationSqr
                OperationPercent.value -> OperationPercent
                LeftBracket.value -> LeftBracket
                RightBracket.value -> RightBracket
                Empty.value -> Empty
                Value0.value -> Value0
                Value1.value -> Value1
                Value2.value -> Value2
                Value3.value -> Value3
                Value4.value -> Value4
                Value5.value -> Value5
                Value6.value -> Value6
                Value7.value -> Value7
                Value8.value -> Value8
                Value9.value -> Value9
                ValuePoint.value -> ValuePoint
                else -> throw Exception("Not found ExpressionItem with value")
            }
        }
    }
}

sealed class ExpressionType{
    object Operation: ExpressionType()
    object Bracket: ExpressionType()
    object Value: ExpressionType()
    object Empty: ExpressionType()
}