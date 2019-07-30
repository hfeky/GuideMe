package com.guideme.guideme.ui.authentication

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.*
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.guideme.guideme.R
import com.guideme.guideme.data.DataManager
import com.guideme.guideme.data.LocalDataManager
import com.guideme.guideme.data.models.User
import com.guideme.guideme.ui.home.MainActivity
import kotlinx.android.synthetic.main.layout_log_in.*

class LogInActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = FirebaseAuth.getInstance()

        val currentUser = auth.currentUser
        if (currentUser != null) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        } else {
            setContentView(R.layout.layout_log_in)

            initTextListeners()
            initSpannableStrings()

            logIn.setOnClickListener {
                if (isContentFilledCorrectly()) {
                    signInUser(email.text.toString(), password.text.toString())
                }
            }
        }
    }

    private fun initTextListeners() {
        email.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable) {
                emailInputLayout.error = null
            }
        })

        password.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable) {
                passwordInputLayout.error = null
            }
        })
    }

    private fun initSpannableStrings() {
        val spannableString1 = SpannableString("Don\'t have an account? Sign up.")
        val clickableSpan1 = object : ClickableSpan() {
            override fun onClick(textView: View) {
                startActivity(Intent(this@LogInActivity, SignUpActivity::class.java))
            }

            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                ds.isUnderlineText = false
            }
        }
        val string1 = "Sign up"
        val startIndex1 = spannableString1.indexOf(string1)
        spannableString1.setSpan(
            clickableSpan1,
            startIndex1,
            startIndex1 + string1.length,
            Spanned.SPAN_INCLUSIVE_EXCLUSIVE
        )
        signUp.text = spannableString1
        signUp.movementMethod = LinkMovementMethod.getInstance()
        signUp.highlightColor = Color.TRANSPARENT

        val spannableString2 =
            SpannableString("By logging in, I accept Guide Me\nTerms and Conditions.")
        val clickableSpan2 = object : ClickableSpan() {
            override fun onClick(textView: View) {
                startActivity(Intent(this@LogInActivity, TermsAndConditionsActivity::class.java))
            }

            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                ds.isUnderlineText = false
            }
        }
        val string2 = "Terms and Conditions"
        val startIndex2 = spannableString2.indexOf(string2)
        spannableString2.setSpan(
            clickableSpan2,
            startIndex2,
            startIndex2 + string2.length,
            Spanned.SPAN_INCLUSIVE_EXCLUSIVE
        )
        termsAndConditions.text = spannableString2
        termsAndConditions.movementMethod = LinkMovementMethod.getInstance()
        termsAndConditions.highlightColor = Color.TRANSPARENT
    }

    private fun isContentFilledCorrectly(): Boolean {
        var result = true

        if (email.length() == 0) {
            emailInputLayout.error = "No email entered"
            result = false
        }

        if (password.length() == 0) {
            passwordInputLayout.error = "No password entered"
            result = false
        }

        return result
    }

    private fun signInUser(email: String, password: String) {
        logIn.visibility = View.INVISIBLE
        progressBar.visibility = View.VISIBLE
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // User sign in succeeded.
                    DataManager().getUser(auth.uid!!, OnSuccessListener { result ->
                        val user = User(
                            result["name"] as String,
                            result["phone_number"] as String,
                            result["photo"] as String
                        )
                        LocalDataManager(this).saveProfile(user.name, user.phoneNumber, user.photo)
                    })
                    startActivity(Intent(this, MainActivity::class.java))
                    finishAffinity()
                } else {
                    // User sign in failed.
                    logIn.visibility = View.VISIBLE
                    progressBar.visibility = View.INVISIBLE
                    when {
                        task.exception is FirebaseAuthInvalidUserException ->
                            emailInputLayout.error = "User does not exist. Sign up?"
                        task.exception is FirebaseAuthInvalidCredentialsException ->
                            passwordInputLayout.error = "Password is invalid"
                        else ->
                            Toast.makeText(
                                baseContext,
                                "Log in failed. Please try again.",
                                Toast.LENGTH_SHORT
                            ).show()
                    }
                }
            }
    }
}
