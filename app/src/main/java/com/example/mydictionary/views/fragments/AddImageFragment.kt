package com.example.mydictionary.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.mydictionary.databinding.FragmentAddImageBinding
import com.example.mydictionary.domain.Card
import com.example.mydictionary.domain.interfaces.AddImageView
import com.example.mydictionary.domain.interfaces.IImageLoader
import com.example.mydictionary.viewmodels.AddImagePresenter
import com.example.mydictionary.views.adapters.ImagesListAdapter
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter
import org.koin.android.ext.android.inject

class AddImageFragment : MvpAppCompatFragment(), AddImageView {

    private lateinit var binding: FragmentAddImageBinding
    private val imageLoader: IImageLoader by inject()
    private val addImagePresenter: AddImagePresenter by inject()
    private val presenter by moxyPresenter { addImagePresenter }
    private var adapter: ImagesListAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
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

    override fun showError(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
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