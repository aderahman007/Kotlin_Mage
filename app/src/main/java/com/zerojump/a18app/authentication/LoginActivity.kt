package com.zerojump.a18app.authentication

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.zerojump.a18app.R
import com.zerojump.a18app.utils.debounce.onClickDebounced
import kotlinx.android.synthetic.main.activity_login.*
import android.content.Intent
import android.widget.Toast
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.list.listItems
import com.github.loadingview.LoadingDialog
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import com.zerojump.a18app.data.RefrenceUser
import com.zerojump.a18app.data.models.User
import com.zerojump.a18app.investor.InvestorMainActivity
import com.zerojump.a18app.user.UserMainActivity


class LoginActivity : AppCompatActivity() {
    /* variale*/
    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private lateinit var loadingDialog : LoadingDialog
    private  val firebaseAuth = FirebaseAuth.getInstance()
    private val mFirestore : FirebaseFirestore = FirebaseFirestore.getInstance()
    companion object {
        const val RC_SIGN_IN = 1
        const val INVESTOR = 100
        const val PEMILIK = 200

        fun getLaunchIntent(from: Context) = Intent(from, LoginActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        initConfigure()
        isLogin()

        sign_google.onClickDebounced {
            val signInIntent = mGoogleSignInClient.signInIntent
            startActivityForResult(signInIntent, RC_SIGN_IN)
            loadingDialog.show()
        }
    }

    private fun isLogin() {
        if(firebaseAuth.currentUser !=null){
            RefrenceUser.refUser(mFirestore).document(firebaseAuth.currentUser!!.uid).get().addOnSuccessListener { document->
                println("development  document: ${firebaseAuth.currentUser!!.uid}")
                if (document.exists()) {
                    val user = document.toObject(User::class.java)
                    println(user)
                    if(user!!.m_sebagai == INVESTOR){
                        goToInvestor()
                    }else if(user.m_sebagai == PEMILIK){
                        goTOPemilik()
                    }else{
                        Toast.makeText(this@LoginActivity,"Something Error" , Toast.LENGTH_LONG).show()
                    }
                } else {
                    selectJob(GoogleSignIn.getLastSignedInAccount(this@LoginActivity))
                }
            }.addOnFailureListener {

            }
        }else{

        }

    }

    private fun initConfigure() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.defaultToken))
            .requestEmail()
            .build()
        mGoogleSignInClient = GoogleSignIn.getClient(this@LoginActivity, gso)

        loadingDialog = LoadingDialog.get(this@LoginActivity)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        println("development  onActivityResult $requestCode  || $resultCode")
        if (requestCode == RC_SIGN_IN) {
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }

    private fun handleSignInResult(task: Task<GoogleSignInAccount>) {
        println("development  handleSignInResult")
            task.addOnSuccessListener {
                firebaseAuthWithGoogle(it)
            }.addOnFailureListener {
                Toast.makeText(this, "Google sign in failed:(", Toast.LENGTH_LONG).show()
            }



    }


    private fun selectJob(account: GoogleSignInAccount?) {
            account?.let{googleAccount->
                loadingDialog.hide()
                val myItems = listOf<String>("Investor" , "Pemilik")
                MaterialDialog(this).show {
                    title = "Pilih"
                    cornerRadius(16f)

                    positiveButton(text = "OK"){
                        dismiss()
                    }

                    listItems(items = myItems){dialog,index,text->
                        if(index == 0){
                            registerInvestor(googleAccount)
                            this.dismiss()
                            loadingDialog.show()
                        }else{
                            registerPemilik(googleAccount)
                            this.dismiss()
                            loadingDialog.show()
                        }

                    }
                }


            }


    }

    private fun registerPemilik(account: GoogleSignInAccount) {
        val mPemilik = User(account.displayName,account.email,"",account.photoUrl.toString(),"",PEMILIK)
        RefrenceUser.refUser(mFirestore).document(firebaseAuth.currentUser!!.uid).set(mPemilik).addOnSuccessListener {
            isLogin()
        }
    }

    private fun registerInvestor(account: GoogleSignInAccount) {
        val mInvestor = User(account.displayName,account.email,"",account.photoUrl.toString(),"",
            INVESTOR)
        RefrenceUser.refUser(mFirestore).document(firebaseAuth.currentUser!!.uid).set(mInvestor).addOnSuccessListener {
            isLogin()
        }
    }

    private fun goTOPemilik() {
        loadingDialog.hide()
        startActivity(Intent(this@LoginActivity,UserMainActivity::class.java)
            .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP))
        finish()
    }

    private fun goToInvestor() {
        loadingDialog.hide()
        startActivity(Intent(this@LoginActivity,InvestorMainActivity::class.java)
            .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP))
        finish()
    }


    private fun firebaseAuthWithGoogle(
        acct: GoogleSignInAccount
    ) {

        val credential = GoogleAuthProvider.getCredential(acct.idToken, null)
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener {
            if (it.isSuccessful) {
                isLogin()
            } else {
                Toast.makeText(this, "Google sign in failed:(", Toast.LENGTH_LONG).show()

            }

        }

    }

}
