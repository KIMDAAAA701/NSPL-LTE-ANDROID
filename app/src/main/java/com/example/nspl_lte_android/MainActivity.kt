package com.example.nspl_lte_android

import android.Manifest
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.media.tv.TvContract.Programs.Genres.MUSIC
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.provider.Telephony
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.PackageManagerCompat
import java.io.File
import java.util.jar.Manifest as Manifest1

class MainActivity : AppCompatActivity() {

//    private val EXTERNAL_STORAGE_PATH = Environment.getExternalStorageDirectory().toString()
//    @RequiresApi(Build.VERSION_CODES.R)
//    private val STORAGE_PATH = Environment.getStorageDirectory().toString()

    val STORAGE_PATH = "\\"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var requestPermissions = arrayOf(Manifest.permission.READ_PHONE_STATE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        val status = ContextCompat.checkSelfPermission(this, requestPermissions.toString())
        if (status == PackageManager.PERMISSION_GRANTED){
            Log.d("nspl_call", "permission granted ${status} ${PackageManager.PERMISSION_GRANTED}")
        }else{
            Log.d("nspl_call", "permission denied ${status} ${PackageManager.PERMISSION_GRANTED}")
            ActivityCompat.requestPermissions(this, requestPermissions, 100)
        }

        Log.d("nspl_call", "MainActivity onCreate")

        findBooks()


    }// onCreate() end


    fun findBooks() {

//        var myList : ArrayList<File>? = null
        var path = Environment.getExternalStorageDirectory().absolutePath
        Log.d("kkang", path.toString())

        var rootSD = Environment.getExternalStorageDirectory().toString()
        Log.d("kkang", rootSD)
        var path2 = File(rootSD+"/Voice Recorder".toString())
        var file_list: Array<File>? = path2.listFiles()


//        Log.d("kkang", file_list.size.toString())

//        Log.d("kkang", file_list[0].toString())
        if (file_list != null) {
            for( f in file_list){
                Log.d("kkang", "<폴더> ${f}")
                //            if (f.isDirectory ){
                //                Log.d("kkang", "<폴더> ${f}")
                //            }
                //            else{
                //                Log.d("kkang", "<파일> ${f}")
                //            }
            }
        }
//
//        val storageDir: File? = getExternalFilesDir(Environment.DIRECTORY_MUSIC)
//        val file = File.createTempFile(
//            ".m4a",
//            storageDir.toString()
//        )
//
//        val filePath = file.absolutePath
//
//        val _URI: Uri = FileProvider.getUriForFile(
//            this, "com.example.test_appe", file
//        )
        val projection = arrayOf(
            MediaStore.Audio.Media._ID,
            MediaStore.Audio.Media.DISPLAY_NAME,
            MediaStore.Audio.Media.DURATION,
            MediaStore.Audio.Media.SIZE
        )
        val selection = null
        val selectionArgs = null
        val sortOrder = "${MediaStore.Audio.Media.DISPLAY_NAME} ASC"

        applicationContext.contentResolver.query(
            MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
            projection,
            selection,
            selectionArgs,
            sortOrder
        )?.use { cursor ->
            while (cursor.moveToNext()) {
                // Use an ID column from the projection to get
                // a URI representing the media item itself.
            }
        }

    } // findBooks() end

}// class end