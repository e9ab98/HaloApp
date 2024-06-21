package core

sealed class ProgressBarState{

   data object ProgressAlertLoading: ProgressBarState()

   data object ScreenLoading: ProgressBarState()

   data object FullScreenLoading: ProgressBarState()

   data object LoadingWithLogo: ProgressBarState()

   data object Idle: ProgressBarState()

}

