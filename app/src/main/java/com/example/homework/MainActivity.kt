package com.example.homework

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.example.homework.data.models.model.app.DbNotes
import com.example.homework.databinding.ActivityMainBinding
import com.example.homework.databinding.ThemeDialogBinding
import com.example.homework.presentation.recycler.ListFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder

private const val THEME_CODE = "THEME_CODE"

class MainActivity : AppCompatActivity() {
    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this)[MainViewModel::class.java]
    }
    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.viewStateObs.observe(this) { state ->
            Toast.makeText(this, state.correctCurrentTime, Toast.LENGTH_SHORT).show()
        }
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setTheme(DbNotes.getSettings().getInt(THEME_CODE, R.style.Theme_Homework))

        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container, ListFragment())
                .commit()
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.context_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.settings -> {
                showSettingsDialog()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
    private fun showSettingsDialog() {
        val bindingMissed =
            ThemeDialogBinding.inflate(LayoutInflater.from(this), null, false)

        val attentionDialog = MaterialAlertDialogBuilder(this)
            .setCustomTitle(bindingMissed.root)
            .setCancelable(true)
            .setBackground(ColorDrawable(ContextCompat.getColor(this, R.color.custom)))
            .create()

        bindingMissed.mainTheme.setOnClickListener {
            DbNotes.getSettings().edit().putInt(THEME_CODE, R.style.Theme_Homework).apply()
            attentionDialog.dismiss()
            recreate()
        }
        bindingMissed.secondTheme.setOnClickListener {
            DbNotes.getSettings().edit().putInt(THEME_CODE, R.style.Theme_Second).apply()
            attentionDialog.dismiss()
            recreate()
        }

        attentionDialog.show()
    }

}
