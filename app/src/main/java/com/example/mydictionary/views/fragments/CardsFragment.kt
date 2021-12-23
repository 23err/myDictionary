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
import com.example.mydictionary.extensions.viewById
import com.example.mydictionary.viewmodels.CardsPresenter
import com.example.mydictionary.views.adapters.WordsListAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter
import org.koin.androidx.scope.createScope
import org.koin.core.component.KoinScopeComponent
import org.koin.core.scope.Scope

class CardsFragment: MvpAppCompatFragment(),  CardsView, KoinScopeComponent{

    private lateinit var binding: FragmentCardsBinding
    override val scope: Scope by lazy { createScope(this)}
    private val cardsPresenter: CardsPresenter by scope.inject()
    private val presenter by moxyPresenter { cardsPresenter }
    private val imageLoader: IImageLoader by scope.inject()
    private val btn by viewById<FloatingActionButton>(R.id.btnAdd)

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