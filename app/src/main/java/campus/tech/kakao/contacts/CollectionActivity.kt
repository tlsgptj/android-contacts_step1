package campus.tech.kakao.contacts

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class CollectionActivity : AppCompatActivity() {
    private lateinit var messageTextView: TextView
    private lateinit var recyclerView: RecyclerView
    private lateinit var contactAdapter: ContactAdapter
    private val addButton: Button by lazy { findViewById(R.id.addbutton) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_collection)
        setupWindowInsets()
        messageTextView = findViewById(R.id.message)
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        loadContacts()
        showMessage()

        addButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun showMessage() {
        messageTextView.visibility = if (getContacts().isEmpty()) View.VISIBLE else View.GONE
    }

    private fun loadContacts() {
        val contacts = getContacts()
        contactAdapter = ContactAdapter(contacts)
        recyclerView.adapter = contactAdapter
    }

    private fun getContacts(): List<Contact> {
        val sharedPreferences = getSharedPreferences("contacts", Context.MODE_PRIVATE)
        val contacts = mutableListOf<Contact>()

        val nameSet = sharedPreferences.getStringSet("names", emptySet())
        val phoneSet = sharedPreferences.getStringSet("phones", emptySet())
        val genderSet = sharedPreferences.getStringSet("genders", emptySet())
        val emailSet = sharedPreferences.getStringSet("emails", emptySet())
        val noteSet = sharedPreferences.getStringSet("notes", emptySet())
        val birthdaySet = sharedPreferences.getStringSet("birthdays", emptySet())

        nameSet?.forEach { name ->
            val index = nameSet.indexOf(name)
            contacts.add(
                Contact(
                    name,
                    phoneSet?.elementAtOrNull(index) ?: "",
                    genderSet?.elementAtOrNull(index) ?: "",
                    emailSet?.elementAtOrNull(index) ?: "",
                    noteSet?.elementAtOrNull(index) ?: "",
                    birthdaySet?.elementAtOrNull(index) ?: ""
                )
            )
        }

        val nameEditText: EditText = findViewById(R.id.name)
        val phoneEditText: EditText = findViewById(R.id.phone)
        val newContact = Contact(
            nameEditText.text.toString(),
            phoneEditText.text.toString(),
            "", // Gender
            "", // Email
            "", // Note
            "" // Birthday
        )
        contacts.add(newContact)

        val editor = sharedPreferences.edit()
        editor.putStringSet("names", contacts.map { it.name }.toMutableSet())
        editor.putStringSet("phones", contacts.map { it.phone }.toMutableSet())
        editor.putStringSet("genders", contacts.map { it.gender }.toMutableSet())
        editor.putStringSet("emails", contacts.map { it.email }.toMutableSet())
        editor.putStringSet("notes", contacts.map { it.message }.toMutableSet())
        editor.putStringSet("birthdays", contacts.map { it.birthday }.toMutableSet())
        editor.apply()

        contactAdapter.notifyDataSetChanged()
        return contacts
    }

    private fun enableEdgeToEdge() {
        // Implement edge-to-edge functionality
    }

    private fun setupWindowInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}