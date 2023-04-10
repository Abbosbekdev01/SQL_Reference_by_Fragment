package uz.abbosbek.my_sqlite_reference.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import uz.abbosbek.my_sqlite_reference.databinding.ItemRvBinding
import uz.abbosbek.my_sqlite_reference.models.MyCustomer
import uz.abbosbek.my_sqlite_reference.models.MyEmployee

class MyEmpCustAdapter<T>(val list: List<T>):RecyclerView.Adapter<MyEmpCustAdapter<T>.Vh>() {

    inner class Vh(val itemRv:ItemRvBinding):RecyclerView.ViewHolder(itemRv.root){
        fun onEmployeeBinding(myEmployee: MyEmployee){
            itemRv.tvName.text = myEmployee.name
            itemRv.tvNumber.visibility = View.VISIBLE
            itemRv.tvNumber.text = myEmployee.number
            itemRv.tvEmployee.visibility = View.GONE
            itemRv.tvCustomer.visibility = View.GONE
        }

        fun onCustomerBinding(myCustomer: MyCustomer){
            itemRv.tvName.text = myCustomer.name
            itemRv.tvNumber.visibility = View.VISIBLE
            itemRv.tvNumber.text = myCustomer.number
            itemRv.tvAddress.visibility = View.VISIBLE
            itemRv.tvAddress.text = myCustomer.address
            itemRv.tvEmployee.visibility = View.GONE
            itemRv.tvCustomer.visibility = View.GONE
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(ItemRvBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        try {
            val myEmployee:MyEmployee = list[position] as MyEmployee
            holder.onEmployeeBinding(myEmployee)
        }catch (e:Exception){
            val myCustomer:MyCustomer = list[position] as MyCustomer
            holder.onCustomerBinding(myCustomer)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}