package co.edu.ufps.turismoapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import co.edu.ufps.turismoapp.vista.Contenedor
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
 lateinit var usuario: TextInputEditText
 lateinit var clave: TextInputEditText
 lateinit var inicio: Button
 lateinit var crearUsuario: TextView
 val TAG: String = "MainActivity"
 private lateinit var auth: FirebaseAuth;


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        usuario = findViewById(R.id.usuario)
        clave = findViewById(R.id.clave)
        inicio = findViewById(R.id.iniciar)
        crearUsuario = findViewById(R.id.crearusuario)
        auth = Firebase.auth
        inicio.setOnClickListener {
            signIn(usuario.text.toString(), clave.text.toString())
        }
        crearUsuario.setOnClickListener {
            registrar()
        }
    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        //updateUI(currentUser)
    }

    fun signIn(usuario: String, clave: String){
        auth.signInWithEmailAndPassword(usuario, clave)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithEmail:success")
                    val user = auth.currentUser
                    Toast.makeText(
                        baseContext,
                        "Authentication exitosa.",
                        Toast.LENGTH_SHORT,
                    ).show()

                    irInicio()
                    //updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithEmail:failure", task.exception)
                    Toast.makeText(
                        baseContext,
                        "Authentication failed.",
                        Toast.LENGTH_SHORT,
                    ).show()
                   // updateUI(null)
                }
            }
    }

    fun registrar (){
        val intent = Intent(this,RegistrarUsuario::class.java)
        startActivity(intent)
    }

    fun irInicio (){
        val intent = Intent(this,Contenedor::class.java)
        startActivity(intent)
    }


}