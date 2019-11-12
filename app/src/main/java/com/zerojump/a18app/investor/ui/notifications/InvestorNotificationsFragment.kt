package com.zerojump.a18app.investor.ui.notifications

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.zerojump.a18app.R
import com.zerojump.a18app.user.ui.notifications.NotificationsViewModel
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_notifications_pemilik.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import java.net.URLConnection

//class InvestorNotificationsFragment : AppCompatActivity() {
//
//    val list = ArrayList<Users>()
//
//    val listUsers = arrayOf(
//            "Google",
//            "Apple",
//            "Microsoft",
//            "Asus",
//            "Zenpone",
//            "Acer"
//    )
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.fragment_notifications_pemilik)
//
//        mRecyclerView.setHasFixedSize(true)
//        mRecyclerView.layoutManager = LinearLayoutManager(this)
//
//        for (i in 0 until listUsers.size){
//
//            list.add(Users(listUsers.get(i)))
//
//            if(listUsers.size - 1 == i){
//                // init adapter yang telah dibuat tadi
//                val adapter = Adapter(list)
//                adapter.notifyDataSetChanged()
//
//                //tampilkan data dalam recycler view
//                mRecyclerView.adapter = adapter
//            }
//
//        }
//
//    }
//}

class InvestorNotificationsFragment : Fragment() {
    private lateinit var notificationsViewModel: NotificationsViewModel

    @SuppressLint("WrongConstant")
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

//        notificationsViewModel =
//                ViewModelProviders.of(this).get(NotificationsViewModel::class.java)

        val root = inflater.inflate(R.layout.fragment_notifications_pemilik, container, false)
        val recyclerView: RecyclerView = root.findViewById(R.id.notif)
//        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)
//        notificationsViewModel.text.observe(this, Observer {
//            textView.text = it
//        })

//        return root
//        val users = ArrayList<Users>()
//
//        users.add(Users("Transaksi Berhasil"))
//
//        val adapter = Adapter(users)

        //recyclerView.adapter=adapter
        return root
    }


}