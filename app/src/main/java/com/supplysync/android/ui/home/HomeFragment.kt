//to manage the inventory

package com.supplysync.android.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.supplysync.android.R
import com.supplysync.android.databinding.FragmentHomeBinding

//todo use recycler view and view model
class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val homeViewModel: HomeViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        homeViewModel.getVizData()

        homeViewModel.title1.observe(viewLifecycleOwner) { title ->
            binding.tv1.text = title.toString()
        }
        homeViewModel.imageurl1.observe(viewLifecycleOwner) { imageurl ->
            Glide.with(binding.iv1.context)
                .load(imageurl)
                .placeholder(R.drawable.loading)
                .error(R.drawable.error)
                .into(binding.iv1)

        }

        homeViewModel.title2.observe(viewLifecycleOwner) { title ->
            binding.tv2.text = title.toString()
        }
        homeViewModel.imageurl2.observe(viewLifecycleOwner) { imageurl ->
            Glide.with(binding.iv2.context)
                .load(imageurl)
                .placeholder(R.drawable.loading)
                .error(R.drawable.error)
                .into(binding.iv2)

        }

        homeViewModel.title3.observe(viewLifecycleOwner) { title ->
            binding.tv3.text = title.toString()
        }
        homeViewModel.imageurl3.observe(viewLifecycleOwner) { imageurl ->
            Glide.with(binding.iv3.context)
                .load(imageurl)
                .placeholder(R.drawable.loading)
                .error(R.drawable.error)
                .into(binding.iv3)

        }

        homeViewModel.title4.observe(viewLifecycleOwner) { title ->
            binding.tv4.text = title.toString()
        }
        homeViewModel.imageurl4.observe(viewLifecycleOwner) { imageurl ->
            Glide.with(binding.iv4.context)
                .load(imageurl)
                .placeholder(R.drawable.loading)
                .error(R.drawable.error)
                .into(binding.iv4)

        }

        homeViewModel.title5.observe(viewLifecycleOwner) { title ->
            binding.tv5.text = title.toString()
        }
        homeViewModel.imageurl5.observe(viewLifecycleOwner) { imageurl ->
            Glide.with(binding.iv5.context)
                .load(imageurl)
                .placeholder(R.drawable.loading)
                .error(R.drawable.error)
                .into(binding.iv5)

        }

        homeViewModel.title6.observe(viewLifecycleOwner) { title ->
            binding.tv6.text = title.toString()
        }
        homeViewModel.imageurl6.observe(viewLifecycleOwner) { imageurl ->
            Glide.with(binding.iv6.context)
                .load(imageurl)
                .placeholder(R.drawable.loading)
                .error(R.drawable.error)
                .into(binding.iv6)

        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }

}