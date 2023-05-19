package com.ahrorovk.testmangofzco.core

import android.content.ContentUris
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.viewModelScope
import com.ahrorovk.testmangofzco.data.local.DataStoreManager
import com.ahrorovk.testmangofzco.presentation.ProfileScreen.ProfileEvent
import com.ahrorovk.testmangofzco.presentation.ProfileScreen.ProfileState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.io.File
import java.time.LocalDate
import java.time.ZoneId

fun checkPhone(phoneNumber:String):Boolean {
    var check = true
    for (i in phoneNumber.indices) {
        if (phoneNumber[i] < '0' || phoneNumber[i] > '9') {
            check = false
            break
        }
    }
    return check
}
fun checkUserName(userName:String):Boolean {
    var check = true
    for (i in userName.indices) {
        if ((userName[i] in 'a'..'z') || (userName[i] in 'A'..'Z') || userName[i] == '-' || userName[i] == '_' || userName[i]==' ') {
            continue
        } else {
            check = false
            break
        }
    }
    return check
}


@RequiresApi(Build.VERSION_CODES.O)
fun LocalDate.toCurrentInMillis(): Long {
    val zonedDateTime = this.atStartOfDay(ZoneId.systemDefault())
    return zonedDateTime.toInstant().toEpochMilli()
}
fun zodiacMonth(s:String):String {
    val bd = s.split('-').map { it.toInt() }
    val mm = bd[1]
    val dd = bd[2]
    val zodiac = if ((dd >= 21 && mm == 3) || (mm == 4 && dd <= 20)) "Овен"
    else if ((dd >= 21 && mm == 4) || (mm == 4 && dd <= 20)) "Телец"
    else if ((dd >= 21 && mm == 5) || (mm == 6 && dd <= 21)) "Близнецы"
    else if ((dd >= 22 && mm == 6) || (mm == 7 && dd <= 22)) "Рак"
    else if ((dd >= 23 && mm == 7) || (mm == 8 && dd <= 22)) "Лев"
    else if ((dd >= 23 && mm == 8) || (mm == 9 && dd <= 23)) "Дева"
    else if ((dd >= 24 && mm == 9) || (mm == 10 && dd <= 23)) "Весы"
    else if ((dd >= 24 && mm == 10) || (mm == 11 && dd <= 22)) "Скорпион"
    else if ((dd >= 23 && mm == 11) || (mm == 12 && dd <= 21)) "Стрелец"
    else if ((dd >= 22 && mm == 12) || (mm == 1 && dd <= 20)) "Козерог"
    else if ((dd >= 21 && mm == 1) || (mm == 2 && dd <= 18)) "Водолей"
    else "Рыбы"
    return zodiac
}

fun checkUpdatesInfo(typeOfItem:String,type:String,itemState:String,state:String):String {
    return if (typeOfItem == type) itemState else state
}

fun onInfoStateChange(_state:ProfileState,event: ProfileEvent.OnInfoStatesChange,dataStoreManager: DataStoreManager,viewModelScope:CoroutineScope){
    if (_state.name != event.name) {
        viewModelScope.launch(Dispatchers.IO) {
            event.name?.let { dataStoreManager.updateName(it) }
        }
    }
    if (_state.username != event.username) {
        viewModelScope.launch(Dispatchers.IO) {
            event.username?.let { dataStoreManager.updateUsername(it) }
        }
    }
    if (_state.avatar != event.avatar) {
        viewModelScope.launch(Dispatchers.IO) {
            event.avatar?.let { dataStoreManager.updateAvatar(it) }
        }
    }
    if (_state.birthday != event.birthday) {
        viewModelScope.launch(Dispatchers.IO) {
            event.birthday?.let { dataStoreManager.updateBirthday(it) }
        }
    }
    if (_state.city != event.city) {
        viewModelScope.launch(Dispatchers.IO) {
            event.city?.let { dataStoreManager.updateCity(it) }
        }
    }
    if (_state.instagram != event.instagram) {
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreManager.updateInstagram(event.instagram ?: "")
        }
    }
    if (_state.status != event.status) {
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreManager.updateStatus(event.status ?: "")
        }
    }
    if (_state.vk != event.vk) {
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreManager.updateVK(event.vk ?: "")
        }
    }
}


fun getRealPathFromURI(context: Context, uri: Uri): String? {
    when {
        // DocumentProvider
        DocumentsContract.isDocumentUri(context, uri) -> {
            when {
                // ExternalStorageProvider
                isExternalStorageDocument(uri) -> {
                    val docId = DocumentsContract.getDocumentId(uri)
                    val split = docId.split(":").toTypedArray()
                    val type = split[0]
                    // This is for checking Main Memory
                    return if ("primary".equals(type, ignoreCase = true)) {
                        if (split.size > 1) {
                            Environment.getExternalStorageDirectory().toString() + "/" + split[1]
                        } else {
                            Environment.getExternalStorageDirectory().toString() + "/"
                        }
                        // This is for checking SD Card
                    } else {
                        "storage" + "/" + docId.replace(":", "/")
                    }
                }
                isDownloadsDocument(uri) -> {
                    val fileName = getFilePath(context, uri)
                    if (fileName != null) {
                        return Environment.getExternalStorageDirectory().toString() + "/Download/" + fileName
                    }
                    var id = DocumentsContract.getDocumentId(uri)
                    if (id.startsWith("raw:")) {
                        id = id.replaceFirst("raw:".toRegex(), "")
                        val file = File(id)
                        if (file.exists()) return id
                    }
                    val contentUri = ContentUris.withAppendedId(Uri.parse("content://"), java.lang.Long.valueOf(id))
                    return getDataColumn(context, contentUri, null, null)
                }
                isMediaDocument(uri) -> {
                    val docId = DocumentsContract.getDocumentId(uri)
                    val split = docId.split(":").toTypedArray()
                    val type = split[0]
                    var contentUri: Uri? = null
                    when (type) {
                        "image" -> {
                            contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                        }
                        "video" -> {
                            contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI
                        }
                        "audio" -> {
                            contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
                        }
                    }
                    val selection = "_id=?"
                    val selectionArgs = arrayOf(split[1])
                    return getDataColumn(context, contentUri, selection, selectionArgs)
                }
            }
        }
        "content".equals(uri.scheme, ignoreCase = true) -> {
            // Return the remote address
            return if (isGooglePhotosUri(uri)) uri.lastPathSegment else getDataColumn(context, uri, null, null)
        }
        "file".equals(uri.scheme, ignoreCase = true) -> {
            return uri.path
        }
    }
    return null
}

fun getDataColumn(
    context: Context,
    uri: Uri?,
    selection: String?,
    selectionArgs: Array<String>?
): String? {
    var cursor: Cursor? = null
    val column = "_data"
    val projection = arrayOf(
        column
    )
    try {
        if (uri == null) return null
        cursor = context.contentResolver.query(
            uri, projection, selection, selectionArgs,
            null
        )
        if (cursor != null && cursor.moveToFirst()) {
            val index = cursor.getColumnIndexOrThrow(column)
            return cursor.getString(index)
        }
    } finally {
        cursor?.close()
    }
    return null
}

fun getFilePath(context: Context, uri: Uri?): String? {
    var cursor: Cursor? = null
    val projection = arrayOf(
        MediaStore.MediaColumns.DISPLAY_NAME
    )
    try {
        if (uri == null) return null
        cursor = context.contentResolver.query(
            uri, projection, null, null,
            null
        )
        if (cursor != null && cursor.moveToFirst()) {
            val index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DISPLAY_NAME)
            return cursor.getString(index)
        }
    } finally {
        cursor?.close()
    }
    return null
}

/**
 * @param uri The Uri to check.
 * @return Whether the Uri authority is ExternalStorageProvider.
 */
fun isExternalStorageDocument(uri: Uri): Boolean {
    return "com.android.externalstorage.documents" == uri.authority
}

/**
 * @param uri The Uri to check.
 * @return Whether the Uri authority is DownloadsProvider.
 */
fun isDownloadsDocument(uri: Uri): Boolean {
    return "com.android.providers.downloads.documents" == uri.authority
}

/**
 * @param uri The Uri to check.
 * @return Whether the Uri authority is MediaProvider.
 */
fun isMediaDocument(uri: Uri): Boolean {
    return "com.android.providers.media.documents" == uri.authority
}

/**
 * @param uri The Uri to check.
 * @return Whether the Uri authority is Google Photos.
 */
fun isGooglePhotosUri(uri: Uri): Boolean {
    return "com.google.android.apps.photos.content" == uri.authority
}