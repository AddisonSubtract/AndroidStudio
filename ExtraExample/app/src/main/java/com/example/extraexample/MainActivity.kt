package com.example.extraexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var handler = Handler()
        var fab = findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener(handler)

    }
    inner class Handler : View.OnClickListener
    {
        override fun onClick(v: View?)
        {
            println("Fab clicked")

            var v = findViewById<View>(R.id.cl)
            var sb = Snackbar.make(v,"Replace with own message", Snackbar.LENGTH_LONG)

            sb.setAction("Undo",Handler())
            sb.show()

            val text = "Hello toast!"
            val duration = Toast.LENGTH_SHORT
            val toast = Toast.makeText(this, text, duration)
            toast.show()
        }
    }
}