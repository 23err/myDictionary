package layout

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.mydictionary.App
import com.example.mydictionary.R
import com.example.mydictionary.databinding.FragmentAddWordBinding
import com.example.mydictionary.viewmodels.AddCardViewModel
import javax.inject.Inject

class AddCardFragment : Fragment() {

    private lateinit var binding: FragmentAddWordBinding
    @Inject lateinit var addCardViewModel: AddCardViewModel

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
                    this@AddCardFragment.addCardViewModel.nextClicked(word)
                }
            }
        }
    }
}