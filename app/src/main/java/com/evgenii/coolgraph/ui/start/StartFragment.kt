package com.evgenii.coolgraph.ui.start

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.evgenii.coolgraph.R
import com.evgenii.coolgraph.databinding.FragmentStartBinding
import com.evgenii.coolgraph.ui.UiUtils.getTextEmptyFlow
import com.evgenii.coolgraph.ui.UiUtils.hide
import com.evgenii.coolgraph.ui.UiUtils.setInvisible
import com.evgenii.coolgraph.ui.UiUtils.show
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.evgenii.coolgraph.ui.start.LoadPointsState.SUCCESS
import com.evgenii.coolgraph.ui.start.LoadPointsState.ERROR
import kotlinx.coroutines.launch

class StartFragment: Fragment(R.layout.fragment_start) {

    private var _binding: FragmentStartBinding? = null

    private val binding get() = _binding!!

    private val viewModel by viewModel<StartViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentStartBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        listenToViewModel()
        bindCountInput()
        bindButton()
    }

    private fun listenToViewModel() {
        lifecycleScope.launch {
            viewModel.result.collect {
                when (it) {
                    SUCCESS -> {

                    }
                    ERROR -> {
                        binding.errorText.show()
                    }
                    else -> {}
                }
            }
        }
        lifecycleScope.launchWhenResumed {
            viewModel.isLoading.collect { showProgress ->
                binding.countInput.setInvisible(showProgress)
                binding.progressBar.isVisible = showProgress
                binding.button.isEnabled = !showProgress
            }
        }
    }

    private fun bindCountInput() {
        lifecycleScope.launchWhenResumed {
            binding.countInput.getTextEmptyFlow().collect { isEmpty ->
                binding.button.isEnabled = !isEmpty
            }
        }
    }

    private fun bindButton() {
        binding.button.setOnClickListener {
            viewModel.loadPoints(binding.countInput.text.toString())
            binding.errorText.hide()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}