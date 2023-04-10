package uz.abbosbek.my_sqlite_reference.ui

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import uz.abbosbek.my_sqlite_reference.R
import uz.abbosbek.my_sqlite_reference.adapters.OrderAdapter
import uz.abbosbek.my_sqlite_reference.databinding.FragmentEmployeeBinding
import uz.abbosbek.my_sqlite_reference.databinding.ItemDialogBinding
import uz.abbosbek.my_sqlite_reference.db.MyDbHelper
import uz.abbosbek.my_sqlite_reference.models.MyOrder

class OrdersFragment : Fragment() {

    private val binding by lazy { FragmentEmployeeBinding.inflate(layoutInflater) }
    private lateinit var orderAdapter: OrderAdapter
    private lateinit var myDbHelper: MyDbHelper
    private lateinit var orderList: ArrayList<MyOrder>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding.root.setBackgroundColor(Color.YELLOW)

        myDbHelper = MyDbHelper(requireContext())
        orderAdapter = OrderAdapter(myDbHelper.getALlOrders())
        orderList = ArrayList(myDbHelper.getALlOrders())

        binding.rv.adapter = orderAdapter

        binding.btnAdd.setOnClickListener {
            val dialog = AlertDialog.Builder(requireContext()).create()
            val itemDialogBinding = ItemDialogBinding.inflate(layoutInflater)
            dialog.setView(itemDialogBinding.root)
            dialog.show()

            itemDialogBinding.apply {
                /** bu yerda employeeList ga faqat name saqlab olamiz*/
                val empList = ArrayList<String>()
                val employeeList = myDbHelper.getAllEmployee()
                for (myEmployee in employeeList) {
                    empList.add(myEmployee.name!!)
                }
                spinnerEmployee.adapter =
                    ArrayAdapter(binding.root.context, android.R.layout.simple_list_item_1, empList)

                val cusList = ArrayList<String>()
                val customerList = myDbHelper.getAllCustomer()
                for (myCutomer in customerList) {
                    cusList.add(myCutomer.name!!)
                }
                spinnerCustomer.adapter =
                    ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, cusList)
                edtPrice.visibility = View.VISIBLE
                edtAddress.visibility = View.VISIBLE

                btnSave.setOnClickListener {
                    val myOrder = MyOrder(
                        edtName.text.toString(),
                        edtAddress.text.toString(),
                        edtPrice.text.toString().toInt(),
                        customerList[spinnerCustomer.selectedItemPosition],
                        employeeList[spinnerEmployee.selectedItemPosition]
                    )

                    myDbHelper.addOrder(myOrder)
                    orderList.add(myOrder)
                    orderAdapter.notifyItemInserted(orderList.size)
                    Toast.makeText(context, "Saved", Toast.LENGTH_SHORT).show()
                }
            }
        }

        return binding.root
    }
}