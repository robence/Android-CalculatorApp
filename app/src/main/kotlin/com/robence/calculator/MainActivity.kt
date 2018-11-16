package com.robence.calculator

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    private var result: EditText? = null
    private var newNumber: EditText? = null
    private var displayOperation: TextView? = null

    private var operand1: Double = 0.0
    private var pendingOperation: String? = "="

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        result = findViewById<View>(R.id.result) as EditText
        newNumber = findViewById<View>(R.id.newNumber) as EditText
        displayOperation = findViewById<View>(R.id.operation) as TextView

        val button0 = findViewById<View>(R.id.button0) as Button
        val button1 = findViewById<View>(R.id.button1) as Button
        val button2 = findViewById<View>(R.id.button2) as Button
        val button3 = findViewById<View>(R.id.button3) as Button
        val button4 = findViewById<View>(R.id.button4) as Button
        val button5 = findViewById<View>(R.id.button5) as Button
        val button6 = findViewById<View>(R.id.button6) as Button
        val button7 = findViewById<View>(R.id.button7) as Button
        val button8 = findViewById<View>(R.id.button8) as Button
        val button9 = findViewById<View>(R.id.button9) as Button
        val buttonDot = findViewById<View>(R.id.buttonDot) as Button

        val buttonEquals = findViewById<View>(R.id.buttonEquals) as Button
        val buttonDivide = findViewById<View>(R.id.buttonDivide) as Button
        val buttonMultiply = findViewById<View>(R.id.buttonMultiply) as Button
        val buttonMinus = findViewById<View>(R.id.buttonMinus) as Button
        val buttonPlus = findViewById<View>(R.id.buttonPlus) as Button

        val listener = View.OnClickListener { view ->
            val b = view as Button
            newNumber!!.append(b.text.toString())
        }
        button0.setOnClickListener(listener)
        button1.setOnClickListener(listener)
        button2.setOnClickListener(listener)
        button3.setOnClickListener(listener)
        button4.setOnClickListener(listener)
        button5.setOnClickListener(listener)
        button6.setOnClickListener(listener)
        button7.setOnClickListener(listener)
        button8.setOnClickListener(listener)
        button9.setOnClickListener(listener)
        buttonDot.setOnClickListener(listener)

        val opListener = View.OnClickListener { view ->
            val button = view as Button
            val operation = button.text.toString()
            val value = newNumber!!.text.toString()
            try {
                val doubleValue = java.lang.Double.valueOf(value)
                performOperation(doubleValue, operation)
            } catch (e: NumberFormatException) {
                newNumber!!.setText("")
            }

            pendingOperation = operation
            displayOperation!!.text = pendingOperation
        }

        buttonEquals.setOnClickListener(opListener)
        buttonDivide.setOnClickListener(opListener)
        buttonMultiply.setOnClickListener(opListener)
        buttonMinus.setOnClickListener(opListener)
        buttonPlus.setOnClickListener(opListener)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString(STATE_PENDING_OPERATION, pendingOperation)
        if (operand1 != null) {
            outState.putDouble(STATE_OPERAND1, operand1!!)
        }
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        pendingOperation = savedInstanceState.getString(STATE_PENDING_OPERATION)
        operand1 = savedInstanceState.getDouble(STATE_OPERAND1)
        displayOperation!!.text = pendingOperation
    }

    private fun performOperation(value: Double, operation: String) {
//        if (null == operand1) {
//            operand1 = value
//        } else {
            if (pendingOperation == "=") {
                pendingOperation = operation
            }
            when (pendingOperation) {
                "=" -> operand1 = value
                "/" -> if (value == 0.0) {
                        operand1 = 0.0
                    } else {
                    operand1 /= value
                    }
                "*" -> value?.let{operand1 = operand1 / value}
                "-" -> value?.let{operand1 = operand1 - value}
                "+" -> value?.let{operand1 = operand1 + value}
            }
//        }

        result!!.setText(operand1!!.toString())
        newNumber!!.setText("")
    }

    companion object {

        private const val STATE_PENDING_OPERATION = "PendingOperation"
        private const val STATE_OPERAND1 = "Operand1"
    }
}