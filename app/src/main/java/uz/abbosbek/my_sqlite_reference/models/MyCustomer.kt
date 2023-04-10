package uz.abbosbek.my_sqlite_reference.models

class MyCustomer {

    var id:Int? = null
    var name:String? = null
    var number:String? = null
    var address:String? = null

    constructor(id: Int?, name: String?, number: String?, address: String?) {
        this.id = id
        this.name = name
        this.number = number
        this.address = address
    }

    constructor(name: String?, number: String?, address: String?) {
        this.name = name
        this.number = number
        this.address = address
    }

    constructor()
}