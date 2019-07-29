package com.guideme.guideme.ui.authentication

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.*
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.guideme.guideme.R
import com.guideme.guideme.data.DataManager
import com.guideme.guideme.data.models.User
import com.guideme.guideme.ui.home.MainActivity
import kotlinx.android.synthetic.main.layout_sign_up.*

private const val PASSWORD_MIN_LENGTH = 8

class SignUpActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_sign_up)

        auth = FirebaseAuth.getInstance()

        initTextListeners()
        initSpannableStrings()

        createAccount.setOnClickListener {
            if (isContentFilledCorrectly()) {
                createAccount(email.text.toString(), password.text.toString())
            }
        }
    }

    private fun initTextListeners() {
        name.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable) {
                if (s.length > nameInputLayout.counterMaxLength) {
                    nameInputLayout.error = "Should not exceed ${nameInputLayout.counterMaxLength} characters"
                } else {
                    nameInputLayout.error = null
                }
            }
        })

        phoneNumber.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable) {
                phoneNumberInputLayout.error = null
            }
        })

        email.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable) {
                if (s.isEmpty()) {
                    emailInputLayout.error = null
                } else if (!Patterns.EMAIL_ADDRESS.matcher(email.text).matches()) {
                    emailInputLayout.error = "Email is not valid"
                } else {
                    emailInputLayout.error = null
                }
            }
        })

        password.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable) {
                when {
                    s.isEmpty() -> passwordInputLayout.error = null
                    password.length() < PASSWORD_MIN_LENGTH ->
                        passwordInputLayout.error = "Min password length is $PASSWORD_MIN_LENGTH"
                    else -> passwordInputLayout.error = null
                }
            }
        })

        confirmPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable) {
                when {
                    s.isEmpty() -> confirmPasswordInputLayout.error = null
                    password.text.toString() != confirmPassword.text.toString() ->
                        confirmPasswordInputLayout.error = "Password is different"
                    else -> confirmPasswordInputLayout.error = null
                }
            }
        })
    }

    private fun initSpannableStrings() {
        val spannableString1 = SpannableString("Already have an account? Log in.")
        val clickableSpan1 = object : ClickableSpan() {
            override fun onClick(textView: View) {
                finish()
            }

            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                ds.isUnderlineText = false
            }
        }
        val string1 = "Log in"
        val startIndex1 = spannableString1.indexOf(string1)
        spannableString1.setSpan(
            clickableSpan1,
            startIndex1,
            startIndex1 + string1.length,
            Spanned.SPAN_INCLUSIVE_EXCLUSIVE
        )
        logIn.text = spannableString1
        logIn.movementMethod = LinkMovementMethod.getInstance()
        logIn.highlightColor = Color.TRANSPARENT

        val spannableString2 = SpannableString("By creating an account, I accept Guide Me\nTerms and Conditions.")
        val clickableSpan2 = object : ClickableSpan() {
            override fun onClick(textView: View) {
                startActivity(Intent(this@SignUpActivity, TermsAndConditionsActivity::class.java))
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

        if (name.length() == 0) {
            nameInputLayout.error = "No name entered"
            result = false
        } else if (name.length() > nameInputLayout.counterMaxLength) {
            nameInputLayout.error = "Should not exceed ${nameInputLayout.counterMaxLength} characters"
            result = false
        }

        if (phoneNumber.length() == 0) {
            phoneNumberInputLayout.error = "No phone number entered"
            result = false
        }

        if (email.length() == 0) {
            emailInputLayout.error = "No email entered"
            result = false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email.text).matches()) {
            emailInputLayout.error = "Email is not valid"
            result = false
        }

        if (password.length() == 0) {
            passwordInputLayout.error = "No password entered"
            result = false
        } else if (password.length() < PASSWORD_MIN_LENGTH) {
            passwordInputLayout.error = "Min password length is $PASSWORD_MIN_LENGTH"
            result = false
        }

        if (confirmPassword.length() == 0) {
            confirmPasswordInputLayout.error = "No password entered"
            result = false
        } else if (password.text.toString() != confirmPassword.text.toString()) {
            confirmPasswordInputLayout.error = "Password is different"
            result = false
        }

        return result
    }

    private fun createAccount(email: String, password: String) {
        createAccount.visibility = View.INVISIBLE
        progressBar.visibility = View.VISIBLE
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Account creation succeeded.
                    val user = User(name.text.toString(), phoneNumber.text.toString(), "")
                    DataManager().createNewUser(auth.uid!!, user, OnSuccessListener {
                        startActivity(Intent(this, MainActivity::class.java))
                        finishAffinity()
                    })
                } else {
                    // Account creation failed.
                    createAccount.visibility = View.VISIBLE
                    progressBar.visibility = View.INVISIBLE
                    if (task.exception is FirebaseAuthUserCollisionException) {
                        emailInputLayout.error = "User already exists"
                    } else {
                        Toast.makeText(
                            baseContext,
                            "User account creation failed. Please try again.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
    }
}
