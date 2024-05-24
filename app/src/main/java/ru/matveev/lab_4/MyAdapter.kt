package ru.matveev.lab_4

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.*
import android.graphics.Paint
import androidx.constraintlayout.widget.ConstraintLayout


class MyAdapter(private val context: Context, private val listWork: List<Work>) :  RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        val title: TextView = itemView.findViewById(R.id.title)
        val description: TextView = itemView.findViewById(R.id.description)
        val date_text: TextView = itemView.findViewById(R.id.date_text)
        val cardView: CardView = itemView.findViewById(R.id.cardView)
        val сonstraintLayout: ConstraintLayout = itemView.findViewById(R.id.сonstraintLayout)
        val format = SimpleDateFormat("dd.MM.yyyy")
        val dateLocal = System.currentTimeMillis()

        fun bindSet(work: Work){

            if(work.title.length > 15)
            {
                title.text = work.title.substring(0, 15-3) + "..."
            }
            else
            {
                title.text = work.title
            }

            if(work.description?.length!! > 70)
            {
                description.text = work.description?.substring(0, 70-3) + "..."
            }
            else
            {
                description.text = work.description
            }
            date_text.text = format.format(Date(work.date))

            if(work.date < (dateLocal - 86400000)) {
                date_text.setTextColor(Color.RED)
            }

            if(work.status) {
                title.paintFlags = title.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                description.paintFlags = description.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                date_text.paintFlags = date_text.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                сonstraintLayout.setBackgroundColor(Color.parseColor("#686868"))
                date_text.setTextColor(Color.BLACK)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.work_item, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
       holder.bindSet(listWork[position])
        holder.cardView.setOnClickListener{

            val intent = Intent(context, ContentActivity::class.java).apply {
                putExtra("id", listWork[position].id)
                putExtra("title", listWork[position].title)
                putExtra("description", listWork[position].description)
                putExtra("date", listWork[position].date)
                putExtra("status", listWork[position].status)
            }
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return listWork.size
    }
}