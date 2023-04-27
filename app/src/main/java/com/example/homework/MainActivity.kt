package com.example.homework

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.homework.databinding.ActivityMainBinding
import com.example.homework.fragments.PreviewFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) apply {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container, PreviewFragment())
                .commit()
        }

    }

}
