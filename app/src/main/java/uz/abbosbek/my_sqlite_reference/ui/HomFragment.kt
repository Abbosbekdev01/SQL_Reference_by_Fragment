package uz.abbosbek.my_sqlite_reference.ui

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import uz.abbosbek.my_sqlite_reference.R
import uz.abbosbek.my_sqlite_reference.databinding.FragmentHomBinding

class HomFragment : Fragment() {

    private val binding by lazy { FragmentHomBinding.inflate(layoutInflater) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding.apply {
            btnEmployee.setOnClickListener { findNavController().navigate(R.id.employeeFragment) }
            btnCustomer.setOnClickListener { findNavController().navigate(R.id.customerFragment) }
            btnOrder.setOnClickListener { findNavController().navigate(R.id.ordersFragment) }
        }

        return binding.root
    }

}