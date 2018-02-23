package roberts.byron.kotlinproject

import android.app.Fragment
import android.os.Bundle
import android.support.constraint.ConstraintSet
import android.support.transition.TransitionManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_constraint.*

class ConstraintLayoutFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_constraint, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        var set = false
        val constraint1 = ConstraintSet()
        constraint1.clone(rootView)
        val constraint2 = ConstraintSet()
        constraint2.clone(activity, R.layout.fragment_constraint_end)

        imageView2.setOnClickListener {
            TransitionManager.beginDelayedTransition(rootView)
            val constraint = if (set) constraint1 else constraint2
            constraint.applyTo(rootView)
            set = !set
        }
    }
}