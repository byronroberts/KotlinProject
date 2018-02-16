package roberts.byron.kotlinproject

import android.app.Fragment
import android.content.CursorLoader
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_image_picker.*
import kotlinx.android.synthetic.main.fragment_to_do.*


class ImagePickerFragment : Fragment() {

    private var count: Int = 0

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_image_picker, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        val adapter = ImagePickerRecyclerViewAdapter()
        images_recycler_view.layoutManager = GridLayoutManager(context,  3)
        images_recycler_view.adapter = adapter

//        ActivityCompat.requestPermissions(activity, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
//                1234)

        loadImages()
    }

    private fun loadImages() {
        val adapter = ImagePickerRecyclerViewAdapter()
        images_recycler_view.layoutManager = GridLayoutManager(context,  3)
        images_recycler_view.adapter = adapter

        val callback = SimpleItemTouchHelperCallback(adapter)
        val touchHelper = ItemTouchHelper(callback)
        touchHelper.attachToRecyclerView(images_recycler_view)

        val columns = arrayOf(MediaStore.Images.Media.DATA, MediaStore.Images.Media._ID)
        val orderBy = MediaStore.Images.Media._ID

        val imageCursor = CursorLoader(activity, MediaStore.Images.Media.EXTERNAL_CONTENT_URI, columns, null, null, orderBy).loadInBackground()

        val imageColumnIndex = imageCursor.getColumnIndex(MediaStore.Images.Media._ID)
        count = imageCursor.count
        val thumbnails = arrayOfNulls<Bitmap>(count)
        val imagePath = arrayOfNulls<String>(count)

        for (i in 0 until count){
            imageCursor.moveToPosition(i)
            val id = imageCursor.getLong(imageColumnIndex)
            val dataColumnIndex = imageCursor.getColumnIndex(MediaStore.Images.Media.DATA)
            thumbnails[i] = MediaStore.Images.Thumbnails.getThumbnail(activity.applicationContext.contentResolver, id, MediaStore.Images.Thumbnails.MINI_KIND, null)
            imagePath[i] = imageCursor.getString(dataColumnIndex)
        }

        val thumbnailList = thumbnails.asList()
        adapter.setPhotos(thumbnailList)

    }
}