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
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.list.*
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

    companion object {

        @JvmStatic
        fun newInstance() =
                InvestorNotificationsFragment().apply {
                    arguments = Bundle().apply {
                        // putInt(ARG_COLUMN_COUNT, columnCount)
                    }
                }
    }

    private lateinit var adapter: MyQuoteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            // columnCount = it.getInt(ARG_COLUMN_COUNT)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_notifications_pemilik, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val items = listOf(
                Users("Premature optimization is the root of all evil"),
                Users("Any sufficiently advanced technology is indistinguishable from magic."),
                Users("Content 01"),
                Users("Content 02"),
                Users("Content 03")
        )

        adapter = MyQuoteAdapter()
        adapter.replaceItems(items)
        notif.adapter = adapter
    }

    class MyQuoteAdapter : RecyclerView.Adapter<MyQuoteAdapter.ViewHolder>() {
        private var items = listOf<Users>()


        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.fragment_notifications_pemilik, parent, false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val item = items[position]

            holder.textViewNotif.text = item.name
        }

        fun replaceItems(items: List<Users>) {
            this.items = items
            notifyDataSetChanged()
        }

        override fun getItemCount(): Int = items.size

        inner class ViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer
    }

}

//    private lateinit var notificationsViewModel: NotificationsViewModel
//
//    @SuppressLint("WrongConstant")
//    override fun onCreateView(
//            inflater: LayoutInflater,
//            container: ViewGroup?,
//            savedInstanceState: Bundle?
//    ): View? {
//
////        notificationsViewModel =
////                ViewModelProviders.of(this).get(NotificationsViewModel::class.java)
//
//        val root = inflater.inflate(R.layout.fragment_notifications_pemilik, container, false)
//        val recyclerView: RecyclerView = root.findViewById(R.id.notif)
////        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)
////        notificationsViewModel.text.observe(this, Observer {
////            textView.text = it
////        })
//
////        return root
////        val users = ArrayList<Users>()
////
////        users.add(Users("Transaksi Berhasil"))
////
////        val adapter = Adapter(users)
//
//        //recyclerView.adapter=adapter
//        return root
//    }


//}