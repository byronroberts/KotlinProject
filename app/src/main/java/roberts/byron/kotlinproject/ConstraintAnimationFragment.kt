package roberts.byron.kotlinproject

import android.app.Fragment
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.constraint.ConstraintSet
import android.support.transition.TransitionManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_constraint.*
import kotlinx.android.synthetic.main.fragment_constraint_animation.*

class ConstraintAnimationFragment : Fragment() {

    private var selectedView: View? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_constraint_animation, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        setupAnimations()
    }

    private fun setupAnimations() {
        selectedView = null

        rootViewAnim.setOnClickListener {
            toDefault()
        }

        javaImg.setOnClickListener {
            if (selectedView != javaImg) {
                updateConstraints(R.layout.fragment_constraint_animation_java)
                selectedView = javaImg
            } else
                toDefault()
        }

        kotlinImg.setOnClickListener {
            if (selectedView != kotlinImg) {
                updateConstraints(R.layout.fragment_constraint_animation_kotlin)
                selectedView = kotlinImg
            } else
                toDefault()
        }
    }

    private fun toDefault() {
        if (selectedView != null) {
            updateConstraints(R.layout.fragment_constraint_animation)
            selectedView = null
        }
    }

    private fun updateConstraints(@LayoutRes id: Int) {
        val newConstraintSet = ConstraintSet()
        newConstraintSet.clone(activity, id)
        newConstraintSet.applyTo(rootViewAnim)
        TransitionManager.beginDelayedTransition(rootViewAnim)
    }
}