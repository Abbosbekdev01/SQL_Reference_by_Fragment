package uz.abbosbek.my_sqlite_reference.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import uz.abbosbek.my_sqlite_reference.adapters.MyEmpCustAdapter
import uz.abbosbek.my_sqlite_reference.databinding.FragmentEmployeeBinding
import uz.abbosbek.my_sqlite_reference.databinding.ItemDialogBinding
import uz.abbosbek.my_sqlite_reference.db.MyDbHelper
import uz.abbosbek.my_sqlite_reference.models.MyCustomer

class CustomerFragment: Fragment() {

    private val binding by lazy { FragmentEmployeeBinding.inflate(layoutInflater) }
    private lateinit var myDbHelper: MyDbHelper
    private lateinit var myCustomerAdapter:MyEmpCustAdapter<MyCustomer>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        myDbHelper = MyDbHelper(requireContext())
        myCustomerAdapter = MyEmpCustAdapter(myDbHelper.getAllCustomer())
        binding.rv.adapter = myCustomerAdapter

        binding.apply {
            binding.btnAdd.setOnClickListener {
                val dialog = AlertDialog.Builder(requireContext()).create()
                val itemDialogBinding = ItemDialogBinding.inflate(layoutInflater)
                dialog.setView(itemDialogBinding.root)
                itemDialogBinding.spinnerEmployee.visibility = View.GONE
                itemDialogBinding.spinnerCustomer.visibility = View.GONE
                itemDialogBinding.edtNumber.visibility = View.VISIBLE
                itemDialogBinding.edtAddress.visibility = View.VISIBLE
                itemDialogBinding.btnSave.setOnClickListener {
                    val myCustomer = MyCustomer(
                        itemDialogBinding.edtName.text.toString(),
                        itemDialogBinding.edtNumber.text.toString(),
                        itemDialogBinding.edtAddress.text.toString()
                    )
                    myDbHelper.addCustomer(myCustomer)
                   // val toast = Toast(context).gravity.compareTo(10).compareTo(-10)
                    Toast.makeText(requireContext(), "Saved", Toast.LENGTH_SHORT).show()
                    myCustomerAdapter.notifyItemInserted(myDbHelper.getAllCustomer().size)
                }
                dialog.show()
            }
        }

        return binding.root
    }
}