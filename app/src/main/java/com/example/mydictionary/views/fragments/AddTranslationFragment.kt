package com.example.mydictionary.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.mydictionary.R
import com.example.mydictionary.databinding.FragmentAddTranslationBinding
import com.example.mydictionary.domain.Card
import com.example.mydictionary.viewmodels.AddTranslationViewModel
import com.example.mydictionary.viewmodels.AppState
import com.google.android.material.chip.Chip
import org.koin.android.ext.android.inject

class AddTranslationFragment : Fragment() {

    private lateinit var binding: FragmentAddTranslationBinding

    private val viewModel: AddTranslationViewModel by inject()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddTranslationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            btnNext.setOnClickListener {
                this@AddTranslationFragment.viewModel.nextClick(binding.word.text.toString())
            }
            arguments?.getParcelable<Card>(WORD)?.let {
                title.text = it.value
                viewModel.init(it)
            }

            viewModel.getLiveData().observe(viewLifecycleOwner) { appState ->
                when (appState) {
                    is AppState.Success -> {
                        translationsContainer.removeAllViews()
                        appState.data.forEach{
                            it.id?.let{id->
                                translationsContainer.addView(createTranslationWordChip(id, it.value))
                            }
                        }
                    }
                    is AppState.Loading->createToast(appState.status)
                    is AppState.Error->createToast(appState.error.message.toString())
                }
            }



        }
    }

    private fun createToast(text:String){
        Toast.makeText(requireContext(), text, Toast.LENGTH_SHORT).show()
    }

    private fun createTranslationWordChip(id: Int, text: String): Chip {
        val chip =
            layoutInflater.inflate(R.layout.chip, binding.translationsContainer, false) as Chip
        return chip.apply {
            this.text = text
            this.id = id
            setOnCheckedChangeListener { view, checked ->
                viewModel.checkedWord(id, checked)
            }
        }
    }

    companion object {
        private const val WORD = "word"
        fun instance(card: Card): Fragment = AddTranslationFragment().apply {
            val bundle = Bundle().apply {
                putParcelable(WORD, card)
            }
            arguments = bundle
        }
    }
}