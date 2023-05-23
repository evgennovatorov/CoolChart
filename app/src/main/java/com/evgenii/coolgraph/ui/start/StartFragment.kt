package com.evgenii.coolgraph.ui.start

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.evgenii.coolgraph.common.collect
import com.evgenii.coolgraph.databinding.FragmentStartBinding
import com.evgenii.coolgraph.ui.UiUtils.getTextEmptyFlow
import com.evgenii.coolgraph.ui.UiUtils.hide
import com.evgenii.coolgraph.ui.UiUtils.setInvisible
import com.evgenii.coolgraph.ui.UiUtils.show
import org.koin.androidx.viewmodel.ext.android.viewModel

class StartFragment: Fragment() {

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
        viewModel.result.collect(viewLifecycleOwner) {
            val action = StartFragmentDirections.actionStartFragmentToGraphFragment(
                it.toTypedArray()
            )
            findNavController().navigate(action)
        }
        viewModel.error.collect(viewLifecycleOwner) {
            if (it) {
                binding.errorText.show()
            } else {
                binding.errorText.hide()
            }
        }
        viewModel.isLoading.collect(viewLifecycleOwner) { showProgress ->
            binding.countInput.setInvisible(showProgress)
            binding.progressBar.isVisible = showProgress
            if (binding.countInput.text.toString().isNotEmpty()) {
                binding.button.isEnabled = !showProgress
            }
        }
    }

    private fun bindCountInput() {
        binding.countInput.getTextEmptyFlow().collect(viewLifecycleOwner) { isEmpty ->
            binding.button.isEnabled = !isEmpty
        }
    }

    private fun bindButton() {
        binding.button.setOnClickListener {
            viewModel.loadPoints(binding.countInput.text.toString())
            binding.errorText.hide()
            hideKeyboard()
        }
    }

    private fun hideKeyboard() {
        val imm: InputMethodManager? =
            ContextCompat.getSystemService(
                requireContext(),
                InputMethodManager::class.java
            )
        imm?.hideSoftInputFromWindow(binding.countInput.windowToken, 0)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}