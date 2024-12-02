package com.test.profile


import android.app.SearchManager
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.net.Uri.*
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import androidx.preference.PreferenceManager
import com.test.profile.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private  lateinit var  sharedPreferences: SharedPreferences

    private var lat: Double = 0.0
    private var long: Double =  0.0
    private lateinit var imgUri: Uri


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        //updateUI()
        getUserData()
        setUpIntents()

       /* binding.tvlocation.setOnClickListener{
            binding.tvlocation.text = "Lat: $lat, Long: $long"
        }*/

    }

    private fun getUserData() {
        imgUri = Uri.parse(sharedPreferences.getString(getString(R.string.key_image),""))
        val name = sharedPreferences.getString(getString(R.string.key_name), null)
        val email = sharedPreferences.getString(getString(R.string.key_email), null)
        val website = sharedPreferences.getString(getString(R.string.key_website), null)
        val phone = sharedPreferences.getString(getString(R.string.key_phone), null)
        lat = sharedPreferences.getString(getString(R.string.key_lat),"0.0")!!.toDouble()
        long = sharedPreferences.getString(getString(R.string.key_long), "0.0")!!.toDouble()

        updateUI(name, email, website, phone)
    }

    private fun setUpIntents() {
       binding.tvName.setOnClickListener {
           val intent = Intent(Intent.ACTION_WEB_SEARCH).apply {
               putExtra(SearchManager.QUERY, binding.tvName.text)
           }
           launchIntent(intent)
       }
        binding.tvEmail.setOnClickListener{
            val intent = Intent(Intent.ACTION_SENDTO).apply {
                data = parse("mailto")
                putExtra(Intent.EXTRA_EMAIL, arrayOf(binding.tvEmail.text))
                putExtra(Intent.EXTRA_SUBJECT, "Desde curso basico de kotlin")
                putExtra(Intent.EXTRA_TEXT, "Hola desde el desarrollador")
            }
            launchIntent(intent)
        }

        binding.tvWebSite.setOnClickListener{
            val webPage = parse(binding.tvWebSite.text.toString())
            val intent = Intent(Intent.ACTION_VIEW, webPage)
            launchIntent(intent)
        }

        binding.tvPhone.setOnClickListener{
            val intent = Intent(Intent.ACTION_DIAL).apply {
                val phone = (it as TextView).text
                data = parse("tel:$phone")
            }
            launchIntent(intent)
        }

        binding.tvlocation.setOnClickListener{
            val intent = Intent(Intent.ACTION_VIEW).apply {
                data = parse("geo:0.0?q=$lat,$long(Curso Android ANT)")
                //`Package` = "com.google.android.apps.maps"
            }
            launchIntent(intent)
        }

        binding.tvEnableLocation.setOnClickListener{
            // TODO por hacer
        }

    }

    private fun launchIntent(intent: Intent){
        if(intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        }else{
            Toast.makeText(this, getString(R.string.profile_error_no_resolve), Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateUI(name: String? ,
                        email: String? ,
                        website: String?,
                        phone: String? ) {
        with(binding) {
            imgProfileac.setImageURI(imgUri)
            tvName.text = name ?: "Curso Android ANT"
            tvEmail.text = email ?: "curso@gmail.com"
            tvWebSite.text = website ?: "https://www.cursoandroidant.com"
            tvPhone.text = phone ?: "+56789987979"
        }
        /*lat = 37.398901
        long = -122.104068*/

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.action_edit->{
                    val intent = Intent(this, EditActivity::class.java)
                    intent.putExtra(getString(R.string.key_image),imgUri.toString())
                    intent.putExtra(getString(R.string.key_name), binding.tvName.text.toString())
                    intent.putExtra(getString(R.string.key_email), binding.tvEmail.text.toString())
                    intent.putExtra(getString(R.string.key_website), binding.tvWebSite.text.toString())
                    intent.putExtra(getString(R.string.key_phone), binding.tvPhone.text.toString())
                    intent.putExtra(getString(R.string.key_lat), lat)
                    intent.putExtra(getString(R.string.key_long), long)

                    editResult.launch(intent)
                }
            R.id.action_settings->startActivity(Intent(this, SettingsActivity::class.java))
        }

        return super.onOptionsItemSelected(item)
    }

    private val  editResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        if(it.resultCode == RESULT_OK){
            imgUri = Uri.parse(it.data?.getStringExtra(getString(R.string.key_image)))
            val name = it.data?.getStringExtra(getString(R.string.key_name))
            val email = it.data?.getStringExtra(getString(R.string.key_email))
            val website = it.data?.getStringExtra(getString(R.string.key_website))
            val phone = it.data?.getStringExtra(getString(R.string.key_phone))
            lat = it.data?.getDoubleExtra(getString(R.string.key_lat), 0.0) ?: 0.0
            long = it.data?.getDoubleExtra(getString(R.string.key_long), 0.0) ?:0.0

            //updateUI(name!!,email!!,website!!,phone!!)
            saveUserData(name, email, website, phone)
        }

    }

    private fun saveUserData (name: String?, email: String?, website: String?, phone: String?) {
        sharedPreferences.edit{
            putString(getString(R.string.key_image), imgUri.toString())
            putString(getString(R.string.key_email),email)
            putString(getString(R.string.key_website),website)
            putString(getString(R.string.key_phone),phone)
            putString(getString(R.string.key_lat), lat.toString())
            putString(getString(R.string.key_long), long.toString())
            apply()
        }
        updateUI(name,email,website,phone)
    }

}
