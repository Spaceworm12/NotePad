package com.example.homework

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.homework.data.models.model.app.DbNotes
import com.example.homework.databinding.ActivityMainBinding
import com.example.homework.presentation.recycler.ListFragment

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
        setTheme(DbNotes.getSettingsTheme().getInt(THEME_CODE, R.style.Theme_Homework))

        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container, ListFragment())
                .commit()
        }

    }



}
