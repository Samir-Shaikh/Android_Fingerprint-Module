package com.zero.fingerprint

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.hardware.fingerprint.FingerprintManager
import android.os.CancellationSignal
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat

class FingerprintHandler(private val appContext: Context, private val authStatus: TextView) : FingerprintManager.AuthenticationCallback() {

    private var cancellationSignal: CancellationSignal? = null

    fun startAuth(manager: FingerprintManager, cryptoObject: FingerprintManager.CryptoObject) {

        cancellationSignal = CancellationSignal()

        if (ActivityCompat.checkSelfPermission(appContext, Manifest.permission.USE_FINGERPRINT) != PackageManager.PERMISSION_GRANTED)
            return
        manager.authenticate(cryptoObject, cancellationSignal, 0, this, null)
    }

    override fun onAuthenticationFailed() {
        authStatus.text = appContext.getString(R.string.not_recognized)
        authStatus.setTextColor(appContext.getColor(R.color.Red))
    }

    override fun onAuthenticationSucceeded(result: FingerprintManager.AuthenticationResult) {
        val intent = Intent(appContext, SuccessActivity::class.java)
        appContext.startActivity(intent)
    }
}