package com.example.mydictionary.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.mydictionary.R
import com.example.mydictionary.databinding.FragmentCardsBinding
import com.example.mydictionary.domain.interfaces.CardsView
import com.example.mydictionary.domain.interfaces.IImageLoader
import com.example.mydictionary.viewmodels.CardsPresenter
import com.example.mydictionary.views.adapters.WordsListAdapter
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter
import org.koin.android.ext.android.inject

class CardsFragment: MvpAppCompatFragment(),  CardsView{

    private lateinit var binding: FragmentCardsBinding
    private val cardsPresenter: CardsPresenter by inject()
    private val presenter by moxyPresenter { cardsPresenter }
    private val imageLoader: IImageLoader by inject()

    private var adapter: WordsListAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCardsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = WordsListAdapter(presenter.wordsPresenter, imageLoader)
        presenter.init()
        with(binding){
            toolbar.title = getString(R.string.words)
            rvWords.adapter = adapter
            btnAdd.setOnClickListener{
                presenter.addWordClick()
            }
        }
    }

    override fun showError(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    override fun notifyDataSetChanged() {
        adapter?.notifyDataSetChanged()
    }

}