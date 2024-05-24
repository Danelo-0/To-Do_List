package ru.matveev.lab_4

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import androidx.lifecycle.asLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val addButton : ImageButton = findViewById(R.id.addButton)
        val listWork : RecyclerView = findViewById(R.id.listWork)

        val db = MyDB.getDB(this)

        db.getDao().getAllWorkSortedByStatusAndDate().asLiveData().observe(this){ list->

            listWork.layoutManager = LinearLayoutManager(this)
            listWork.adapter = MyAdapter(this, list)
        }

        addButton.setOnClickListener{

            val intent = Intent(this, AddActivity::class.java)
            startActivity(intent)
        }


    }
}