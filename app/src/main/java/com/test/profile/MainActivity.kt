package com.test.profile

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.test.profile.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private var lat: Double = 0.0
    private var long: Double =  0.0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        updateUI()

        binding.tvlocation.setOnClickListener{
            binding.tvlocation.text = "Lat: $lat, Long: $long"
        }

    }

    private fun updateUI(name: String = "Curso Android ANT",
                        email: String = "curso@gmail.com",
                        website: String = "https://www.cursoandroidant.com",
                        phone: String = "+56789987979") {
        binding.tvName.text = name
        binding.tvEmail.text = email
        binding.tvWebSite.text = website
        binding.tvPhone.text = phone
        /*lat = 37.398901
        long = -122.104068*/

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_edit){
            val intent = Intent(this, EditActivity::class.java)
            intent.putExtra(getString(R.string.key_name), binding.tvName.text.toString())
            intent.putExtra(getString(R.string.key_email), binding.tvEmail.text.toString())
            intent.putExtra(getString(R.string.key_website), binding.tvWebSite.text.toString())
            intent.putExtra(getString(R.string.key_phone), binding.tvPhone.text.toString())
            intent.putExtra(getString(R.string.key_lat), lat)
            intent.putExtra(getString(R.string.key_long), long)
            //startActivity(intent)
            //startActivityForResult(intent,RC_EDIT)
            editResult.launch(intent)

        }
        return super.onOptionsItemSelected(item)
    }

    private val  editResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        if(it.resultCode == RESULT_OK){
            val name = it.data?.getStringExtra(getString(R.string.key_name))
            val email = it.data?.getStringExtra(getString(R.string.key_email))
            val website = it.data?.getStringExtra(getString(R.string.key_website))
            val phone = it.data?.getStringExtra(getString(R.string.key_phone))
            lat = it.data?.getDoubleExtra(getString(R.string.key_lat), 0.0) ?: 0.0
            long = it.data?.getDoubleExtra(getString(R.string.key_long), 0.0) ?:0.0

            updateUI(name!!,email!!,website!!,phone!!)
        }

    }
    /*override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
            onActivityResult(requestCode, resultCode, data)

        if(resultCode == RESULT_OK){
            if (requestCode == RC_EDIT){
                val name = data?.getStringExtra(getString(R.string.key_name))
                val email = data?.getStringExtra(getString(R.string.key_email))
                val website = data?.getStringExtra(getString(R.string.key_website))
                val phone = data?.getStringExtra(getString(R.string.key_phone))
                lat = data?.getDoubleExtra(getString(R.string.key_lat), 0.0) ?: 0.0
                long = data?.getDoubleExtra(getString(R.string.key_long), 0.0) ?:0.0

                updateUI(name!!,email!!,website!!,phone!!)
            }
        }
    }*/

    /*companion object {
        private const val RC_EDIT = 21
    }*/
}