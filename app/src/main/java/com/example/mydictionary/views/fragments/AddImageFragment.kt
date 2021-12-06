package com.example.mydictionary.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.mydictionary.App
import com.example.mydictionary.databinding.FragmentAddImageBinding
import com.example.mydictionary.domain.Card
import com.example.mydictionary.domain.interfaces.AddImageView
import com.example.mydictionary.domain.interfaces.IImageLoader
import com.example.mydictionary.viewmodels.AddImagePresenter
import com.example.mydictionary.views.adapters.ImagesListAdapter
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter
import javax.inject.Inject

class AddImageFragment : MvpAppCompatFragment(), AddImageView {

    private lateinit var binding: FragmentAddImageBinding
    @Inject lateinit var addImagePresenter: AddImagePresenter
    @Inject lateinit var imageLoader: IImageLoader
    private val presenter by moxyPresenter { addImagePresenter }
    private var adapter: ImagesListAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        App.instance.appComponent.inject(this)
        super.onCreate(savedInstanceState)
        arguments?.getParcelable<Card>(WORD)?.let{
            presenter.init(it)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddImageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding){
            btnNext.setOnClickListener{
                presenter.nextClicked()
            }
            adapter = ImagesListAdapter(presenter.rvImagesPresenter, imageLoader)
            rvImages.adapter = adapter
        }
    }

    override fun notifyDataSetChanged() {
        adapter?.notifyDataSetChanged()
    }

    override fun setTitle(title: String) {
        binding.title.text = title
    }

    companion object {
        private const val WORD = "word"

        fun instance(card: Card): Fragment = AddImageFragment().apply {
            val bundle = Bundle().apply {
                putParcelable(WORD, card)
            }
            arguments = bundle
        }
    }
}