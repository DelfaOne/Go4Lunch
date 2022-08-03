package com.fadel.go4lunch.ui.workmates

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.fadel.go4lunch.data.usecase.GetWorkmatesUseCase
import com.fadel.go4lunch.utils.DispatcherProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class WorkmatesViewModel @Inject constructor(
    dispatcherProvider: DispatcherProvider,
    getWorkmatesUseCase: GetWorkmatesUseCase
) : ViewModel() {

    val workmatesListLiveData: LiveData<List<WorkmatesUiModel>> =
        getWorkmatesUseCase.invoke().map {
            it.map { workmate ->
                WorkmatesUiModel(
                    workmate.uid,
                    workmate.userName,
                    workmate.avatarUrl
                )
            }
        }
            .distinctUntilChanged()
            .flowOn(dispatcherProvider.ioDispatcher)
            .asLiveData()

}
