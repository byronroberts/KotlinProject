package roberts.byron.kotlinproject

import android.app.DatePickerDialog
import android.app.Fragment
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import kotlinx.android.synthetic.main.fragment_to_do.*
import java.util.*

class ToDoFragment : Fragment() {

    private lateinit var toDoListAdapter: ToDoListRecyclerViewAdapter
    private var toDoListItems: ArrayList<ToDoListComponent> = ArrayList()
    private lateinit var linearLayoutManager: LinearLayoutManager
    private var calendar: Calendar = Calendar.getInstance()

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_to_do, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        doneButton.setOnClickListener { addElementToList() }

        linearLayoutManager = LinearLayoutManager(activity)
        linearLayoutManager.isAutoMeasureEnabled = false
        toDoListAdapter = ToDoListRecyclerViewAdapter(toDoListItems, activity)
        toDoRecyclerView.layoutManager = linearLayoutManager
        toDoRecyclerView.adapter = toDoListAdapter

        val callback = SimpleItemTouchHelperCallback(toDoListAdapter)
        val touchHelper = ItemTouchHelper(callback)
        touchHelper.attachToRecyclerView(toDoRecyclerView)

        completionDate.setOnClickListener { openDatePicker() }
    }

    private fun openDatePicker() {
        DatePickerDialog(activity, date, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show()
    }

    private fun addElementToList() {
        val newElement = ToDoListComponent(toDoEntry.text.toString(), completionDate.text.toString())
        toDoListAdapter.addListItem(newElement)
    }

    private val date = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth -> completionDate.setText(dayOfMonth.toString() + "/" + month.plus(1).toString() + "/" + year.toString() ) }
}