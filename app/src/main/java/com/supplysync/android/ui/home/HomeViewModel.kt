package com.supplysync.android.ui.home

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.supplysync.android.network.RetrofitInstance
import kotlinx.coroutines.launch

class HomeViewModel(application: Application) : AndroidViewModel(application) {
    //warehouse
    private val _warehousename = MutableLiveData<String>()
    val warehousename : LiveData<String> = _warehousename

    private val _percentagefilled = MutableLiveData<Float>()
    val percentagefilled : LiveData<Float> = _percentagefilled

    //section
    private val _total_empty_sections = MutableLiveData<Int>()
    val total_empty_sections : LiveData<Int> = _total_empty_sections

    private val _total_filled_sections = MutableLiveData<Int>()
    val total_filled_sections : LiveData<Int> = _total_filled_sections

    private val _percent_section_filled = MutableLiveData<Float>()
    val percent_section_filled : LiveData<Float> = _percent_section_filled

    private val _sectionBarGraph = MutableLiveData<List<FilledsizeSectionidResponse>>()
    val sectionBarGraph : LiveData<List<FilledsizeSectionidResponse>> = _sectionBarGraph

    //total
    private val _total_sections = MutableLiveData<Int>()
    val total_sections : LiveData<Int> = _total_sections

    private val _total_racks = MutableLiveData<Int>()
    val total_racks : LiveData<Int> = _total_racks

    //warehouse
    fun getWarehouseData(warehouse_id: String){
        viewModelScope.launch {
            try{
                val context = getApplication<Application>().applicationContext

                val response = RetrofitInstance.getApiService(context).getWarehouseDetails(warehouse_id)

                if (response.isSuccessful){
                    _warehousename.value = response.body()?.warehouse_name
                    _percentagefilled.value = response.body()?.percent_filled

                    _total_sections.value = response.body()?.total_sections
                    _total_racks.value = response.body()?.total_racks

                }
            }
            catch (e: Exception){
                Log.i("err", e.toString())
                _warehousename.value = "error"
                _percentagefilled.value = 0.0f
            }
        }
    }

    //section
    fun getSectionData(warehouse_id: String){
        viewModelScope.launch {
            try{
                val context = getApplication<Application>().applicationContext
                val response = RetrofitInstance.getApiService(context).getSectionDetails(warehouse_id)

                if (response.isSuccessful){
                    _total_empty_sections.value = response.body()?.total_empty_sections
                    _total_filled_sections.value = response.body()?.total_filled_sections
                    _percent_section_filled.value = response.body()?.percent_section_filled
                }

                val response2 = RetrofitInstance.getApiService(context).getfilledsizesectionid(warehouse_id)

                //bar graph
                if(response2.isSuccessful){
                    _sectionBarGraph.value = response2.body()


                }
            }
            catch (e: Exception){
                Log.i("err", e.toString())
                _total_empty_sections.value = 0
                _total_filled_sections.value = 0
            }
        }
    }

}