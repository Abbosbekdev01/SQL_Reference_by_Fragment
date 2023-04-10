package uz.abbosbek.my_sqlite_reference.models

class MyOrder {
    var id: Int? = null
    var name: String? = null
    var date: String? = null
    var price:Int? = null
    var myCustomer: MyCustomer? = null
    var myEmployee: MyEmployee? = null


    constructor(
        id: Int?,
        name: String?,
        date: String?,
        price: Int?,
        myCustomer: MyCustomer?,
        myEmployee: MyEmployee?
    ) {
        this.id = id
        this.name = name
        this.date = date
        this.price = price
        this.myCustomer = myCustomer
        this.myEmployee = myEmployee
    }

    constructor(
        name: String?,
        date: String?,
        price: Int?,
        myCustomer: MyCustomer?,
        myEmployee: MyEmployee?
    ) {
        this.name = name
        this.date = date
        this.price = price
        this.myCustomer = myCustomer
        this.myEmployee = myEmployee
    }



}