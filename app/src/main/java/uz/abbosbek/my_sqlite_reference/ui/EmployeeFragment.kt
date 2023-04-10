package uz.abbosbek.my_sqlite_reference.ui

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import uz.abbosbek.my_sqlite_reference.R
import uz.abbosbek.my_sqlite_reference.adapters.MyEmpCustAdapter
import uz.abbosbek.my_sqlite_reference.databinding.FragmentEmployeeBinding
import uz.abbosbek.my_sqlite_reference.databinding.ItemDialogBinding
import uz.abbosbek.my_sqlite_reference.db.MyDbHelper
import uz.abbosbek.my_sqlite_reference.models.MyCustomer
import uz.abbosbek.my_sqlite_reference.models.MyEmployee

class EmployeeFragment : Fragment() {
    private val binding by lazy { FragmentEmployeeBinding.inflate(layoutInflater) }
    private lateinit var myDbHelper: MyDbHelper
    private lateinit var myEmployeeAdapter: MyEmpCustAdapter<MyEmployee>

      override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

          myDbHelper = MyDbHelper(requireContext())
          myEmployeeAdapter = MyEmpCustAdapter(myDbHelper.getAllEmployee())
          binding.rv.adapter = myEmployeeAdapter

          binding.root.setBackgroundColor(Color.CYAN)

          binding.apply {
              binding.btnAdd.setOnClickListener {
                  val dialog = AlertDialog.Builder(requireContext()).create()
                  val itemDialogBinding = ItemDialogBinding.inflate(layoutInflater)
                  dialog.setView(itemDialogBinding.root)
                  itemDialogBinding.spinnerEmployee.visibility = View.GONE
                  itemDialogBinding.spinnerCustomer.visibility = View.GONE
                  itemDialogBinding.edtNumber.visibility = View.VISIBLE
                  itemDialogBinding.btnSave.setOnClickListener {
                      val myEmployee = MyEmployee(
                          itemDialogBinding.edtName.text.toString(),
                          itemDialogBinding.edtNumber.text.toString()
                      )
                      myDbHelper.addEmployee(myEmployee)
                      // val toast = Toast(context).gravity.compareTo(10).compareTo(-10)
                      Toast.makeText(requireContext(), "Saved", Toast.LENGTH_SHORT).show()
                      myEmployeeAdapter.notifyItemInserted(myDbHelper.getAllEmployee().size)
                  }
                  dialog.show()
              }
          }

          return binding.root
     }
}