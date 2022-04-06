package com.mistershorr.birthdaytracker

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.backendless.Backendless
import com.backendless.BackendlessUser
import com.backendless.async.callback.AsyncCallback
import com.backendless.exceptions.BackendlessFault
import com.mistershorr.birthdaytracker.databinding.ActivityRegistrationBinding
import java.util.*

class RegistrationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegistrationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistrationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Backendless.initApp( this, Constants.APP_ID, Constants.API_KEY)

        // access any values that were sent to us from the intent that launched this activity
        val username = intent.getStringExtra(LoginActivity.EXTRA_USERNAME)
        val password = intent.getStringExtra(LoginActivity.EXTRA_PASSWORD)
        Toast.makeText(this, "user:$username pwd $password", Toast.LENGTH_SHORT).show()


        binding.buttonRegistrationRegister.setOnClickListener {

            if(!RegistrationUtil.validateName(
                    binding.editTextRegistrationName.text.toString())) {
                Toast.makeText(this, "Invalid name", Toast.LENGTH_SHORT).show()
            }

            else {

                registerUser()

            }
        }
    }

    private fun registerUser() {
        val user = BackendlessUser()
        user.setProperty("email", "${binding.editTextRegistrationEmail.text.toString()}")
        user.password = "${binding.editTextRegistrationPassword.text.toString()}"
        user.setProperty("username", "${binding.editTextRegistrationUsername.text.toString()}")
        user.setProperty("name", "${binding.editTextRegistrationName.text.toString()}")

        Backendless.UserService.register(user, object : AsyncCallback<BackendlessUser?> {
            override fun handleResponse(registeredUser: BackendlessUser?) {
                Toast.makeText(this@RegistrationActivity, "registration successful", Toast.LENGTH_SHORT).show()
                returnToLogin(binding.editTextRegistrationUsername.text.toString(), binding.editTextRegistrationPassword.text.toString())
            }

            override fun handleFault(fault: BackendlessFault) {
                // an error has occurred, the error code can be retrieved with fault.getCode()
                Toast.makeText(this@RegistrationActivity, "${fault.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun returnToLogin(username: String?, password: String?) {
        var returnToLoginIntent = Intent().apply {
            putExtra(
                LoginActivity.EXTRA_USERNAME,
                binding.editTextRegistrationUsername.text.toString()
            )
            putExtra(
                LoginActivity.EXTRA_PASSWORD,
                binding.editTextRegistrationPassword.text.toString()
            )
        }
        setResult(Activity.RESULT_OK, returnToLoginIntent)
        finish()
    }
}










