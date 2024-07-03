package campus.tech.kakao.contacts

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class WhoAmIActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_who_am_iactivity)

        data class Contact(
            val name: String,
            val phone: String,
            val gender: String,
            val email: String,
            val message: String,
            val birthday: String
        )

        val contact = intent.getSerializableExtra("contact") as? Contact
        if (contact != null) {
            findViewById<TextView>(R.id.nameTextView).text = contact.toString()
            findViewById<TextView>(R.id.phoneTextView).text = contact.toString()
            findViewById<TextView>(R.id.emailTextView).text = contact.toString()
            findViewById<TextView>(R.id.BirTextView).text = contact.toString()
            findViewById<TextView>(R.id.memoTextView).text = contact.toString()
            findViewById<TextView>(R.id.genderTextView).text = contact.toString()
        }


        }
    }
