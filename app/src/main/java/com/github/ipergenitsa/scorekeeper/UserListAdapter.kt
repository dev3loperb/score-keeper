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


class UserListAdapter(
    private val layoutId: Int,
    private val userList: List<User>,
    context: Context
) : ArrayAdapter<User>(context, layoutId, userList) {

    private val inflater = LayoutInflater.from(context)

    override fun getView(position: Int, convertViewArg: View?, parent: ViewGroup): View {
        val viewHolder: ViewHolder
        val convertView = if (convertViewArg == null) {
            val inflated = inflater.inflate(this.layoutId, parent, false)
            viewHolder = ViewHolder(inflated)
            inflated.tag = viewHolder
            inflated
        } else {
            viewHolder = convertViewArg.tag as ViewHolder
            convertViewArg
        }

        val user = userList[position]

        viewHolder.nameView.text = user.name
        viewHolder.countView.setText(user.score)

        viewHolder.addButton.setOnClickListener {addScore(context, user, viewHolder.countView)}
        viewHolder.subtractButton.setOnClickListener {subtractScore(context, user, viewHolder.countView)}

        return convertView
    }

    private fun addScore(context: Context, user: User, view: TextView) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Enter score to add")

        val input = EditText(context)
        input.inputType = InputType.TYPE_CLASS_NUMBER
        builder.setView(input)

        builder.setPositiveButton("Add") { _, _ ->
            user.score = Integer.parseInt(view.text.toString()) + Integer.parseInt(input.text.toString())
            notifyDataSetChanged()
        }
        builder.setNegativeButton("Cancel") { dialog, _ -> dialog.cancel() }

        builder.show()
    }

    private fun subtractScore(context: Context, user: User, view: TextView) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Enter score to subtract")

        val input = EditText(context)
        input.inputType = InputType.TYPE_CLASS_NUMBER
        builder.setView(input)

        builder.setPositiveButton("Subtract") { _, _ ->
            user.score = Integer.parseInt(view.text.toString()) - Integer.parseInt(input.text.toString())
            notifyDataSetChanged()
        }
        builder.setNegativeButton("Cancel") { dialog, _ -> dialog.cancel() }

        builder.show()
    }

    private inner class ViewHolder internal constructor(view: View) {
        internal val addButton: Button = view.findViewById(R.id.addScoreButton)
        internal val subtractButton: Button = view.findViewById(R.id.subtractScoreButton)
        internal val nameView: TextView = view.findViewById(R.id.userNameView)
        internal val countView: TextView = view.findViewById(R.id.userScoreView)
    }
}