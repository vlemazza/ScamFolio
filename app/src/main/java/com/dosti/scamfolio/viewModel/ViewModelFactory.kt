package com.dosti.scamfolio.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dosti.scamfolio.SharedPrefRepository
import com.dosti.scamfolio.db.repositories.Repository

@Suppress("UNCHECKED_CAST")
class ViewModelFactory(private val repository: Repository,
                       private val sharedCoinGeko : SharedCoinGekoViewModel,
                       private val sharedPrefRepository: SharedPrefRepository) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        if(modelClass.isAssignableFrom(LoginViewModel::class.java))
                LoginViewModel(repository) as T
        else if( modelClass.isAssignableFrom(CryptoScreenViewModel::class.java))
            CryptoScreenViewModel(repository, sharedPrefRepository) as T
        else if( modelClass.isAssignableFrom(HomepageViewModel::class.java))
            HomepageViewModel(repository, sharedPrefRepository) as T
        else if( modelClass.isAssignableFrom(ConverterViewModel::class.java))
            ConverterViewModel(sharedCoinGeko) as T
        else if(modelClass.isAssignableFrom(SplashScreenViewModel::class.java))
            SplashScreenViewModel(repository,sharedCoinGeko) as T
        else
            SearchedCryptosViewModel(repository, sharedCoinGeko, sharedPrefRepository) as T
}