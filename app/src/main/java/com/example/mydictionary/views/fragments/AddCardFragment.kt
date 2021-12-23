package layout

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.mydictionary.R
import com.example.mydictionary.databinding.FragmentAddWordBinding
import com.example.mydictionary.viewmodels.AddCardViewModel
import org.koin.core.component.KoinScopeComponent
import org.koin.core.component.createScope
import org.koin.core.scope.Scope

class AddCardFragment : Fragment(), KoinScopeComponent {
    override val scope: Scope = createScope(this)
    private lateinit var binding: FragmentAddWordBinding
    private val addCardViewModel: AddCardViewModel by scope.inject()

    override fun onCreate(savedInstanceState: Bundle?) {
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