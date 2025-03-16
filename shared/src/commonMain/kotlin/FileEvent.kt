sealed class FileEvent {
    data class ShareFile(val fileUri: String) : FileEvent()
    object None : FileEvent()
}