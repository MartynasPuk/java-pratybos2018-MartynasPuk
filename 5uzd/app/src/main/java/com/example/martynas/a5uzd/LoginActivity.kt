package com.example.martynas.a5uzd

import android.app.PendingIntent
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.view.View.OnClickListener
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.EmailAuthCredential
import com.google.firebase.auth.FirebaseUser
import com.okta.appauth.android.OktaAppAuth
import kotlinx.android.synthetic.main.activity_login.*
import net.openid.appauth.AuthorizationException


class LoginActivity : AppCompatActivity() {

    private var mOktaAuth: OktaAppAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mOktaAuth = OktaAppAuth.getInstance(this)

        setContentView(R.layout.activity_login)

        mOktaAuth!!.init(
            this,
            object : OktaAppAuth.OktaAuthListener {
                override fun onSuccess() {
                    auth_button.visibility = View.VISIBLE
                    auth_message.visibility = View.GONE
                    progress_bar.visibility = View.GONE
                }

                override fun onTokenFailure(ex: AuthorizationException) {
                    auth_message.text = ex.toString()
                    progress_bar.visibility = View.GONE
                    auth_button.visibility = View.GONE
                }
            }
        )

        val button = findViewById(R.id.auth_button) as Button
        button.setOnClickListener { v ->
            val completionIntent = Intent(v.context, MainActivity::class.java)
            val cancelIntent = Intent(v.context, LoginActivity::class.java)

            cancelIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP

            mOktaAuth!!.login(
                v.context,
                PendingIntent.getActivity(v.context, 0, completionIntent, 0),
                PendingIntent.getActivity(v.context, 0, cancelIntent, 0)
            )
        }
    }
}


/*class LoginActivity : OnClickListener, AppCompatActivity() {

    override fun onClick(v: View?) {
        signIn(email.text.toString(), password.text.toString())
    }

    private val TAG = "FirebaseEmailPassword"

    private var mAuth: FirebaseAuth? = null

    /*private lateinit var mAuth : FirebaseAuth
    private lateinit var loginButton: Button
    private lateinit var email: EditText
    private lateinit var password: EditText*/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        button.setOnClickListener(this)

        mAuth = FirebaseAuth.getInstance()

        /*email = findViewById(R.id.email) as EditText
        password = findViewById(R.id.password) as EditText
        loginButton = findViewById(R.id.button) as Button


        loginButton.setOnClickListener{
            mAuth = FirebaseAuth.getInstance()
            mAuth.signInWithEmailAndPassword(email.text.toString(), password.text.toString()).addOnCompleteListener { task: Task<AuthResult> ->
                val intentToMain = Intent(this@LoginActivity, MainActivity::class.java)
                startActivity(intentToMain)*/
            }


    private fun signIn(email: String, password: String) {
        Log.e(TAG, "signIn:" + email)
        if(!validateForm(email, password)){
            return
        }

        mAuth!!.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if(task.isSuccessful){
                    Log.e(TAG, "signIn: Success!")


                    val user = mAuth!!.getCurrentUser()
                    updateUI(user)
                } else {
                    Log.e(TAG, "signIn: Fail!", task.exception)
                    Toast.makeText(applicationContext, "Authentication failed!", Toast.LENGTH_SHORT).show()
                    updateUI(null)
                }

            }
    }

    private fun validateForm(email: String, password: String): Boolean {
        if(TextUtils.isEmpty(email)) {
            Toast.makeText(applicationContext, "Enter email address!", Toast.LENGTH_LONG).show()
            return false
        }

        if(TextUtils.isEmpty(password)) {
            Toast.makeText(applicationContext, "Enter password!", Toast.LENGTH_SHORT).show()
            return false
        }

        if(password.length < 6) {
            Toast.makeText(applicationContext, "Password too short! Enter minimum 6 characters", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    private fun updateUI(user: FirebaseUser?) {
        val intent = Intent(this@LoginActivity, MainActivity::class.java)
        startActivity(intent)

        }


        //mAuth = FirebaseAuth.getInstance()

        /*var button = findViewById<Button>(R.id.button)
        button.setOnClickListener {view ->
            signIn(view, "user@company.com", "pass")
        }*/


    /*fun signIn(view: View, email: String, password: String){
        showMessage(view, "Authenticating...")

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task: Task<AuthResult> ->

            if(task.isSuccessful){
                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra("id", mAuth.currentUser?.email)
                startActivity(intent)
            }
            else{
                showMessage(view,"Error: ${task.exception?.message}")
            }
        }
        //showMessage(view, "Please fill all fields")
    }*/

    fun showMessage(view: View, message: String) {
        Snackbar.make(view, message, Snackbar.LENGTH_INDEFINITE).setAction("Action", null).show()
    }
}*/