package uz.abbosbek.my_sqlite_reference.models

class MyEmployee {
    var id:Int? = null
    var name:String? = null
    var number:String? = null

    constructor(id: Int?, name: String?, number: String?) {
        this.id = id
        this.name = name
        this.number = number
    }

    constructor(name: String?, number: String?) {
        this.name = name
        this.number = number
    }

    constructor()


}