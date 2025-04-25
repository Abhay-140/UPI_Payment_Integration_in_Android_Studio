package com.upipaymentintegration

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import dev.shreyaspatil.easyupipayment.EasyUpiPayment
import dev.shreyaspatil.easyupipayment.listener.PaymentStatusListener
import dev.shreyaspatil.easyupipayment.model.TransactionDetails
import dev.shreyaspatil.easyupipayment.model.TransactionStatus
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


class MainActivity : AppCompatActivity(), PaymentStatusListener {

    private lateinit var amountEdt: EditText
    private lateinit var descEdt: EditText
    private lateinit var payButton: Button
    private lateinit var anotherMethodButton: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        amountEdt = findViewById(R.id.editTextNumber)
        descEdt = findViewById(R.id.editText2)
        payButton = findViewById(R.id.button)
        anotherMethodButton = findViewById(R.id.anotherMethod)

        payButton.setOnClickListener {
            if (TextUtils.isEmpty(amountEdt.text.toString())) {
                toast("Please enter amount....")
            } else {
                upiPayment(amountEdt.text.toString(), descEdt.text.toString())
            }
        }

        anotherMethodButton.setOnClickListener {
            val intent = Intent(this, AnotherUPIPaymentActivity::class.java)
            startActivity(intent)
        }

    }

    private fun upiPayment(amount:String, description:String) {
        val validAmount = validateAmount(amount)

        if(validAmount.isEmpty()){
            toast("Please enter valid amount....")
        }
        else{
            val c = Calendar.getInstance().time
            val df = SimpleDateFormat("ddMMyyyyHHmmss", Locale.getDefault())
            val transcId = df.format(c)

            val easyUpiPayment = EasyUpiPayment(this) {
                this.payeeVpa = UPI_ID
                this.payeeName = Name
                this.payeeMerchantCode = Merchant_Code
                this.transactionId = transcId
                this.transactionRefId = transcId
                this.description = if(description.isEmpty()) "it is okay" else description
                this.amount = validAmount
            }

            easyUpiPayment.setPaymentStatusListener(this)
            easyUpiPayment.startPayment()

        }
    }

    override fun onTransactionCompleted(transactionDetails: TransactionDetails) {

        when (transactionDetails.transactionStatus) {
            TransactionStatus.SUCCESS -> onTransactionSuccess()
            TransactionStatus.FAILURE -> onTransactionFailed()
            TransactionStatus.SUBMITTED -> onTransactionSubmitted()
        }
    }

    override fun onTransactionCancelled() {
        // Payment Cancelled by User
        toast("Payment is Cancelled by user")
    }

    private fun onTransactionSuccess() {
        // Payment Success
        toast("Payment is Successful")
    }

    private fun onTransactionSubmitted() {
        // Payment Pending
        toast("Payment is Pending")
    }

    private fun onTransactionFailed() {
        // Payment Failed
        toast("Payment is Failed")
    }

    private fun validateAmount(amount: String): String {
        // Remove leading zeros
        val cleanedNumberStr = amount.trimStart('0')

        // Handle edge case: all zeros like "0000000"
        if (cleanedNumberStr.isEmpty()) return ""
        else {
            val number = cleanedNumberStr.toInt()

            if (number in 1..10000) {
                return "$number.00"
            } else {
                return ""
            }
        }
        return ""
    }

    private fun toast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}