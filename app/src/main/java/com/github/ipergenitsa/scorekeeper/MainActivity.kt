package com.github.ipergenitsa.scorekeeper

import android.os.Bundle
import android.text.InputType
import android.widget.EditText
import android.widget.ListView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.collections.ArrayList


class MainActivity : AppCompatActivity() {

    private val users: MutableList<User> = mutableListOf(
        User("Player 1", 0),
        User("Player 2", 0)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val lvMain = findViewById<ListView>(R.id.lvUsers)
        val adapter = UserListAdapter(this, R.layout.user_list_item, users)
        lvMain.adapter = adapter

        fab.setOnClickListener {addNewUser(adapter)}
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList("users", ArrayList(users))
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        val savedUsers = savedInstanceState.getParcelableArrayList<User>("users")
        users.clear()
        users.addAll(Optional.ofNullable(savedUsers).orElse(
            arrayListOf(User("Player 1", 0), User("Player 2", 0))
        ))
    }

    private fun addNewUser(adapter: UserListAdapter) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Title")

        val input = EditText(this)
        input.inputType = InputType.TYPE_CLASS_TEXT
        builder.setView(input)

        builder.setPositiveButton("OK") { _, _ ->
            run {
                users.add(User(input.text.toString(), 0))
                adapter.notifyDataSetChanged()
            }
        }
        builder.setNegativeButton("Cancel") { dialog, _ -> dialog.cancel() }

        builder.show()
    }
}
