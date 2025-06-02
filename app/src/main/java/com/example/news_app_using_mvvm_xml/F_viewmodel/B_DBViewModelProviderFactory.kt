package com.example.news_app_using_mvvm_xml.F_viewmodel


    import androidx.lifecycle.ViewModel
    import androidx.lifecycle.ViewModelProvider
    import com.example.news_app_using_mvvm_xml.I_repository.B_Db_Repository

class B_DBViewModelProviderFactory(private val repository: B_Db_Repository)
        : ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(B_DB_Viewmodel::class.java)) {
                return B_DB_Viewmodel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
