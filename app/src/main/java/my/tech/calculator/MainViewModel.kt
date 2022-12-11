package my.tech.calculator

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {

    private val _result: MutableLiveData<String> = MutableLiveData("")
    val result: LiveData<String>
        get() = _result

    private val _expression: MutableLiveData<ExpressionData> = MutableLiveData(ExpressionData())
    val expression: LiveData<ExpressionData>
        get() = _expression

    fun addValue(item: ExpressionItem) {
        _expression.value?.let {
            val expression = it.expression
            val privateExpression = it.privateExpression
            expression.append(item.value)
            privateExpression.append(item.value)
            _expression.postValue(
                ExpressionData(
                    expression, privateExpression, item
                )
            )

            _result.postValue(privateExpression.toString())
        }
    }

    fun addBracket(item: ExpressionItem) {
        _expression.value?.let {
            val expression = it.expression
            val privateExpression = it.privateExpression
            expression.append(item.value)
            privateExpression.append(item.value)
            _expression.postValue(
                ExpressionData(
                    expression, privateExpression, item
                )
            )

            _result.postValue(privateExpression.toString())
        }
    }

    fun addOperation(item: ExpressionItem) {
        _expression.value?.let {
            val expression = it.expression
            val privateExpression = it.privateExpression

            if(it.currentExpressionItem.type == ExpressionType.Operation){
                expression.deleteCharAt(expression.lastIndex)
                privateExpression.deleteCharAt(privateExpression.lastIndex)
            }

            expression.append(item.value)
            privateExpression.append(item.value)
            _expression.postValue(
                ExpressionData(
                    expression, privateExpression, item
                )
            )

            _result.postValue(privateExpression.toString())
        }
    }

    fun clearExpression() {
        _expression.postValue(ExpressionData())
        _result.postValue("")
    }

    fun removeSymbol() {
        _expression.value?.let {
            if (it.expression.isNotEmpty()) {
                val expression = it.expression
                val privateExpression = it.privateExpression
                expression.deleteCharAt(expression.lastIndex)
                privateExpression.deleteCharAt(privateExpression.lastIndex)
                val expressionItem =
                    if (expression.isEmpty()) ExpressionItem.Empty else ExpressionItem.convertToExpression(
                        expression.last().toString()
                    )
                _expression.postValue(
                    ExpressionData(
                        expression,
                        privateExpression,
                        expressionItem
                    )
                )

                _result.postValue(privateExpression.toString())
            }
        }
    }

    fun equality() {
        _expression.value?.let {
            val expression = it.expression
            val privateExpression = it.privateExpression
            val result = privateExpression.toString()
            expression.clear()
            privateExpression.clear()
            privateExpression.append(result)
            expression.append(result)
            _expression.postValue(
                ExpressionData(
                    expression, privateExpression, ExpressionItem.Empty
                )
            )

            _result.postValue(result)
        }
    }
}