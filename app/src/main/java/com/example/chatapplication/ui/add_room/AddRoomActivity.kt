package com.example.chatapplication.ui.add_room

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import androidx.lifecycle.ViewModelProvider
import com.example.chatapplication.R
import com.example.chatapplication.base.BaseActivity
import com.example.chatapplication.databinding.ActivityAddRoomBinding
import com.example.chatapplication.ui.add_room.RoomCategory.Companion.getCategories
import com.example.chatapplication.ui.home.HomeActivity


class AddRoomActivity : BaseActivity<ActivityAddRoomBinding, AddRoomViewModel>(), Navigator {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding.vm = viewModel
        viewModel.navigator = this
        initializeSpinner()
        viewBinding.toolBar.arrowBack.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }

    }

    lateinit var adapter: CategoriesAdapter
    fun initializeSpinner() {
        adapter = CategoriesAdapter(getCategories())
        viewBinding.categorySpinner.adapter = adapter
        viewBinding.categorySpinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parentView: AdapterView<*>?,
                    itemView: View?,
                    position: Int,
                    itemId: Long
                ) {
                    val selectedCategory = adapter.getItem(position) as RoomCategory
                    //        val selectedCategory = getCategories()[position]
                    viewModel.selectedCategory = selectedCategory


                }

                override fun onNothingSelected(p0: AdapterView<*>?) {


                }
            }


    }

    override fun generateViewModel(): AddRoomViewModel {
        return ViewModelProvider(this)[AddRoomViewModel::class.java]
    }

    override fun getLayoutId(): Int = R.layout.activity_add_room
    override fun goBack() {
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
        finish()
    }
}