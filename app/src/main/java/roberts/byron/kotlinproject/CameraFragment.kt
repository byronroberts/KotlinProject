package roberts.byron.kotlinproject

import android.app.Fragment
import android.hardware.Camera
import android.media.ExifInterface
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE
import android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO
import android.support.design.widget.Snackbar
import android.util.Log
import android.view.LayoutInflater
import android.view.SurfaceHolder
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_camera.*
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


class CameraFragment : Fragment(), SurfaceHolder.Callback {
    private var camera: Camera? = null
    private lateinit var surfaceHolder: SurfaceHolder
    private var previewing = false
    private var params: Camera.Parameters? = null
    private var flashEnabled = false

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_camera, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        camera?.stopPreview()
        camera?.release()
        surfaceHolder = camera_preview.holder
        surfaceHolder.addCallback(this)
        surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS)
        flash_status.text = resources.getString(R.string.flash_status, "Off")

        button_capture.setOnClickListener {
            camera?.takePicture(null, null, pictureCallback)
        }

        flash.setOnClickListener {
            if (flashEnabled) {
                params?.flashMode = Camera.Parameters.FLASH_MODE_OFF
                flashEnabled = false
                flash_status.text = resources.getString(R.string.flash_status, "Off")
            } else {
                params?.flashMode = Camera.Parameters.FLASH_MODE_ON
                flashEnabled = true
                flash_status.text = resources.getString(R.string.flash_status, "On")
            }
            camera?.parameters = params
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        camera?.stopPreview()
        camera?.release()
    }

    override fun surfaceChanged(holder: SurfaceHolder?, format: Int, width: Int, height: Int) {
        if (previewing) {
            camera?.stopPreview()
            previewing = false
        }

        try {
            camera?.setPreviewDisplay(surfaceHolder)
            camera?.startPreview()
            camera?.setDisplayOrientation(90)
            previewing = true
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    override fun surfaceDestroyed(holder: SurfaceHolder?) {
        camera?.stopPreview()
        camera?.release()
        camera = null
        previewing = false
    }

    override fun surfaceCreated(holder: SurfaceHolder?) {
        camera = Camera.open()
        params = camera?.parameters
        params?.focusMode = Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE
        params?.set("orientation", "portrait")
        camera?.parameters = params
    }

    private val pictureCallback = Camera.PictureCallback { data: ByteArray?, camera: Camera? ->
        val pictureFile = getOutputMediaFile(MEDIA_TYPE_IMAGE)
        pictureFile?.let {
            try {
                val fos = FileOutputStream(it)
                fos.write(data)
                fos.close()
                camera?.startPreview()
                Snackbar.make(view, "Photo taken", Snackbar.LENGTH_SHORT)
            } catch (e: FileNotFoundException) {
            } catch (e: IOException) {
            }
        }

    }

    /** Create a File for saving an image or video  */
    private fun getOutputMediaFile(type: Int): File? {
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.

        val mediaStorageDir = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toURI())
        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d("KotlinProject", "failed to create directory")
                return null
            }
        }

        // Create a media file name
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val mediaFile: File
        mediaFile = when (type) {
            MEDIA_TYPE_IMAGE -> File(mediaStorageDir.path + File.separator +
                    "IMG_" + timeStamp + ".jpg")
            MEDIA_TYPE_VIDEO -> File(mediaStorageDir.path + File.separator +
                    "VID_" + timeStamp + ".mp4")
            else -> return null
        }

        return mediaFile
    }
}