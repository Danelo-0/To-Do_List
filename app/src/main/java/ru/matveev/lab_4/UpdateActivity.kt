package ru.matveev.lab_4

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.CalendarView
import android.widget.EditText
import android.widget.ImageButton
import java.util.Calendar

class UpdateActivity : AppCompatActivity() {

    private var selectedDate: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update)

        val db = MyDB.getDB(this)

        val backButton : ImageButton = findViewById(R.id.backButton)
        val buttonUpdate : Button = findViewById(R.id.buttonUpdate)
        val titleText : EditText = findViewById(R.id.editTitle)
        val descriptionText : EditText = findViewById(R.id.editDescription)
        val calendarDate: CalendarView = findViewById(R.id.dateCalendar)

        val id = intent.getIntExtra("id", 0)
        val title = intent.getStringExtra("title")
        val description = intent.getStringExtra("description")
        val date = intent.getLongExtra("date", 0)
        val status = intent.getBooleanExtra("status", false)


        titleText.setText(title)
        descriptionText.setText(description)
        calendarDate.setDate(date, true, true)

        calendarDate.setOnDateChangeListener { view, year, month, dayOfMonth ->
            val calendar = Calendar.getInstance()
            calendar.set(year, month, dayOfMonth)
            selectedDate = calendar.timeInMillis
        }

        fun changeActivity(){

            val intent = Intent(this, ContentActivity::class.java).apply {
                putExtra("id", id)
                putExtra("title", titleText.text.toString())
                putExtra("description", descriptionText.text.toString())
                putExtra("date", selectedDate)
                putExtra("status", status)
            }
            startActivity(intent)
        }


        backButton.setOnClickListener{

            changeActivity()
        }

        buttonUpdate.setOnClickListener{

            Thread{
                db.getDao().updateFullById(
                    id,
                    titleText.text.toString(),
                    descriptionText.text.toString(),
                    selectedDate)
            }.start()

            changeActivity()
        }

    }
}