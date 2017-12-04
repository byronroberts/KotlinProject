package roberts.byron.kotlinproject

import android.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_to_do.*

class ToDoFragment : Fragment() {

    private val toList = arrayListOf<String>()

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_to_do, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        done_button.setOnClickListener{ addElementToList() }
    }

    private fun addElementToList() {
        toList.add(to_do_entry.text.toString())
        entered_text.text = ""
        val stringBuilder = StringBuilder()
        val textToShow : String?

        for (element in toList) {
            stringBuilder.append(element)
            stringBuilder.append("\n")
        }

        textToShow = stringBuilder.toString()

        entered_text.text = textToShow
    }


}