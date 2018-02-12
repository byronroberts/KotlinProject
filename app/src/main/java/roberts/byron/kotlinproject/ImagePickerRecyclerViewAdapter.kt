package roberts.byron.kotlinproject

import android.graphics.Bitmap
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class ImagePickerRecyclerViewAdapter : RecyclerView.Adapter<ImageViewHolder>() {

    private var photos: List<Bitmap?> = mutableListOf()
    private var selectedImageCount = 0

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ImageViewHolder {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.list_item_image_view, parent, false)
        view.setOnClickListener {
            val imageNumberView = view.findViewById<TextView>(R.id.image_number)
            val visibility = view.findViewById<TextView>(R.id.image_number).visibility

            if (visibility == View.GONE) {
                ++selectedImageCount
                imageNumberView.text = selectedImageCount.toString()
                imageNumberView.visibility = View.VISIBLE
            } else {
                --selectedImageCount
                imageNumberView.visibility = View.GONE
            }
        }
        return ImageViewHolder(view)
    }

    override fun getItemCount(): Int {
        return photos.size
    }

    override fun onBindViewHolder(holder: ImageViewHolder?, position: Int) {
        holder?.imageView?.setImageBitmap(photos[position])
    }

    fun setPhotos(photos: List<Bitmap?>) {
        this.photos = photos
        notifyDataSetChanged()
    }
}