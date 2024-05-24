package ru.matveev.lab_4

import android.app.AlertDialog
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import java.text.SimpleDateFormat
import java.util.Date

class ContentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_content)

        fun changeActivity(){

            val intentMain = Intent(this, MainActivity::class.java)
            startActivity(intentMain)
        }

        val db = MyDB.getDB(this)

        val backButton : ImageButton = findViewById(R.id.backButton)
        val titleText: TextView = findViewById(R.id.titleContent)
        val descriptionText: TextView = findViewById(R.id.descriptionContent)
        val dateContent: TextView = findViewById(R.id.dateContent)
        val buttonDelete: TextView = findViewById(R.id.buttonDelete)
        val buttonDone: TextView = findViewById(R.id.buttonDone)
        val buttonUpdate: TextView = findViewById(R.id.buttonUpdate)

        val format = SimpleDateFormat("dd.MM.yyyy")
        val dateLocal = System.currentTimeMillis()

        val id = intent.getIntExtra("id", 0)
        val title = intent.getStringExtra("title")
        val description = intent.getStringExtra("description")
        val date = intent.getLongExtra("date", 0)
        val status = intent.getBooleanExtra("status", false)

        val alert = AlertDialog.Builder(this)
            .setMessage("Поздравляем. Задача выполнена")
            .setPositiveButton("ОК") { d, id->
                d.cancel()

                changeActivity()
            }
            .create()

        titleText.text = title
        descriptionText.text = description
        dateContent.text = format.format(Date(date))

        if(date < (dateLocal - 86400000)) {
            dateContent.setTextColor(Color.RED)
        }

        if(status){
            buttonDone.isEnabled = false
            buttonDone.setBackgroundColor(Color.parseColor("#686868"))
            buttonDone.setTextColor(Color.WHITE)

            buttonUpdate.isEnabled = false
            buttonUpdate.setBackgroundColor(Color.parseColor("#686868"))
            buttonUpdate.setTextColor(Color.WHITE)
        }

        backButton.setOnClickListener{

            changeActivity()
        }

        buttonDelete.setOnClickListener{

            Thread{
                db.getDao().deleteById(id)
            }.start()

            changeActivity()
        }

        buttonDone.setOnClickListener{

            Thread{
                db.getDao().updateStatusById(id, true)
            }.start()

            alert.show()
        }

        buttonUpdate.setOnClickListener{

            val intent = Intent(this, UpdateActivity::class.java).apply {
                putExtra("id", id)
                putExtra("title", title)
                putExtra("description", description)
                putExtra("date", date)
                putExtra("status", status)
            }
            startActivity(intent)

        }





    }
}