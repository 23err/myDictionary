package com.example.mydictionary.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.mydictionary.App
import com.example.mydictionary.R
import com.example.mydictionary.databinding.FragmentAddTranslationBinding
import com.example.mydictionary.domain.Card
import com.example.mydictionary.domain.interfaces.AddTranslationView
import com.example.mydictionary.presenters.AddTranslationPresenter
import com.google.android.material.chip.Chip
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter
import javax.inject.Inject

class AddTranslationFragment : MvpAppCompatFragment(), AddTranslationView {

    private lateinit var binding: FragmentAddTranslationBinding

    @Inject
    lateinit var addTranslationPresenter: AddTranslationPresenter
    private val presenter by moxyPresenter { addTranslationPresenter }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddTranslationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        App.instance.appComponent.inject(this)
        super.onCreate(savedInstanceState)
        arguments?.getParcelable<Card>(WORD)?.let {
            presenter.init(it)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            btnNext.setOnClickListener {
                presenter.nextClick(binding.word.text.toString())
            }


        }
    }

    override fun setTitle(text: String) {
        binding.title.text = text
    }

    override fun addTranslationWord(id:Int, text: String) {
        val chip =
            layoutInflater.inflate(R.layout.chip, binding.translationsContainer, false) as Chip
        chip.apply {
            this.text = text
            this.id = id
            setOnCheckedChangeListener{ view, checked->
                presenter.checkedWord(id, checked)
            }
        }
        binding.translationsContainer.addView(chip)

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