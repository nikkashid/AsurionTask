package com.nikhil.asuriontask.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.nikhil.asuriontask.R
import com.nikhil.asuriontask.databinding.FragmentHomeBinding
import com.nikhil.asuriontask.entities.PetsEntity
import com.nikhil.asuriontask.ui.adapters.PetsListAdapter
import com.nikhil.asuriontask.viewmodel.HomeViewModel
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONObject
import java.util.*
import kotlin.collections.ArrayList

class HomeFragment : Fragment(R.layout.fragment_home), PetsListAdapter.IClickListener {

    private val TAG = "HomeFragment"

    private lateinit var homeFragment: FragmentHomeBinding

    //Hilt ViewModel Injection
    private val homeViewModel: HomeViewModel by activityViewModels<HomeViewModel>()

    lateinit var navController: NavController

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        homeFragment = FragmentHomeBinding.bind(view)

        navController = Navigation.findNavController(view)

        lifecycleScope.launch {
            val configInfo = homeViewModel.getConfigInfo("config")
            Log.d(TAG, "onViewCreated: $configInfo")
            setConfigData(configInfo)
        }

        lifecycleScope.launch {
            val petsList = homeViewModel.getConfigInfo("pets")
            Log.d(TAG, "onViewCreated: $petsList")
            convertDataToList(petsList)
        }

        homeFragment.ibChat.setOnClickListener {
            if (checkTimeSlot()) {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.within_working_hours),
                    Toast.LENGTH_SHORT
                )
                    .show()
            } else {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.working_hours_completed),
                    Toast.LENGTH_SHORT
                )
                    .show()
            }
        }

        homeFragment.ibCall.setOnClickListener { homeFragment.ibChat.performClick() }
    }

    private fun setConfigData(configInfo: String?) {
        if (configInfo!!.isNotEmpty()) {
            try {
                val jsonObject: JSONObject = JSONObject(configInfo)

                when (jsonObject.getBoolean("isChatEnabled")) {
                    false -> homeFragment.ibChat.visibility = View.GONE
                }

                when (jsonObject.getBoolean("isCallEnabled")) {
                    false -> homeFragment.ibCall.visibility = View.GONE
                }

                homeFragment.tvTime.text =
                    getString(R.string.working_hours_time) + jsonObject.getString("workHours")

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun convertDataToList(petsList: String?) {
        if (petsList!!.isNotEmpty()) {
            var petsListJsonArray: JSONArray = JSONArray(petsList)
            var petEntityList: ArrayList<PetsEntity> = ArrayList()
            for (i in 0 until petsListJsonArray.length()) {
                val innerObject: JSONObject = petsListJsonArray.getJSONObject(i)
                var petEntity = PetsEntity(
                    innerObject.getString("content_url"),
                    innerObject.getString("date_added"),
                    innerObject.getString("image_url"),
                    innerObject.getString("title")
                )
                petEntityList.add(petEntity)
            }
            setupAdapter(petEntityList)
        }
    }

    private fun setupAdapter(petsList: List<PetsEntity>) {
        if (petsList.isNotEmpty()) {
            val petsListAdapter = PetsListAdapter(this)
            homeFragment.rvPetsList.layoutManager = LinearLayoutManager(requireContext())
            homeFragment.rvPetsList.hasFixedSize()
            homeFragment.rvPetsList.adapter = petsListAdapter
            petsListAdapter.submitList(petsList)
        }
    }

    override fun onClickListener(petsEntity: PetsEntity) {
        var action = HomeFragmentDirections.actionHomeFragmentToDetailsFragment(petsEntity)
        navController.navigate(action)
    }

    private fun checkTimeSlot(): Boolean {
        val cal = Calendar.getInstance()
        cal.time = Date()
        val hour = cal[Calendar.HOUR_OF_DAY]
        return hour in 9..18
    }
}