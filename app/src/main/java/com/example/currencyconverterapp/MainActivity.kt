package com.example.currencyconverterapp

import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.Toast
import android.widget.Toolbar
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText

class MainActivity : AppCompatActivity() {
    val TAG = "MainActivity"
    private val egyptianPound = "Egyptian Pound"
    private val americanDollar = "American Dollar"
    private val AED = "AED"
    private val GBP = "GBP"

    val values = mapOf(
        americanDollar to 1.0,
        egyptianPound to 15.73,
        AED to 3.67,
        GBP to 0.74
    )
    private lateinit var toDropDownMenu : AutoCompleteTextView
    private lateinit var fromDropDownMenu : AutoCompleteTextView
    private lateinit var convertButton : Button
    private lateinit var amountEt : TextInputEditText
    private lateinit var resultEt : TextInputEditText
    private lateinit var toolbar : Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initializeViews()

        populateDropDownMenu()

        toolbar.inflateMenu(R.menu.options_menu)
        toolbar.setOnMenuItemClickListener {
            menuItem ->
            if (menuItem.itemId == R.id.share){

                val message = "${amountEt.text.toString()} ${fromDropDownMenu.text.toString()} is equal to ${resultEt.text.toString()} ${toDropDownMenu.text.toString()}"

                val shareIntent = Intent(Intent.ACTION_SEND)
                shareIntent.type = "text/plain"
                shareIntent.putExtra(Intent.EXTRA_TEXT , message)
                if(shareIntent.resolveActivity(packageManager) != null){
                    startActivity(shareIntent)
                }else{
                    Toast.makeText(this,"No Activity found to handle this intent", Toast.LENGTH_SHORT).show()
                }
            }
            true
        }



        convertButton.setOnClickListener {
            calculateResult()
        }

    }

    private fun calculateResult(){
        if(amountEt.text.toString().isNotEmpty()){
            val amount = amountEt.text.toString().toDouble()
            Log.d(TAG, "from ${fromDropDownMenu.text.toString()}")
            Log.d(TAG, "to ${toDropDownMenu.text.toString()}")
            val toValue = values[toDropDownMenu.text.toString()]
            val fromValue = values[fromDropDownMenu.text.toString()]
            val result = amount.times(toValue!!.div(fromValue!!))
            val formattedResult = String.format("%.4f",result)
            resultEt.setText(result.toString())
        }else{
//                val snackbar = Snackbar.make(resultEt,"amount field required",Snackbar.LENGTH_SHORT)
//                snackbar.setAction("OK"){
//                    amountEt.requestFocus()
//                }
//                snackbar.show()

            amountEt.error = "amount field required"
        }
    }

    private fun populateDropDownMenu(){
        val listOfCurrency = listOf(egyptianPound,americanDollar,AED,GBP)
        val adapter = ArrayAdapter(this, R.layout.drop_down_list_item,listOfCurrency)
        toDropDownMenu.setAdapter(adapter)
        fromDropDownMenu.setAdapter(adapter)
    }

    private fun initializeViews(){
        convertButton= findViewById(R.id.convert_button)
        amountEt= findViewById(R.id.amount)
        resultEt = findViewById(R.id.result)
        toDropDownMenu = findViewById(R.id.toDropDownMenu)
        fromDropDownMenu = findViewById(R.id.fromDropDownMenu)
        toolbar = findViewById(R.id.toolbar)
    }

//    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
////        val menuInflater : MenuInflater = getMenuInflater()
//        menuInflater.inflate(R.menu.options_menu,menu)
//        return true
//    }
//
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        when (item.itemId) {
//            R.id.share -> {
//
//            }
//            R.id.setting -> {
//
//            }
//            R.id.contact -> {
//
//            }
//        }
//        return true
//    }
}