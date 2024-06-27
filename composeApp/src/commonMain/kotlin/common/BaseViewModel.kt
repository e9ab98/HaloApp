package common

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import core.CommonUiState
import core.NetworkState
import core.ProgressBarState
import core.Queue
import core.UIComponent
import di.CUSTOM_TAG


abstract class BaseViewModel : ViewModel() {
    val stateBase: MutableState<CommonUiState> = mutableStateOf(CommonUiState())

    fun setProgressBarState(progressState: ProgressBarState){
        stateBase.value = stateBase.value.copy(progressBarState = progressState)

    }
    fun appendToMessageQueue(uiComponent: UIComponent) {
        if (uiComponent is UIComponent.None) {
            println("$CUSTOM_TAG: onTriggerEvent:  ${uiComponent.message}")
            return
        }
        val queue = stateBase.value.errorQueue
        queue.add(uiComponent)
        stateBase.value = stateBase.value.copy(errorQueue = Queue(mutableListOf())) // force recompose
        stateBase.value = stateBase.value.copy(errorQueue = queue)
    }

    fun removeHeadMessage() {
        try {
            val queue = stateBase.value.errorQueue
            queue.remove() // can throw exception if empty
            stateBase.value =
                stateBase.value.copy(errorQueue = Queue(mutableListOf())) // force recompose
            stateBase.value = stateBase.value.copy(errorQueue = queue)
        } catch (e: Exception) {
            println("$CUSTOM_TAG: removeHeadMessage: Nothing to remove from DialogQueue")
        }
    }
    fun onUpdateNetworkState(networkState: NetworkState) {
        stateBase.value = stateBase.value.copy(networkState = networkState)
    }
}