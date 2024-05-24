package ru.matveev.lab_4

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.CalendarView
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import java.util.*

class AddActivity : AppCompatActivity() {

    private var selectedDate: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)

        fun changeActivity(){

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        val backButton : ImageButton = findViewById(R.id.backButton)
        val buttonAddInDB : Button = findViewById(R.id.buttonAddInDB)
        val title : EditText = findViewById(R.id.editTitle)
        val description : EditText = findViewById(R.id.editDescription)
        val calendarDate: CalendarView = findViewById(R.id.dateCalendar)

        val db = MyDB.getDB(this)

        val alert = AlertDialog.Builder(this)
            .setMessage("Название и дата являются обязательными полями")
            .setPositiveButton("ОК") { d, id->
                d.cancel()
            }
            .create()


        calendarDate.setOnDateChangeListener { view, year, month, dayOfMonth ->
            val calendar = Calendar.getInstance()
            calendar.set(year, month, dayOfMonth)
            selectedDate = calendar.timeInMillis
        }

        backButton.setOnClickListener{

            changeActivity()
        }

        buttonAddInDB.setOnClickListener {

            if(!title.text.isEmpty()) {
                val work = Work(
                    null,
                    title.text.toString(),
                    description.text.toString(),
                    selectedDate
                )
                Thread {
                    db.getDao().insertItem(work)
                }.start()

                title.setText("")
                description.setText("")

                changeActivity()
            }
            else{
                alert.show()
            }

        }




    }
}