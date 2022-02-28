package com.rabilu.trackexpenditures.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.rabilu.trackexpenditures.R
import com.rabilu.trackexpenditures.databinding.FragmentLoginBinding

class LoginFragment : Fragment(R.layout.fragment_login) {

    private lateinit var binding: FragmentLoginBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentLoginBinding.bind(view)
        val email = binding.editTextEmail.editText
        val password = binding.editTextPassord.editText

        binding.btnctreateAccount.setOnClickListener {
            findNavController().navigate(R.id.registrationFragment)
        }

        binding.btnLogin.setOnClickListener {

            val dialogLayout =
                layoutInflater.inflate(R.layout.progress_dialog, binding.root, false)
            val alertDialog = AlertDialog.Builder(requireContext())
                .setView(dialogLayout)
                .setCancelable(false)
                .create()

            when {
                email!!.text.isEmpty() -> {
                    email.error = "Email is required"
                }
                password!!.text.isEmpty() -> {
                    password.error = "Password is empty"
                }
                else -> {
                    alertDialog.show()
                    Firebase.auth.signInWithEmailAndPassword(
                        email.text.toString(),
                        password.text.toString()
                    ).addOnCompleteListener {
                        if (it.isSuccessful) {
                            findNavController().popBackStack(
                                findNavController().currentDestination!!.id,
                                true
                            )
                            findNavController().navigate(R.id.homeFragment)
                            alertDialog.dismiss()
                        } else {
                            alertDialog.dismiss()
                            Toast.makeText(
                                requireContext(),
                                it.exception!!.localizedMessage,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }

            }
        }
    }

}