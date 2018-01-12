package roberts.byron.kotlinproject

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.to_do_list_item.view.*


class ToDoListRecyclerViewAdapter(private val toDoValues: ArrayList<ToDoListComponent>, private val context: Context?) : RecyclerView.Adapter<ToDoListRecyclerViewAdapter.ToDoViewHolder>() {

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

    class ToDoViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        fun bind(toDoListComponent: ToDoListComponent) {
            view.toDoTitle.text = toDoListComponent.title
            view.toDoDateBy.text = toDoListComponent.dateToBeCompleted
        }
    }
}