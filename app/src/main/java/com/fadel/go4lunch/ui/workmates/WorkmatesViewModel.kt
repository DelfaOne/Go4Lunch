package com.fadel.go4lunch.ui.workmates

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.fadel.go4lunch.domain.workmates.usecase.GetWorkmatesUseCase
import com.fadel.go4lunch.utils.DispatcherProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class WorkmatesViewModel @Inject constructor(
    dispatcherProvider: DispatcherProvider,
    getWorkmatesUseCase: GetWorkmatesUseCase,
    workmateUiModelConverter: WorkmateUiModelConverter
) : ViewModel() {

    val workmatesListLiveData: LiveData<List<WorkmatesUiModel>> =
        getWorkmatesUseCase.invoke()
            .distinctUntilChanged()
            .map {
                it.map { workmate ->
                    workmateUiModelConverter.convertWorkmate(workmate)
                }
            }
            .flowOn(dispatcherProvider.ioDispatcher)
            .asLiveData()

}
