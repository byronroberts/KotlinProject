package roberts.byron.kotlinproject

import android.graphics.Bitmap
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import java.util.*

class ImagePickerRecyclerViewAdapter : RecyclerView.Adapter<ImageViewHolder>(), ItemTouchHelperAdapter {

    private var photos: List<Bitmap?> = mutableListOf()
    private var selectedPhotos: ArrayList<Bitmap?> = arrayListOf()
    private var selectedImageCount = 0

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ImageViewHolder {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.list_item_image_view, parent, false)
        return ImageViewHolder(view)
    }

    override fun getItemCount(): Int {
        return photos.size
    }

    override fun onBindViewHolder(holder: ImageViewHolder?, position: Int) {
        holder?.imageView?.setImageBitmap(photos[position])
        holder?.selectedCount?.text = selectedImageCount.toString()

        holder?.let {
            if (it.isSelected) {
                if (!selectedPhotos.contains(photos[position])) {
                    selectedPhotos.add(photos[position])
                }
                it.selectedCount.text = selectedPhotos.indexOf(photos[position]).plus(1).toString()
                it.selectedCount.visibility = View.VISIBLE
            } else {
                if (selectedPhotos.contains(photos[position])) {
                    selectedPhotos.remove(photos[position])
                }
                it.selectedCount.visibility = View.GONE
            }
        }

        holder?.imageView?.setOnClickListener{
            holder.isSelected = !holder.isSelected
            notifyDataSetChanged()
        }
    }

    fun setPhotos(photos: List<Bitmap?>) {
        this.photos = photos
        notifyDataSetChanged()
    }

    override fun onItemMove(fromPosition: Int, toPosition: Int): Boolean {
        if (fromPosition < toPosition) {
            for (i in fromPosition until toPosition) {
                Collections.swap(photos, i, i + 1)
            }
        } else {
            for (i in fromPosition downTo toPosition + 1) {
                Collections.swap(photos, i, i - 1)
            }
        }
        notifyItemMoved(fromPosition, toPosition)
        return true
    }

    override fun onItemDismiss(position: Int) {}
}