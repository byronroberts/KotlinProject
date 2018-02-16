package roberts.byron.kotlinproject

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView

class ImageViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

    val imageView: ImageView = view.findViewById(R.id.image_view)
    val selectedCount: TextView = view.findViewById(R.id.image_number)
    var imageNumber: Int = 0
    var isSelected : Boolean = false
}