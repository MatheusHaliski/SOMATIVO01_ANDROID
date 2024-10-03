package com.example.myinitactiv1
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class FourthViewModelFactory(private val appDatabase: AppDatabase) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FourthViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return FourthViewModel(appDatabase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}