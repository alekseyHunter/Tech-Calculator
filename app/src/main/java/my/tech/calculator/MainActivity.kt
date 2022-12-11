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
            if(it.isNotEmpty()) {
                binding.result.text = "= $it"
            } else {
                binding.result.text = "0"
            }
        }
    }

    private fun initUI() {
        binding.num0.setOnClickListener {
            viewModel.addValue("0")
        }
        binding.num1.setOnClickListener {
            viewModel.addValue("1")
        }
        binding.num2.setOnClickListener {
            viewModel.addValue("2")
        }
        binding.num3.setOnClickListener {
            viewModel.addValue("3")
        }
        binding.num4.setOnClickListener {
            viewModel.addValue("4")
        }
        binding.num5.setOnClickListener {
            viewModel.addValue("5")
        }
        binding.num6.setOnClickListener {
            viewModel.addValue("6")
        }
        binding.num7.setOnClickListener {
            viewModel.addValue("7")
        }
        binding.num8.setOnClickListener {
            viewModel.addValue("8")
        }
        binding.num9.setOnClickListener {
            viewModel.addValue("9")
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
            viewModel.addValue(".")
        }
        binding.addLeftBracket.setOnClickListener {
            viewModel.addBracket("(")
        }
        binding.addRightBracket.setOnClickListener {
            viewModel.addBracket(")")
        }

        binding.opPlus.setOnClickListener {
            viewModel.addOperation("+")
        }
        binding.opMinus.setOnClickListener {
            viewModel.addOperation("-")
        }
        binding.opMul.setOnClickListener {
            viewModel.addOperation("*")
        }
        binding.opDiv.setOnClickListener {
            viewModel.addOperation("/")
        }
        binding.opSqrt.setOnClickListener {
            viewModel.addOperation("√")
        }
        binding.opSqr.setOnClickListener {
            viewModel.addOperation("^")
        }
        binding.opPersent.setOnClickListener {
            viewModel.addOperation("%")
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