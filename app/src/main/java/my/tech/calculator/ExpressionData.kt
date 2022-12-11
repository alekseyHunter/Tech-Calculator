package my.tech.calculator

data class ExpressionData(
    val expression: StringBuilder = StringBuilder(),
    val privateExpression: StringBuilder = StringBuilder(),
    val currentExpressionItem: ExpressionItem = ExpressionItem.Empty
)
