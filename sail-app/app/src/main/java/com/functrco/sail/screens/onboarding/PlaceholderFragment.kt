package com.functrco.sail.screens.onboarding

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.functrco.sail.SignInActivity
import com.functrco.sail.databinding.FragmentOnboardingBinding

/**
 * A placeholder fragment containing a simple view.
 */
class PlaceholderFragment : Fragment() {

    private lateinit var pageViewModel: PageViewModel
    private var _binding: FragmentOnboardingBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pageViewModel = ViewModelProvider(this).get(PageViewModel::class.java).apply {
            setIndex(arguments?.getInt(ARG_SECTION_NUMBER) ?: 1)
        }
    }

    private fun redirectToSignIn() {
        val i = Intent(activity, SignInActivity::class.java)
        i.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(i)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentOnboardingBinding.inflate(inflater, container, false)
        val root = binding.root

        // print what is stored in shared pref
        val sp = activity?.getSharedPreferences("sail-preferences", Context.MODE_PRIVATE)
        if (sp?.getString("show_onboarding", "default") == "false") {
            redirectToSignIn()
        }


        val sectionHeading: TextView = binding.sectionHeading
        val sectionDescription: TextView = binding.sectionDescription
        val constraintLayout: ConstraintLayout = binding.constraintLayout
        val continueButton: Button = binding.omboardingContinueBtn

        pageViewModel.text.observe(viewLifecycleOwner, Observer {
            sectionHeading.text = it[1]
            sectionDescription.text = it[2]
            constraintLayout.setBackgroundColor(Color.parseColor(it[3]))

            // We check whether it is the last activity
            continueButton.visibility = if (it[0].toInt() == 2) View.VISIBLE else View.GONE

            // Either way, we attach an onClick listerener that takes the user
            // to login screen
            continueButton.setOnClickListener {
                // Put into shared preferences so that we never show onboarding again
                val sp = activity?.getSharedPreferences("sail-preferences", Context.MODE_PRIVATE)
                sp?.edit()?.putString("show_onboarding", "false")?.apply()
                redirectToSignIn()
            }

        })

        return root
    }

    companion object {
        private const val ARG_SECTION_NUMBER = "section_number"
        @JvmStatic
        fun newInstance(sectionNumber: Int): PlaceholderFragment {
            return PlaceholderFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_SECTION_NUMBER, sectionNumber)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}