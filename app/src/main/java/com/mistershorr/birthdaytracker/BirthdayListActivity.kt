package com.mistershorr.birthdaytracker

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.mistershorr.birthdaytracker.databinding.ActivityBirthdayListBinding
import com.backendless.exceptions.BackendlessFault

import com.backendless.async.callback.AsyncCallback

import com.backendless.Backendless
import com.backendless.persistence.DataQueryBuilder

class BirthdayListActivity : AppCompatActivity() {

    lateinit var binding : ActivityBirthdayListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBirthdayListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // make an onclicklistener for the floating action button
        // that launches the detailActivity
        binding.floatingActionButton.setOnClickListener {
            // make the intent
            val detailIntent = Intent(this, BirthdayDetailActivity::class.java)
            // launch the activity
            startActivity(detailIntent)
        }
    }

    override fun onStart() {
        super.onStart()

        // load the data from the database
        // put it into the recyclerView
        // this is in onStart so when we return from the detail view
        // it will reload the data from the database
        loadDataFromBackendless()
    }

    private fun loadDataFromBackendless() {

        // retrieve only objects shoe ownerId matches the user's
        val objectId = Backendless.UserService.CurrentUser().objectId
        val whereClause = "ownerId = '$objectId'"
        val queryBuilder = DataQueryBuilder.create()
        queryBuilder.whereClause = whereClause

        // this retrieves all objects regardless of owner
        // but adding the builder will now serach with the where clause
        Backendless.Data.of(Person::class.java).find(queryBuilder, object : AsyncCallback<List<Person>?> {
            override fun handleResponse(foundPeople: List<Person>?) {
                // all person instances have been found
                Log.d("BirthdayList", "handleResponse: ${foundPeople}")
                val adapter = BirthdayAdapter((foundPeople ?: listOf<Person>()))
                binding.recyclerViewBirthdayListPeople.adapter = adapter
                binding.recyclerViewBirthdayListPeople.layoutManager =
                    LinearLayoutManager(this@BirthdayListActivity)
            }

            override fun handleFault(fault: BackendlessFault) {
                // an error has occurred, the error code can be retrieved with fault.getCode()
                Log.d("BirthdayList", "handleFault: ${fault.message}")
            }
        })
    }
}