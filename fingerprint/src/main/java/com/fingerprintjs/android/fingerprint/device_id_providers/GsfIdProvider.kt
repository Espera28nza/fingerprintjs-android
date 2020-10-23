package com.fingerprintjs.android.fingerprint.device_id_providers


import android.content.ContentResolver
import android.net.Uri


class GsfIdProvider(
    private val contentResolver: ContentResolver
) {
    fun getGsfAndroidId(): String? {
        val URI = Uri.parse(URI_GSF_CONTENT_PROVIDER)
        val params = arrayOf(ID_KEY)
        return try {
            val cursor = contentResolver
                .query(URI, null, null, params, null) ?: return null
            if (!cursor.moveToFirst() || cursor.columnCount < 2) {
                cursor.close()
                return null
            }
            try {
                val result = java.lang.Long.toHexString(cursor.getString(1).toLong())
                cursor.close()
                result
            } catch (e: NumberFormatException) {
                cursor.close()
                null
            }
        } catch (e: Exception) {
            null
        }
    }
}

private const val URI_GSF_CONTENT_PROVIDER = "content://com.google.android.gsf.gservices"
private const val ID_KEY = "android_id"