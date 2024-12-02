package com.test.profile

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.test.profile.databinding.ActivityEditBinding

class EditActivity : AppCompatActivity() {

    private  lateinit var binding: ActivityEditBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.etName.setText(intent.extras?.getString(getString(R.string.key_name)).toString())
        binding.etEmail.setText(intent.extras?.getString(getString(R.string.key_email)).toString())
        binding.etWebSite.setText(intent.extras?.getString(getString(R.string.key_website)).toString())
        binding.etPhone.setText(intent.extras?.getString(getString(R.string.key_phone)).toString())
        binding.etLatitud.setText(intent.extras?.getDouble(getString(R.string.key_lat)).toString())
        binding.etLongitud.setText(intent.extras?.getDouble(getString(R.string.key_long)).toString())

        }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_edit,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_save){
            sendData()
        }else if (item.itemId == android.R.id.home){
            onBackPressedDispatcher.onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    fun sendData(){
        val intent = Intent()
        intent.putExtra(getString(R.string.key_name), binding.etName.text.toString())
        intent.putExtra(getString(R.string.key_email), binding.etEmail.text.toString())
        intent.putExtra(getString(R.string.key_website), binding.etWebSite.text.toString())
        intent.putExtra(getString(R.string.key_phone), binding.etPhone.text.toString())
        intent.putExtra(getString(R.string.key_lat), binding.etLatitud.text.toString().toDouble())
        intent.putExtra(getString(R.string.key_long), binding.etLongitud.text.toString().toDouble())

        setResult(RESULT_OK, intent)

        finish()
    }

}