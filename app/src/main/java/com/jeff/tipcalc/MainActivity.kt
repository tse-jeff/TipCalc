package com.jeff.tipcalc

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.jeff.tipcalc.databinding.ActivityMainBinding
import java.text.NumberFormat
import kotlin.math.ceil

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.calculateButton.setOnClickListener { calculateTip() }
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

        //round up function from switch on
        if (binding.roundUpSwitch.isChecked) {
            tip = ceil(tip)
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
}