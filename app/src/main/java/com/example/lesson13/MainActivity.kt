package com.example.lesson13

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.io.IOException
import java.io.ObjectInputStream
import java.io.ObjectOutputStream


class MainActivity : AppCompatActivity() {

    private lateinit var toolbar: Toolbar
    private lateinit var recyclerView: RecyclerView
    private var arrayList = arrayListOf<Element>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initAll()
    }

    private fun initAll() {
        toolbar = findViewById(R.id.main_toolbar)
        recyclerView = findViewById(R.id.recycler)
    }

    override fun onStart() {
        super.onStart()
        setToolBar()
        takeAllElementsFromFile()
    }

    private fun setToolBar() {
        setSupportActionBar(toolbar)
    }

    private fun takeAllElementsFromFile() {
        filesDir.listFiles().forEach { file ->
            if (file.name == "data") {
                val inputStream = openFileInput(file.name)
                val objInputStream = ObjectInputStream(inputStream)
                while (objInputStream.available() > 0) {
                    val title = objInputStream.readUTF()
                    val count = objInputStream.readInt()
                    arrayList.add(Element(title, count))
                }
                setRecycler()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_screen_toolbar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.save_btn -> saveInFile()
            R.id.add_btn -> createItemOfRecycle()
        }
        return true
    }

    private fun setRecycler() {
        with(recyclerView) {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = MainAdapter(arrayList,
                onClick = { showAlert(it) })
        }
    }

    private fun createItemOfRecycle() {
        arrayList.add(Element("", 0))
        setRecycler()
    }

    private fun saveInFile() {
        if (arrayList.isNotEmpty()) {
            try {
                val fileOutput = openFileOutput("data", MODE_PRIVATE)
                val objOutput = ObjectOutputStream(fileOutput)
                arrayList.forEach {
                    objOutput.writeUTF(it.title)
                    objOutput.writeInt(it.currentCount)
                }
                objOutput.close()
            } catch (e: IOException) {
                Toast.makeText(this, "HERNYA", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun showAlert(element: Element) {
        val builder = AlertDialog.Builder(this@MainActivity)
        builder.setPositiveButton("Yes") { _, _ ->
            arrayList.remove(element)
        }
        builder.setNegativeButton("No") { _, _ -> }
        builder.setTitle("Delete everything?")
        builder.setMessage("Are you sure you want to delete everything?")
        builder.show()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList("THIS_LIST_OF_ITEMS", arrayList)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        arrayList = savedInstanceState
            .getParcelableArrayList<Element>("THIS_LIST_OF_ITEMS") as ArrayList<Element>
        setRecycler()
    }
}