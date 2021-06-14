package com.example.lesson13

import android.app.AlertDialog
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MainAdapter(
    private val arrayList: ArrayList<Element>,
    private val onClick:(Element) -> Unit
) :
    RecyclerView.Adapter<MainAdapter.ViewHolder>() {
    class ViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        val title = item.findViewById<EditText>(R.id.title)!!
        val deleteBtn = item.findViewById<ImageButton>(R.id.delete_btn)!!
        private val plusBtn = item.findViewById<ImageButton>(R.id.plus_btn)!!
        private val currentNumber = item.findViewById<TextView>(R.id.current_number)!!
        private val minusBtn = item.findViewById<ImageButton>(R.id.minus_btn)!!

        fun bind(element: Element , onClick: (Element) -> Unit) {
            title.setText(element.title)
            currentNumber.text = element.currentCount.toString()
            plusBtn.setOnClickListener {
                element.currentCount++
                currentNumber.text = element.currentCount.toString()
            }
            minusBtn.setOnClickListener {
                element.currentCount--
                currentNumber.text = element.currentCount.toString()
            }
            title.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    element.title = title.text.toString()
                }

                override fun afterTextChanged(s: Editable?) {
                }
            })
            deleteBtn.setOnClickListener {
                onClick(element)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(arrayList[position], onClick)
    }

    override fun getItemCount(): Int = arrayList.size
}