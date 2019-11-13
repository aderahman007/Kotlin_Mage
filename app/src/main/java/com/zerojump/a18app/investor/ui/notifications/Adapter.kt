package com.zerojump.a18app.investor.ui.notifications

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.zerojump.a18app.R
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.fragment_notifications_pemilik.*



class Adapter(val userList: ArrayList<Users>) : RecyclerView.Adapter<Adapter.ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v=LayoutInflater.from(parent.context).inflate(R.layout.list, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user: Users=userList.get(position)

        holder.textViewNotif.text=user.name
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val textViewNotif = itemView.findViewById(R.id.textViewNotif) as TextView
    }
}