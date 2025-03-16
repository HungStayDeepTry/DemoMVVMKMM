package com.example.demomvvmkmm.android

import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.demomvvmkmm.Greeting
import com.example.demomvvmkmm.MyScreen
import com.example.demomvvmkmm.MyViewModel
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI

class MainActivity : ComponentActivity() {
    private val openFileLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                // Có thể cập nhật trạng thái nếu cần
                println("Người dùng đã mở file xong và quay lại")
            }
        }
//    private val shareFileReceiver = object : BroadcastReceiver() {
//        @RequiresApi(Build.VERSION_CODES.TIRAMISU)
//        override fun onReceive(context: Context?, intent: Intent?) {
//            val fileUri: Uri? = intent?.getParcelableExtra("fileUri", Uri::class.java)
//            fileUri?.let {
//                shareFile(it)  // Thực hiện chia sẻ file
//            }
//        }
//    }
    @OptIn(KoinExperimentalAPI::class)
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        val intentFilter = IntentFilter("com.example.demomvvmkmm.SHARE_FILE")
//        LocalBroadcastManager.getInstance(this).registerReceiver(shareFileReceiver, intentFilter)
        setContent {
            val myVM = koinViewModel<MyViewModel>()
            val fileUri = myVM.fileUriFlow.collectAsState(initial = null).value

            LaunchedEffect(fileUri) {
                if(!fileUri.isNullOrEmpty()){
//                    shareFile(Uri.parse(fileUri))
                    openFile(Uri.parse(fileUri),"text/plain")
                }
            // 🔹 Chia sẻ file nếu có
            }
            MyApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MyScreen()
                }
            }
        }
    }
    private fun shareFile(fileUri: Uri) {
        val shareIntent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_STREAM, fileUri)
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }
        startActivity(Intent.createChooser(shareIntent, "Chia sẻ qua"))
    }

    override fun onDestroy() {
        super.onDestroy()
//        unregisterReceiver(shareFileReceiver)
    }
    fun openFile(fileUri: Uri, mimeType: String) {
        val openIntent = Intent(Intent.ACTION_VIEW).apply {
            setDataAndType(fileUri, mimeType) // Xác định URI & loại file
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION) // Cho phép đọc file
        }
        val chooser = Intent.createChooser(openIntent, "Chọn ứng dụng để mở")
        openFileLauncher.launch(chooser)
    }
}

@Composable
fun GreetingView(text: String) {
    Text(text = text)
}

@Preview
@Composable
fun DefaultPreview() {
    MyApplicationTheme {
        GreetingView("Hello, Android!")
    }
}
