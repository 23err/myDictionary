package com.example.mydictionary.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.mydictionary.App
import com.example.mydictionary.R
import com.example.mydictionary.databinding.FragmentCardsBinding
import com.example.mydictionary.domain.interfaces.CardsView
import com.example.mydictionary.presenters.CardsPresenter
import com.example.mydictionary.views.adapters.WordsListAdapter
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter
import javax.inject.Inject

class CardsFragment: MvpAppCompatFragment(),  CardsView{

    private lateinit var binding: FragmentCardsBinding
    @Inject lateinit var cardsPresenter: CardsPresenter
    private val presenter by moxyPresenter { cardsPresenter }

    private var adapter: WordsListAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        App.instance.appComponent.inject(this)
        super.onCreate(savedInstanceState)
    }

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
        adapter = WordsListAdapter(presenter.wordsPresenter)
        presenter.init()
        with(binding){
            toolbar.title = getString(R.string.words)
            rvWords.adapter = adapter
            btnAdd.setOnClickListener{
                presenter.addWordClick()
            }
        }
    }

    override fun notifyDataSetChanged() {
        adapter?.notifyDataSetChanged()
    }

}