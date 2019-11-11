package com.zerojump.a18app.authentication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.zerojump.a18app.R
import com.zerojump.a18app.utils.debounce.onClickDebounced
import kotlinx.android.synthetic.main.activity_login.*
import android.content.Intent
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.list.listItems
import com.github.loadingview.LoadingDialog
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FirebaseFirestore
import com.zerojump.a18app.data.RefrenceUser
import com.zerojump.a18app.data.models.User
import com.zerojump.a18app.investor.InvestorMainActivity
import com.zerojump.a18app.user.UserMainActivity
import kotlin.Exception


class LoginActivity : AppCompatActivity() {
    /* variale*/
    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private lateinit var loadingDialog : LoadingDialog
    companion object {
        const val RC_SIGN_IN = 1
        const val INVESTOR = 100
        const val PEMILIK = 200
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        initConfigure()
        sign_google.onClickDebounced {

            val signInIntent = mGoogleSignInClient.signInIntent
            startActivityForResult(signInIntent, RC_SIGN_IN)
        }
    }

    private fun initConfigure() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail()
            .build()
        mGoogleSignInClient = GoogleSignIn.getClient(this@LoginActivity, gso)
        loadingDialog = LoadingDialog.get(this@LoginActivity)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RC_SIGN_IN) {
            loadingDialog.show()
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task);
        }
    }

    private fun handleSignInResult(task: Task<GoogleSignInAccount>) {
        try {
            val account: GoogleSignInAccount? = task.getResult(ApiException::class.java)
            cekLogin(account)
        } catch (e: Exception) {
            cekLogin(null)
        }
    }

    private fun cekLogin(account: GoogleSignInAccount?) {
        account?.let {
            RefrenceUser.refUser().document(account.id.toString()).get().addOnSuccessListener {document->
                if (document != null) {
                    val user = document.toObject(User::class.java)
                    user?.let{
                        if(it.mSebagai == INVESTOR){
                            goToInvestor()
                        }else{
                            goTOPemilik()
                        }
                    }
                } else {
                   selectJob(account)
                }
            }.addOnFailureListener {

            }
        }
    }

    private fun selectJob(account: GoogleSignInAccount) {
        loadingDialog.hide()
        val myItems = listOf<String>("Investor" , "Pemilik")
        MaterialDialog(this).show {
            title = "Pilih"
            cornerRadius(16f)
            cancelable(false)
            positiveButton(text = "Batalkan"){
                dismiss()
            }

            listItems(items = myItems){dialog,index,text->
                if(index == 0){
                    registerInvestor(account)
                }else{
                    registerPemilik(account)
                }

            }
        }

    }

    private fun registerPemilik(account: GoogleSignInAccount) {
        val mPemilik = User(account.displayName,account.email,"",account.photoUrl.toString(),"",PEMILIK)
        RefrenceUser.refUser().document(account.id.toString()).set(mPemilik).addOnSuccessListener {
            goTOPemilik()
        }
    }

    private fun registerInvestor(account: GoogleSignInAccount) {
        val mInvestor = User(account.displayName,account.email,"",account.photoUrl.toString(),"",
            INVESTOR)
        RefrenceUser.refUser().document(account.id.toString()).set(mInvestor).addOnSuccessListener {
            goToInvestor()
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
}
