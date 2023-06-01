package co.app.food.tabs.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import co.app.food.common.addTo
import co.app.food.common.base.BaseFragment
import co.app.food.common.viewBinding
import co.app.food.landing.databinding.FragmentTabBinding
import co.app.food.tabs.domain.TabState
import co.app.food.tabs.domain.TabViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TabFragment : BaseFragment<TabState>() {

    private val binding by viewBinding(FragmentTabBinding::inflate)
    private val vm: TabViewModel by viewModels()
    //  private val navArgs by navArgs<TabFragmentArgs>()
    private val screen by lazy { TabScreenImpl() }
    private lateinit var urlToLoad: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpHandlers()
        extractData()
//        vm.loadTabContent(urlToLoad)
    }

    private fun extractData() {
        //  urlToLoad = navArgs.tabArgs.url
    }

    private fun setUpHandlers() {
        vm.state.observe(
            viewLifecycleOwner,
            { state ->
                state?.let {
                    _state.onNext(it)
                }
            }
        )
        screen.bind(binding, state.share())
            .addTo(compositeBag)
        screen.event.observe(
            viewLifecycleOwner,
            { event ->
                when (event) {
                    else -> {}
                }
            }
        )
    }
}
