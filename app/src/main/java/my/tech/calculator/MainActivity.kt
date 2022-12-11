package my.tech.calculator

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import my.tech.calculator.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initUI()

        viewModel.result.observe(this) {
            if (it.isNotEmpty()) {
                binding.result.text = "= $it"
            } else {
                binding.result.text = "0"
            }
        }

        viewModel.expression.observe(this) {
            binding.currentExpression.text = it.expression.toString()
        }
    }

    private fun initUI() {
        binding.num0.setOnClickListener {
            viewModel.addValue(ExpressionItem.Value0)
        }
        binding.num1.setOnClickListener {
            viewModel.addValue(ExpressionItem.Value1)
        }
        binding.num2.setOnClickListener {
            viewModel.addValue(ExpressionItem.Value2)
        }
        binding.num3.setOnClickListener {
            viewModel.addValue(ExpressionItem.Value3)
        }
        binding.num4.setOnClickListener {
            viewModel.addValue(ExpressionItem.Value4)
        }
        binding.num5.setOnClickListener {
            viewModel.addValue(ExpressionItem.Value5)
        }
        binding.num6.setOnClickListener {
            viewModel.addValue(ExpressionItem.Value6)
        }
        binding.num7.setOnClickListener {
            viewModel.addValue(ExpressionItem.Value7)
        }
        binding.num8.setOnClickListener {
            viewModel.addValue(ExpressionItem.Value8)
        }
        binding.num9.setOnClickListener {
            viewModel.addValue(ExpressionItem.Value9)
        }

        binding.opEquality.setOnClickListener {
            viewModel.equality()
        }
        binding.clearField.setOnClickListener {
            viewModel.clearExpression()
        }
        binding.deleteSymbol.setOnClickListener {
            viewModel.removeSymbol()
        }
        binding.addPoint.setOnClickListener {
            viewModel.addValue(ExpressionItem.ValuePoint)
        }
        binding.addLeftBracket.setOnClickListener {
            viewModel.addBracket(ExpressionItem.LeftBracket)
        }
        binding.addRightBracket.setOnClickListener {
            viewModel.addBracket(ExpressionItem.RightBracket)
        }

        binding.opPlus.setOnClickListener {
            viewModel.addOperation(ExpressionItem.OperationPlus)
        }
        binding.opMinus.setOnClickListener {
            viewModel.addOperation(ExpressionItem.OperationMinus)
        }
        binding.opMul.setOnClickListener {
            viewModel.addOperation(ExpressionItem.OperationMul)
        }
        binding.opDiv.setOnClickListener {
            viewModel.addOperation(ExpressionItem.OperationDiv)
        }
        binding.opSqrt.setOnClickListener {
            viewModel.addOperation(ExpressionItem.OperationSqrt)
        }
        binding.opSqr.setOnClickListener {
            viewModel.addOperation(ExpressionItem.OperationSqr)
        }
        binding.opPersent.setOnClickListener {
            viewModel.addOperation(ExpressionItem.OperationPercent)
        }

        binding.switchTheme.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                delegate.applyDayNight()
                binding.themeIcon.setImageResource(R.drawable.ic_day)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                delegate.applyDayNight()
                binding.themeIcon.setImageResource(R.drawable.ic_moon)
            }
        }

        binding.result.text = "0"
    }


}