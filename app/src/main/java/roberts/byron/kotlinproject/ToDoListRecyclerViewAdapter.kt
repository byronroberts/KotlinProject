package roberts.byron.kotlinproject

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.to_do_list_item.view.*
import java.util.*


class ToDoListRecyclerViewAdapter(private val toDoValues: ArrayList<ToDoListComponent>, private val context: Context?) : RecyclerView.Adapter<ToDoListRecyclerViewAdapter.ToDoViewHolder>(), ItemTouchHelperAdapter {

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ToDoViewHolder {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.to_do_list_item, parent, false)
        return ToDoViewHolder(view)
    }

    override fun onBindViewHolder(holder: ToDoViewHolder?, position: Int) {
        val toDoItem = toDoValues[position]
        holder?.bind(toDoItem)
    }

    override fun getItemCount(): Int {
        return toDoValues.size
    }

    fun addListItem(toDoListComponent: ToDoListComponent) {
        toDoValues.add(toDoListComponent)
        notifyDataSetChanged()
    }

    override fun onItemMove(fromPosition: Int, toPosition: Int) : Boolean {
        if (fromPosition < toPosition) {
            for (i in fromPosition until toPosition) {
                Collections.swap(toDoValues, i, i + 1)
            }
        } else {
            for (i in fromPosition downTo toPosition + 1) {
                Collections.swap(toDoValues, i, i - 1)
            }
        }
        notifyItemMoved(fromPosition, toPosition)
        return true
    }

    override fun onItemDismiss(position: Int) {
        toDoValues.removeAt(position)
        notifyItemRemoved(position)
    }

    class ToDoViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        fun bind(toDoListComponent: ToDoListComponent) {
            view.toDoTitle.text = toDoListComponent.title
            view.toDoDateBy.text = toDoListComponent.dateToBeCompleted
        }
    }
}