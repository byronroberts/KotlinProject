package roberts.byron.kotlinproject

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup


class ToDoListRecyclerViewAdapter(val toDoValues : List<ToDoListComponent>) : RecyclerView.Adapter<ToDoListRecyclerViewAdapter.ToDoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ToDoViewHolder {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onBindViewHolder(holder: ToDoViewHolder?, position: Int) {

    }

    override fun getItemCount(): Int {
        return toDoValues.size
    }

    class ToDoViewHolder (val view : View) : RecyclerView.ViewHolder(view)
}