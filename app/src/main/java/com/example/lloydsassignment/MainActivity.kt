package com.example.lloydsassignment

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.appcompat.app.AlertDialog
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.lloydsassignment.core.NetworkConnectivityChecker
import com.example.lloydsassignment.presentation.nav_graph.Navigation
import com.example.lloydsassignment.presentation.news_list.NewsViewModel
import com.example.lloydsassignment.ui.theme.LloydsAssignmentTheme
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {

    private val networkConnectivityChecker: NetworkConnectivityChecker by inject()
    private val newsViewModel: NewsViewModel by viewModel()
    private var alertDialog: AlertDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LloydsAssignmentTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Navigation()
                }
            }
        }

        // Observe the LiveData for connectivity changes
        networkConnectivityChecker.isConnected.observe(this) { isConnected ->
            onNetworkConnectionChanged(isConnected)
        }
    }

    private fun onNetworkConnectionChanged(isConnected: Boolean) {
        if (isConnected) {
            alertDialog?.dismiss()
            newsViewModel.getNews()
        } else {
            showAlertDialog()
        }
    }

    private fun showAlertDialog() {
        if (alertDialog == null) {
            alertDialog = AlertDialog.Builder(this)
                .setTitle("No Internet Connection")
                .setMessage("Please check your internet connection and try again.")
                .setCancelable(false)
                .create()
        }
        alertDialog?.show()
    }

    override fun onDestroy() {
        super.onDestroy()
        networkConnectivityChecker.unregisterCallback()
        alertDialog?.dismiss()
    }
}