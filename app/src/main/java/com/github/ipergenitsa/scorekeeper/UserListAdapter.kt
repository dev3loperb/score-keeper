package com.github.ipergenitsa.scorekeeper

import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import android.content.Context
import android.text.InputType
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog


class UserListAdapter(context: Context, val layoutId: Int, val userList: List<User>)
    : ArrayAdapter<User>(context, layoutId, userList) {
    private val inflater = LayoutInflater.from(context)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView

        val viewHolder: ViewHolder
        if (convertView == null) {
            convertView = inflater.inflate(this.layoutId, parent, false)
            viewHolder = ViewHolder(convertView!!)
            convertView.tag = viewHolder
        } else {
            viewHolder = convertView.tag as ViewHolder
        }

        val user = userList[position]

        viewHolder.nameView.setText(user.name)
        viewHolder.countView.text = user.score.toString()

        viewHolder.addButton.setOnClickListener {addScore(context, user, viewHolder.countView)}

        return convertView
    }

    private fun addScore(context: Context, user: User, view: TextView) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Введите число баллов")

        val input = EditText(context)
        input.inputType = InputType.TYPE_CLASS_NUMBER
        builder.setView(input)

        builder.setPositiveButton("OK") { _, _ ->
            user.score = Integer.parseInt(view.text.toString()) + Integer.parseInt(input.text.toString())
            notifyDataSetChanged()
        }
        builder.setNegativeButton("Cancel") { dialog, _ -> dialog.cancel() }

        builder.show()
    }

    private inner class ViewHolder internal constructor(view: View) {
        internal val addButton: Button = view.findViewById(R.id.addScoreButton)
        internal val nameView: TextView = view.findViewById(R.id.userNameView)
        internal val countView: TextView = view.findViewById(R.id.userScoreView)
    }
}