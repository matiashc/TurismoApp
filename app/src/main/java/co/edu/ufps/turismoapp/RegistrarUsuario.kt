package co.edu.ufps.turismoapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class RegistrarUsuario : AppCompatActivity() {
    lateinit var usuario: TextInputEditText
    lateinit var clave: TextInputEditText
    lateinit var registrar: Button
    private lateinit var auth: FirebaseAuth
    val TAG: String = "RegistrarUsuario"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registrar_usuario)
        usuario = findViewById(R.id.usuario)
        clave = findViewById(R.id.clave)
        registrar = findViewById(R.id.registrar)
        // Initialize Firebase Auth
        auth = Firebase.auth
        registrar.setOnClickListener {
            createAccount(usuario.text.toString(),clave.text.toString())
        }

    }
    fun createAccount(email: String, password: String){
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "createUserWithEmail:success")
                    val user = auth.currentUser
                    finish()
                    //updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    Toast.makeText(
                        baseContext,
                        "Authentication failed.",
                        Toast.LENGTH_SHORT,
                    ).show()
                   // updateUI(null)
                }
            }
    }
}