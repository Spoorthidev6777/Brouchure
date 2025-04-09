package com.example.bonial_brochure.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bonial_brochure.data.Brochure
import com.example.bonial_brochure.repository.BrochureRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BrochureViewModel @Inject constructor(private val brochureRepository: BrochureRepository) : ViewModel() {
    private val _brochures = MutableLiveData<List<Brochure>>()
    val brochures: LiveData<List<Brochure>> = _brochures

    fun loadBrochures() {
        viewModelScope.launch {
            _brochures.value = brochureRepository.getFilteredBrochures()
        }
    }
}
