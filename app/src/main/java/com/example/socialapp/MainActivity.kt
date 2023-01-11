package com.example.socialapp

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.socialapp.daos.PostDao
import com.example.socialapp.daos.UserDao
import com.example.socialapp.databinding.ActivityMainBinding
import com.example.socialapp.models.Post
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.Query
import com.google.firebase.ktx.Firebase


class MainActivity : AppCompatActivity(), IPostAdapter {
    lateinit var binding : ActivityMainBinding
    private lateinit var postAdapter: PostAdapter
    private lateinit var postDao: PostDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.fab.setOnClickListener {
            val intent = Intent(this,CreatePostActivity::class.java)
            startActivity(intent)
        }

        setUpRecyclerView()
    }

    private fun setUpRecyclerView() {
        postDao = PostDao()
        val postsCollections = postDao.postCollection
        val query = postsCollections.orderBy("createdAt",Query.Direction.DESCENDING)
        val recyclerViewOptions = FirestoreRecyclerOptions.Builder<Post>().setQuery(query,Post::class.java).build()
        postAdapter = PostAdapter(recyclerViewOptions,this)

        binding.recyclerView.adapter = postAdapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
    }

    override fun onStart() {
        super.onStart()
        postAdapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        postAdapter.stopListening()
    }

    override fun onLikeClicked(postId: String) {
        postDao.updateLikes(postId)
    }

    //code for to create option menu
    override fun onCreateOptionsMenu(menu : Menu?): Boolean {
        if (menu != null) {
            menu.add("Logout")
        }
        return super.onCreateOptionsMenu(menu)
    }

    //code for perform click event on menu options
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.title=="Logout"){
            showDialog()
        }

        return super.onOptionsItemSelected(item)
    }


    private fun showDialog(){
        Utils.showAlert(
            this,
            getString(R.string.logout),
            getString(R.string.logout_desc),
            object : AlertClick{
                override fun yes() {
                    Firebase.auth.signOut()
                    Toast.makeText(this@MainActivity,"Logout Successfully",Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this@MainActivity,SignInActivity::class.java))
                    finish()
                }

                override fun no() {

                }

            })!!.show()
    }
}

