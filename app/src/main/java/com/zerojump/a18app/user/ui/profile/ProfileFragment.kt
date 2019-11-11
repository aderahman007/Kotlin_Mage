package com.zerojump.a18app.user.ui.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.bumptech.glide.Glide
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth

import com.zerojump.a18app.R
import com.zerojump.a18app.authentication.LoginActivity
import com.zerojump.a18app.utils.debounce.onClickDebounced
import kotlinx.android.synthetic.main.fragment_profile.*


class ProfileFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
            val userGoogle = GoogleSignIn.getLastSignedInAccount(context!!)
            try {
                userGoogle?.let{
                    tvnama.setText(it.displayName)
                    Glide.with(this@ProfileFragment).load(it.photoUrl).into(cv_profile)
                }
            }catch (e :Exception){
                Toast.makeText(context,"Something Error" , Toast.LENGTH_LONG).show()
            }
    }

    override fun onDetach() {
        super.onDetach()

    }



    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        action_logOut.onClickDebounced {
            signOut()
        }
    }
    private fun signOut() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.defaultToken))
            .requestEmail()
            .build()
       val mGoogleSignInClient = GoogleSignIn.getClient(activity!!, gso)
        mGoogleSignInClient.signOut().addOnSuccessListener {
            startActivity(LoginActivity.getLaunchIntent(context!!))
            FirebaseAuth.getInstance().signOut()
            activity?.finish()
        }

    }

}
