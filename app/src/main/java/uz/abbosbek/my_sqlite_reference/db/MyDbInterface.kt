package uz.abbosbek.my_sqlite_reference.db

import uz.abbosbek.my_sqlite_reference.models.MyCustomer
import uz.abbosbek.my_sqlite_reference.models.MyEmployee
import uz.abbosbek.my_sqlite_reference.models.MyOrder

interface MyDbInterface {

    fun addCustomer(myCustomer: MyCustomer)
    fun getAllCustomer():List<MyCustomer>

    fun addEmployee(myEmployee: MyEmployee)
    fun getAllEmployee():List<MyEmployee>

    fun addOrder(myOrder: MyOrder)
    fun getALlOrders():List<MyOrder>

    /** MyEmployee ni id orqali olib kelamiz va getEmployeeById ga o'zlashtiramiz*/
    fun getEmployeeById(id:Int):MyEmployee
    /** MyCustomer ni id orqali olib kelamiz va getCustomerById ga o'zlashtiramiz*/
    fun getCustomerById(id: Int):MyCustomer
}