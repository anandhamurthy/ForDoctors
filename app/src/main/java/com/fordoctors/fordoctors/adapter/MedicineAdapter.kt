package com.fordoctors.fordoctors.adapter

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.fordoctors.fordoctors.R
import com.fordoctors.fordoctors.model.Medicine

class MedicineAdapter(val userList: ArrayList<Medicine>) : RecyclerView.Adapter<MedicineAdapter.ViewHolder>() {

    //this method is returning the view for each item in the list
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.single_prescription_row, parent, false)
        return ViewHolder(v)
    }

    //this method is binding the data on the list
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(userList[position])
    }

    //this method is giving the size of the list
    override fun getItemCount(): Int {
        return userList.size
    }

    //the class is hodling the list view
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindItems(medicine: Medicine) {
            val Name = itemView.findViewById(R.id.name) as TextView
            val Morning  = itemView.findViewById(R.id.morning) as TextView
            val Afternoon  = itemView.findViewById(R.id.afternoon) as TextView
            val Evening  = itemView.findViewById(R.id.evening) as TextView
            val Night  = itemView.findViewById(R.id.night) as TextView
            val Quantity  = itemView.findViewById(R.id.quantity) as TextView
            Name.text = medicine.name
            Morning.text = medicine.mor
            Afternoon.text = medicine.aft
            Evening.text = medicine.eve
            Night.text = medicine.nig
            Quantity.text = medicine.quantity
        }
    }
}