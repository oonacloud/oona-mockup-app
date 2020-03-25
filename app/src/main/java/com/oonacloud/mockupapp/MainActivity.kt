package com.oonacloud.mockupapp

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Browser
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.alert
import kotlin.system.exitProcess


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Package name
        val packageName = "com.oonacloud.checkoutapp"
        val urlWebPageName = "http://app.domaine.com/WebApp.html"

        // Click listener for button widget
        button.setOnClickListener{
            // Launch the app programmatically
            launchApp(packageName)
        }

        buttonClose.setOnClickListener {
            // Close
            finish();
            exitProcess(0);
        }

        buttonOpenWebApp.setOnClickListener {
            // Lunch the web app
            openUrlInChrome(urlWebPageName)
        }

    }


    // Custom method to launch an app
    private fun launchApp(packageName: String) {
        // Get an instance of PackageManager
        val pm = applicationContext.packageManager

        // Initialize a new Intent
        val intent:Intent? = pm.getLaunchIntentForPackage(packageName)

        // Add category to intent
        intent?.addCategory(Intent.CATEGORY_LAUNCHER)

        try {
            applicationContext.startActivity(intent)
        } catch (e: Exception) {
            // Application was just removed?
            alert("$packageName => startActivity(intent) : $e") {
                title = "Alert"
                yesButton { }
                noButton { }
            }.show()
        }
    }

    private fun openUrlInChrome(url: String) {
        try {
            try {
                val uri =
                    Uri.parse("googlechrome://navigate?url=$url")
                val i = Intent(Intent.ACTION_VIEW, uri)
                i.putExtra(Browser.EXTRA_APPLICATION_ID, "com.android.chrome")
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(i)
            } catch (e: ActivityNotFoundException) {
                val uri = Uri.parse(url)
                // Chrome is probably not installed
                // OR not selected as default browser OR if no Browser is selected as default browser
                val i = Intent(Intent.ACTION_VIEW, uri)
                startActivity(i)
            }
        } catch (ex: Exception) {
            //
        }
    }

}

// Extension function to show toast message
fun Context.toast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}