package com.edu.pedroza.at_notes.ui.login

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.EditText
import com.edu.pedroza.at_notes.R
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_registry.*

class RegistryActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registry)

        auth = Firebase.auth

        buttonRealizarCadastro.setOnClickListener {
            createUser()
        }
    }

    private fun createUser() {
        var nome = editTextTextPersonName.text.toString()
        var email = cadastroEmailTextView.text.toString()
        var password = TextViewPass.text.toString()
        var confirmPassword = TextViewPassTwo.text.toString()


        if (nome.isEmpty()) {
            showErro(editTextTextPersonName,"ERRO:Nome invalido")
        }
        if (email.isEmpty()) {
            showErro(cadastroEmailTextView, "ERRO:Entre com um email válido")
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            showErro(cadastroEmailTextView, "ERRO:Entre com um email válido")
        }

        if (password.isEmpty()) {
            showErro(TextViewPass, "ERRO:Entre com uma senha válida")
        }

        if (confirmPassword.isEmpty()) {
            showErro(TextViewPassTwo, "ERRO:Entre com uma senha válida")
        }

        if (password != confirmPassword) {
            showErro(TextViewPassTwo, "ERRO:Senhas não conferem")
        }

        if (email.isNotEmpty() && confirmPassword.isNotEmpty()) {
            auth.createUserWithEmailAndPassword(
                email,
                confirmPassword
            )
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        val profileUpdates = userProfileChangeRequest {
                            displayName = nome
                        }
                        auth.currentUser!!.updateProfile(profileUpdates)

                        startActivity(Intent(this, LoginActivity::class.java))
                        showSnack(this,"OK:Cadastro realizado com sucesso...")
                        finish()
                    }
                }
                .addOnFailureListener {
                    showSnack(this,"ERRO:Verifique sua senha..")
                }
        } else {
            showSnack(this@RegistryActivity,"ERRO:Há campos que não foram preenchidos...")
        }
    }

    private fun showSnack(activity: Activity?, text:String, duration:Int = Snackbar.LENGTH_SHORT) {
        if (activity != null) {
            Snackbar.make(activity.findViewById(android.R.id.content),text,duration).show()
        }
    }

    private fun showErro(str: EditText, error: String) {
        return str.setError(error)
    }
}