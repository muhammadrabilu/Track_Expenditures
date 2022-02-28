package com.rabilu.trackexpenditures.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.rabilu.trackexpenditures.R
import com.rabilu.trackexpenditures.data.User
import com.rabilu.trackexpenditures.databinding.FragmentRegistrationBinding
import com.rabilu.trackexpenditures.util.Constance.USER_DB

class RegistrationFragment : Fragment(R.layout.fragment_registration) {


    private lateinit var binding: FragmentRegistrationBinding
    private val db = Firebase.firestore
    private val mAuth = Firebase.auth

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentRegistrationBinding.bind(view)

        val name = binding.editTextName.editText
        val email = binding.editTextEmail.editText
        val password = binding.editTextPassword.editText
        val confirmPassword = binding.editTextPasswordConfirm.editText

        binding.btnLogin.setOnClickListener {
            findNavController().navigate(R.id.loginFragment)
        }

        binding.btnCreateAccount.setOnClickListener {

            val alertDialog = AlertDialog.Builder(requireContext()).create()
            alertDialog.setCancelable(false)
            val dialogLayout = layoutInflater.inflate(R.layout.progress_dialog, binding.root, false)
            alertDialog.setView(dialogLayout)

            if (name?.text!!.isEmpty() || email?.text!!.isEmpty()
                || password?.text!!.isEmpty() || confirmPassword?.text!!.isEmpty()
            ) {
                Toast.makeText(requireContext(), "One or more field is empty", Toast.LENGTH_SHORT)
                    .show()
            } else {
                if (password.text.toString() != confirmPassword.text.toString()) {
                    Toast.makeText(requireContext(), "Password not same", Toast.LENGTH_SHORT).show()
                } else {
                    alertDialog.show()
                    mAuth.createUserWithEmailAndPassword(
                        email.text.toString(),
                        password.text.toString()
                    ).addOnCompleteListener { task ->

                        val user = User(name = name.text.toString(), email = email.text.toString())

                        if (task.isSuccessful) {
                            // sve the user information to the firebase cloud
                            db.collection(USER_DB).document(mAuth.currentUser!!.uid).set(user).addOnCompleteListener {
                                if (it.isSuccessful){
                                    alertDialog.dismiss()
                                    findNavController().popBackStack(findNavController().currentDestination!!.id, true)
                                    findNavController().navigate(R.id.homeFragment)
                                }else {
                                    Log.e("Error", "onViewCreated: ${it.exception!!.localizedMessage}", )
                                }
                            }
                        } else {
                            alertDialog.dismiss()
                            Toast.makeText(
                                requireContext(),
                                task.exception!!.localizedMessage,
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                    }
                }
            }
        }
    }
}