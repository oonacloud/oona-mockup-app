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
        val packageName = "com.oonacloud.checkoutapp"
        val urlWebPageName = "http://app.domaine.com/WebApp.html"

        button.setOnClickListener{
            launchApp(packageName)
        }

        buttonClose.setOnClickListener {
            finish()
            exitProcess(0)
        }

        buttonOpenWebApp.setOnClickListener {
            openUrlInChrome(urlWebPageName)
        }
    }

    private fun launchApp(packageName: String) {
        val pm = applicationContext.packageManager
        val intent:Intent? = pm.getLaunchIntentForPackage(packageName)
        intent?.addCategory(Intent.CATEGORY_LAUNCHER)
        try {
            applicationContext.startActivity(intent)
        } catch (e: Exception) {
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
                val i = Intent(Intent.ACTION_VIEW, uri)
                startActivity(i)
            }
        } catch (e: Exception) {
            alert("openUrlInChrome error : $e") {
                title = "Alert"
                yesButton { }
                noButton { }
            }.show()
        }
    }
}
