package com.test.profile

import android.R.attr.data
import android.annotation.SuppressLint
import android.app.ComponentCaller
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.test.profile.databinding.ActivityEditBinding

class EditActivity : AppCompatActivity() {
    private  var imgUri: Uri? = null

    private  val galleryResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        if (it.resultCode==RESULT_OK){
            imgUri = it.data?.data

            imgUri?.let{
                val contentResolver = applicationContext.contentResolver
                val takeFlags = Intent.FLAG_GRANT_READ_URI_PERMISSION or
                        Intent.FLAG_GRANT_WRITE_URI_PERMISSION
                it.let { uri -> contentResolver.takePersistableUriPermission( uri, takeFlags) } }
            binding.imgProfile.setImageURI(imgUri)
        }
    }

    private  lateinit var binding: ActivityEditBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        with(binding){
        intent.extras?.let{
                imgUri = Uri.parse(it.getString(getString(R.string.key_image)))
                    imgProfile.setImageURI(imgUri)
                etName.setText(it.getString(getString(R.string.key_name)).toString())
                etEmail.setText(it.getString(getString(R.string.key_email)).toString())
                etWebSite.setText(it.getString(getString(R.string.key_website)).toString())
                etPhone.setText(it.getString(getString(R.string.key_phone)).toString())
                etLatitud.setText(it.getDouble(getString(R.string.key_lat)).toString())
                etLongitud.setText(it.getDouble(getString(R.string.key_long)).toString())
            }

            btnSelectPhoto.setOnClickListener{
                val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
                    addCategory(Intent.CATEGORY_OPENABLE)
                    type = "image/jpeg"
                }
                galleryResult.launch(intent)
            }

        }

    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_edit,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> onBackPressedDispatcher.onBackPressed()
            R.id.action_save -> sendData()
        }
//        if (item.itemId == R.id.action_save){
//            sendData()
//        }else if (item.itemId == android.R.id.home){
//            onBackPressedDispatcher.onBackPressed()
//        }
        return super.onOptionsItemSelected(item)
    }

    fun sendData(){
        val intent = Intent()

        with(binding){
            intent.apply {
                putExtra(getString(R.string.key_image), imgUri.toString())
                putExtra(getString(R.string.key_name), etName.text.toString())
                putExtra(getString(R.string.key_email),etEmail.text.toString())
                putExtra(getString(R.string.key_website), etWebSite.text.toString())
                putExtra(getString(R.string.key_phone), etPhone.text.toString())
                putExtra(getString(R.string.key_lat), etLatitud.text.toString().toDouble())
                putExtra(getString(R.string.key_long), etLongitud.text.toString().toDouble())
            }
        }

        setResult(RESULT_OK, intent)

        finish()
    }
    companion object {
        private const val RC_GALLERY = 22
    }

}


