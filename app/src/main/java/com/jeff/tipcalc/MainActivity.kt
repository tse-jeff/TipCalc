package com.jeff.tipcalc

import android.content.Context
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import com.jeff.tipcalc.databinding.ActivityMainBinding
import java.text.NumberFormat

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.calculateButton.setOnClickListener { calculateTip() }

        setContentView(binding.root)

        binding.optionEighteenPercent.setOnClickListener { calculateTip() }
        binding.optionFifteenPercent.setOnClickListener { calculateTip() }
        binding.optionTwentyPercent.setOnClickListener { calculateTip() }


        binding.calculateButton.setOnClickListener { calculateTip() }

        binding.roundUpSwitch.setOnClickListener { calculateTip() }

        binding.costOfServiceEditText.setOnKeyListener { view, keyCode, _ -> handleKeyEvent(view, keyCode)
        }
    }

    /*
    Calculates the tip based on the service cost
     */
    private fun calculateTip() {
        val cost = binding.costOfServiceEditText.text.toString().toDoubleOrNull()

        if (cost == null) {
            displayTip(0.0)
            displayTot(0.0)
            return
        }

        //determine tip percent from radio buttons
        val tipPercent = when (binding.tipOptions.checkedRadioButtonId) {
            R.id.option_twenty_percent -> .2
            R.id.option_eighteen_percent -> .18
            else -> .15
        }
        var tip = cost * tipPercent     //normal tip
        var total = cost + tip

        //round up function from switch on
        if (binding.roundUpSwitch.isChecked) {
//            tip = ceil(tip)
            total = kotlin.math.ceil(total)
            tip = total - cost

        }

        //display function calls
        displayTip(tip)
        displayTot(tip + cost)
    }

    /*
    This function takes in the amount of tip and puts it in the TextView text
     */
    private fun displayTip(tip: Double) {
        val formattedAmt = NumberFormat.getCurrencyInstance().format(tip)
        binding.tipResult.text = getString(R.string.tip, formattedAmt)
    }

    /*
    This function takes in the amount of tip + cost or total and puts it in the TextView text
     */
    private fun displayTot(total: Double) {
        val formattedTotal = NumberFormat.getCurrencyInstance().format(total)
        binding.totalAmount.text = getString(R.string.total_amount, formattedTotal)
    }

    private fun handleKeyEvent(view: View, keyCode: Int): Boolean {
        if (keyCode == KeyEvent.KEYCODE_ENTER) {
            // Hide the keyboard
            val inputMethodManager =
                getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
            return true
        }
        return false
    }
}