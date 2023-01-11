package com.example.socialapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.socialapp.daos.PostDao
import com.example.socialapp.databinding.ActivityCreatePostBinding
import com.example.socialapp.databinding.ActivityMainBinding

class CreatePostActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCreatePostBinding
    private lateinit var postDao : PostDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreatePostBinding.inflate(layoutInflater)
        setContentView(binding.root)

        postDao = PostDao()

        binding.postButton.setOnClickListener {
            val input = binding.postInput.text.toString().trim()
            if (input.isNotEmpty()){
                postDao.addPost(input)
                Toast.makeText(this,"Post Created",Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }
}