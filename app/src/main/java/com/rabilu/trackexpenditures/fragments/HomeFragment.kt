package com.rabilu.trackexpenditures.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.rabilu.trackexpenditures.R
import com.rabilu.trackexpenditures.databinding.FragmentHomeBinding

class HomeFragment : Fragment(R.layout.fragment_home) {

    private lateinit var binding: FragmentHomeBinding

    private lateinit var mAuth: FirebaseAuth

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentHomeBinding.bind(view)

        mAuth = FirebaseAuth.getInstance()

        if (mAuth.currentUser == null) {
            findNavController().popBackStack(findNavController().currentDestination!!.id, true)
            findNavController().navigate(R.id.loginFragment)
        }

        binding.log.setOnClickListener {
            mAuth.signOut()
        }



    }
}