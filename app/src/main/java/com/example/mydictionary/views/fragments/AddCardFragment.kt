package layout

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.mydictionary.App
import com.example.mydictionary.R
import com.example.mydictionary.databinding.FragmentAddWordBinding
import com.example.mydictionary.domain.interfaces.AddCardView
import com.example.mydictionary.presenters.AddCardPresenter
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter
import javax.inject.Inject

class AddCardFragment : MvpAppCompatFragment(), AddCardView {

    private lateinit var binding: FragmentAddWordBinding

    @Inject lateinit var addCardPresenter: AddCardPresenter
    private val presenter by moxyPresenter { addCardPresenter }

    override fun onCreate(savedInstanceState: Bundle?) {
        App.instance.appComponent.inject(this)
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddWordBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            btnNext.setOnClickListener {
                val word = word.text.toString().trim()
                if (word.length == 0){
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.attension_field_cant_be_empty),
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    presenter.nextClicked(word)
                }
            }
        }
    }
}