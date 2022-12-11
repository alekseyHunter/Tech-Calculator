package my.tech.calculator

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import my.tech.calculator.models.ExpressionData
import my.tech.calculator.models.ExpressionItem
import my.tech.calculator.models.ExpressionType
import my.tech.calculator.models.ThemeMode
import my.tech.calculator.utils.MathModule
import my.tech.calculator.utils.PreferencesManager
import java.text.ParseException
import kotlin.math.roundToLong

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val context = getApplication<Application>().applicationContext
    private val preferencesManager: PreferencesManager = PreferencesManager(context)

    private val _result: MutableLiveData<String> = MutableLiveData("")
    val result: LiveData<String>
        get() = _result

    private val _expression: MutableLiveData<ExpressionData> = MutableLiveData(ExpressionData())
    val expression: LiveData<ExpressionData>
        get() = _expression

    private val _themeMode: MutableLiveData<ThemeMode> = MutableLiveData()
    val themeMode: LiveData<ThemeMode>
        get() = _themeMode

    init {
        val currentTheme = preferencesManager.getCurrentTheme()
        _themeMode.postValue(currentTheme)
    }

    fun updateThemeMode(themeMode: ThemeMode) {
        preferencesManager.setCurrentTheme(themeMode)
        _themeMode.postValue(themeMode)
    }

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

            val result = calculateResult(privateExpression.toString())
            _result.postValue(result)
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
            val result = calculateResult(privateExpression.toString())
            _result.postValue(result)
        }
    }

    fun addOperation(item: ExpressionItem) {
        _expression.value?.let {
            val expression = it.expression
            val privateExpression = it.privateExpression

            if (it.currentExpressionItem.type == ExpressionType.Operation) {
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
                val result = calculateResult(privateExpression.toString())
                _result.postValue(result)
            }
        }
    }

    fun equality() {
        _expression.value?.let {
            val expression = it.expression
            val privateExpression = it.privateExpression
            val result = calculateResult(privateExpression.toString())
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

    private fun calculateResult(expression: String): String {
        return try {
            val mathModule = MathModule(expression)
            val result = mathModule.calculate()
            convertResultToString(result)
        }
        catch (exception: ParseException) {
            ""
        }
        catch (exception: Exception) {
            exception.localizedMessage ?: "Unknown error"
        }
    }

    private fun convertResultToString(result: Double): String {
        return if ((result - result.toLong()) != 0.0) {
            "${String.format("%.2f", result)}"
        } else {
            "${result.roundToLong()}"
        }
    }
}