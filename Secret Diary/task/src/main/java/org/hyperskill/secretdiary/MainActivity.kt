package org.hyperskill.secretdiary
import android.annotation.SuppressLint
import android.os.Bundle
import android.app.AlertDialog
import android.content.Context
import android.content.SharedPreferences
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.datetime.Clock
import java.text.SimpleDateFormat
import java.util.Locale

data class Entry(val timestamp: String, val text: String)

class MainActivity : AppCompatActivity() {
    private lateinit var sharedPreferences: SharedPreferences
    private val entriesArray = ArrayDeque<Entry>()
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sharedPreferences = getSharedPreferences("PREF_DIARY", Context.MODE_PRIVATE)

        val editText = findViewById<EditText>(R.id.etNewWriting)
        val button = findViewById<Button>(R.id.btnSave)
        val diary = findViewById<TextView>(R.id.tvDiary)
        val undoButton = findViewById<Button>(R.id.btnUndo)
        // loading saved data at startup
        diary.text = loadSaveData()

        button.setOnClickListener {
            if (editText.text.contains(Regex("\\S"))) {
                addEntry(editText.text.toString())
                diary.text = getEntriesString()
                editText.text.clear()
            } else {
                val text = "Empty or blank input cannot be saved"
                Toast.makeText(applicationContext, text, Toast.LENGTH_SHORT).show()
            }
        }

        undoButton.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("Remove last note")
                .setMessage("Do you really want to remove the last writing? This operation cannot be undone!")
                .setPositiveButton("Yes") { _, _ ->
                    deleteEntry()
                    diary.text = getEntriesString()
                }
                .setNegativeButton("No", null)
                .show()
        }
    }

    private fun getTimestamp(): String {
        val date = Clock.System.now()
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        return dateFormat.format(date.toEpochMilliseconds())
    }

    private fun deleteEntry() {
        if (entriesArray.isNotEmpty()) entriesArray.removeFirst()
        updateSaveData()
    }

    private fun addEntry(msg: String) {
        val timestamp = getTimestamp()
        entriesArray.addFirst(Entry(timestamp, msg))
        updateSaveData()
    }

    private fun getEntriesString(): String {
        return buildString {
            entriesArray.forEachIndexed { i, msg ->
                append("${if (i != 0) "\n\n" else ""}${msg.timestamp}\n${msg.text}")
            }
        }
    }

    private fun updateSaveData() {
        val editor = sharedPreferences.edit()
        editor.putString("KEY_DIARY_TEXT", getEntriesString()).apply()
    }

    private fun loadSaveData(): String {
        sharedPreferences.getString("KEY_DIARY_TEXT", "")!!.split("\n\n")
            .forEach {
                if (it != "") {
                    val (timestamp, msg) = it.split("\n")
                    entriesArray.addLast(Entry(timestamp, msg))
                } else {
                    return ""
                }
            }
        return getEntriesString()
    }
}