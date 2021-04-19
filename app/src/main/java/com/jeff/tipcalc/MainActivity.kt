package com.jeff.tipcalc

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.jeff.tipcalc.databinding.ActivityMainBinding
import java.text.NumberFormat
import kotlin.math.ceil

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

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
        val cost = binding.costOfService.text.toString().toDouble()

        //determine tip percent from radio buttons
        val tipPercent = when (binding.tipOptions.checkedRadioButtonId) {
            R.id.option_twenty_percent -> .2
            R.id.option_eighteen_percent -> .18
            else -> .15
        }
        var tip = cost * tipPercent     //normal tip

        if (binding.roundUpSwitch.isChecked) {
            tip = ceil(tip)
        }

        val formattedTip = NumberFormat.getCurrencyInstance().format(tip)
        binding.tipResult.text = getString(R.string.tip, formattedTip)
        val formattedTotal = NumberFormat.getCurrencyInstance().format(tip+cost)
        binding.totalAmount.text = getString(R.string.total_amount, formattedTotal)

    }
}