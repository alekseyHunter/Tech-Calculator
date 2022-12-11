package my.tech.calculator.models

data class ExpressionData(
    val expression: StringBuilder = StringBuilder(),
    val privateExpression: StringBuilder = StringBuilder(),
    val currentExpressionItem: ExpressionItem = ExpressionItem.Empty
)
