package be.norio.virtualfilepicker

import android.annotation.SuppressLint
import android.app.Activity
import android.content.ContentResolver
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private val requestGoogleDrawing = 42
    lateinit var logTextView: TextView;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        logTextView = findViewById<Button>(R.id.logTextView)
        logTextView.movementMethod = ScrollingMovementMethod()
        findViewById<Button>(R.id.pickFileButton).setOnClickListener {
            val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
            // intent.type = "application/vnd.google-apps.drawing"
            intent.type = "application/*"
            log("------------------------------\nShowing file picker for mime type ${intent.type}")
            startActivityForResult(intent, requestGoogleDrawing)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun log(s: String) {
        logTextView.text = "${logTextView.text}\n\n$s"
        println(s)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && data != null) {
            if (requestCode == requestGoogleDrawing) {
                if (ContentResolver.SCHEME_CONTENT == data.data!!.scheme) {
                    processGoogleDrawing(data.data!!)
                }
            }
        } else {
            log("File picker was cancelled")
        }
    }

    private fun processGoogleDrawing(uri: Uri) {
        try {
            log("Trying to load Google drawing at $uri")
            val streamTypes = contentResolver.getStreamTypes(uri, "*/*").orEmpty()
            log("getStreamTypes() returned possible mime types: ${streamTypes.joinToString(" ")}")

            log("Trying openTypedAssetFileDescriptor() with mime type ${streamTypes[0]}")
            contentResolver.openTypedAssetFileDescriptor(uri, streamTypes[0], null)?.let {
                val inputStream = it.createInputStream()
                log("Inputstream opened succesfully")
                inputStream.close()
            }
        } catch (e: Exception) {
            log("ERROR: ${e.message}")
            log("¯\\_(ツ)_/¯")
            e.printStackTrace()
        }
    }
}
