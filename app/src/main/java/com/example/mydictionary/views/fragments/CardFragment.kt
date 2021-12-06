package com.example.mydictionary.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.mydictionary.App
import com.example.mydictionary.databinding.FragmentCardBinding
import com.example.mydictionary.domain.Card
import com.example.mydictionary.domain.interfaces.CardView
import com.example.mydictionary.domain.interfaces.IImageLoader
import com.example.mydictionary.viewmodels.CardPresenter
import com.example.mydictionary.views.adapters.CardImagesAdapter
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter
import javax.inject.Inject

class CardFragment : MvpAppCompatFragment() , CardView {

    private lateinit var binding: FragmentCardBinding
    @Inject lateinit var imageLoader: IImageLoader
    @Inject
    lateinit var cardPresenter: CardPresenter
    private val presenter by moxyPresenter { cardPresenter }
    private var adapter: CardImagesAdapter? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        App.instance.appComponent.inject(this)
        super.onCreate(savedInstanceState)
        arguments?.getParcelable<Card>(CARD)?.let {
            presenter.init(it)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = CardImagesAdapter(imageLoader, presenter.rvPresenter)
        with(binding){
            rvCards.adapter = adapter
            showTranslate.setOnClickListener{
                presenter.showTranslateClicked()
            }
        }
    }

    override fun notifyDataSetChanged() {
        adapter?.notifyDataSetChanged()
    }

    override fun setTitle(text: String) {
        binding.title.text = text
    }

    override fun showTranslation(translation: String) {
        binding.showTranslate.text = translation
    }

    companion object{
        private const val CARD = "card"
        fun instance(card: Card) = CardFragment().apply {
            val bundle = Bundle().apply {
                putParcelable(CARD, card)
            }
            arguments = bundle
        }
    }
}