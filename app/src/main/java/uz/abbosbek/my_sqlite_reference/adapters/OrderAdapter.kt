package uz.abbosbek.my_sqlite_reference.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import uz.abbosbek.my_sqlite_reference.databinding.ItemRvBinding
import uz.abbosbek.my_sqlite_reference.models.MyOrder

class OrderAdapter(val list: ArrayList<MyOrder>) : RecyclerView.Adapter<OrderAdapter.Vh>() {

    inner class Vh(val itemRvBinding: ItemRvBinding) : RecyclerView.ViewHolder(itemRvBinding.root) {
        fun onBind(myOrder: MyOrder, position: Int) {
            itemRvBinding.tvName.text = myOrder.name
            itemRvBinding.tvCustomer.text = myOrder.myCustomer?.name
            itemRvBinding.tvEmployee.text = myOrder.myEmployee?.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(ItemRvBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(list[position],position)
    }

    override fun getItemCount(): Int {
        return list.size
    }

}