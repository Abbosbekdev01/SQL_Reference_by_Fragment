package uz.abbosbek.my_sqlite_reference.db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import uz.abbosbek.my_sqlite_reference.db.MyConstants.CUSTOMER_ADDRESS
import uz.abbosbek.my_sqlite_reference.db.MyConstants.CUSTOMER_ID
import uz.abbosbek.my_sqlite_reference.db.MyConstants.CUSTOMER_NAME
import uz.abbosbek.my_sqlite_reference.db.MyConstants.CUSTOMER_NUMBER
import uz.abbosbek.my_sqlite_reference.db.MyConstants.CUSTOMER_TABLE
import uz.abbosbek.my_sqlite_reference.db.MyConstants.DB_NAME
import uz.abbosbek.my_sqlite_reference.db.MyConstants.EMPLOYEE_ID
import uz.abbosbek.my_sqlite_reference.db.MyConstants.EMPLOYEE_NAME
import uz.abbosbek.my_sqlite_reference.db.MyConstants.EMPLOYEE_NUMBER
import uz.abbosbek.my_sqlite_reference.db.MyConstants.EMPLOYEE_TABLE
import uz.abbosbek.my_sqlite_reference.db.MyConstants.ORDER_CUSTOMER_ID
import uz.abbosbek.my_sqlite_reference.db.MyConstants.ORDER_DATE
import uz.abbosbek.my_sqlite_reference.db.MyConstants.ORDER_EMPLOYEE_ID
import uz.abbosbek.my_sqlite_reference.db.MyConstants.ORDER_ID
import uz.abbosbek.my_sqlite_reference.db.MyConstants.ORDER_NAME
import uz.abbosbek.my_sqlite_reference.db.MyConstants.ORDER_PRICE
import uz.abbosbek.my_sqlite_reference.db.MyConstants.ORDER_TABLE
import uz.abbosbek.my_sqlite_reference.db.MyConstants.VERSION
import uz.abbosbek.my_sqlite_reference.models.MyCustomer
import uz.abbosbek.my_sqlite_reference.models.MyEmployee
import uz.abbosbek.my_sqlite_reference.models.MyOrder

class MyDbHelper(context: Context) : SQLiteOpenHelper(context, DB_NAME, null, VERSION),
    MyDbInterface {
    override fun onCreate(p0: SQLiteDatabase?) {
        val queryCustomer =
            "create table $CUSTOMER_TABLE($CUSTOMER_ID integer not null primary key autoincrement unique, $CUSTOMER_NAME text not null, $CUSTOMER_NUMBER text not null, $CUSTOMER_ADDRESS text not null)"
        p0?.execSQL(queryCustomer)

        val queryEmployee =
            "create table $EMPLOYEE_TABLE($EMPLOYEE_ID integer not null primary key autoincrement unique, $EMPLOYEE_NAME text not null, $EMPLOYEE_NUMBER text not null)"
        p0?.execSQL(queryEmployee)

        val queryOrders =
            "create table $ORDER_TABLE($ORDER_ID integer not null primary key autoincrement unique, $ORDER_NAME text not null, $ORDER_DATE text not null, $ORDER_PRICE integer not null, $ORDER_CUSTOMER_ID integer not null, $ORDER_EMPLOYEE_ID integer not null, " +
                    "foreign key ($ORDER_CUSTOMER_ID) references $CUSTOMER_TABLE($CUSTOMER_ID), " +
                    "foreign key ($ORDER_EMPLOYEE_ID) references $EMPLOYEE_TABLE($EMPLOYEE_ID))"
        p0?.execSQL(queryOrders)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {

    }

    override fun addCustomer(myCustomer: MyCustomer) {
        val database = writableDatabase
        val contentValues = ContentValues()
        contentValues.put(CUSTOMER_NAME, myCustomer.name)
        contentValues.put(CUSTOMER_NUMBER, myCustomer.number)
        contentValues.put(CUSTOMER_ADDRESS, myCustomer.address)
        database.insert(CUSTOMER_TABLE, null, contentValues)
        database.close()
    }

    override fun getAllCustomer(): List<MyCustomer> {
        val list = ArrayList<MyCustomer>()
        val database = readableDatabase
        val query = "select * from $CUSTOMER_TABLE"
        val cursor = database.rawQuery(query, null)
        if (cursor.moveToFirst()) {
            do {
                list.add(
                    MyCustomer(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3)
                    )
                )
            } while (cursor.moveToNext())
        }
        return list
    }

    override fun addEmployee(myEmployee: MyEmployee) {
        val database = writableDatabase
        val contentValues = ContentValues()
        contentValues.put(EMPLOYEE_NAME, myEmployee.name)
        contentValues.put(EMPLOYEE_NUMBER, myEmployee.number)
        database.insert(EMPLOYEE_TABLE, null, contentValues)
        database.close()
    }

    override fun getAllEmployee(): List<MyEmployee> {
        val customerList = ArrayList<MyEmployee>()

        val database = readableDatabase
        val query = "select * from $EMPLOYEE_TABLE"
        val cursor = database.rawQuery(query, null)
        if (cursor.moveToFirst()) {
            do {
                customerList.add(
                    MyEmployee(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                    )
                )
            } while (cursor.moveToNext())
        }
        return customerList
    }

    override fun addOrder(myOrder: MyOrder) {
        val database = writableDatabase
        val contentValues = ContentValues()
        contentValues.put(ORDER_NAME, myOrder.name)
        contentValues.put(ORDER_DATE, myOrder.date)
        contentValues.put(ORDER_PRICE, myOrder.price)
        contentValues.put(ORDER_EMPLOYEE_ID, myOrder.myEmployee?.id)
        contentValues.put(ORDER_CUSTOMER_ID, myOrder.myCustomer?.id)
        database.insert(ORDER_TABLE, null, contentValues)
        database.close()
    }

    override fun getALlOrders(): ArrayList<MyOrder> {
        val list = ArrayList<MyOrder>()
        val database = readableDatabase
        val query = "select * from $ORDER_TABLE"
        val cursor = database.rawQuery(query, null)
        if (cursor.moveToFirst()) {
            do {
                list.add(
                    MyOrder(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getInt(3),
                        getCustomerById(cursor.getInt(4)),
                        getEmployeeById(cursor.getInt(5))
                    )
                )
            }while (cursor.moveToNext())
        }
        return list
    }

    override fun getEmployeeById(id: Int): MyEmployee {
        val database = readableDatabase

        /** cursor ga database dan o'qib olingan ma'lumotlarni beramiz, Bu yerda bitta so'rov bo'yicha ma'lumotlarni o'qib olamiz*/
        val cursor = database.query(
            EMPLOYEE_TABLE,
            arrayOf(EMPLOYEE_ID, EMPLOYEE_NAME, EMPLOYEE_NUMBER),
            "$EMPLOYEE_ID = ?",
            /** arrayOf olishimizga sabab id lar ko'p bo'lishi mumkun*/
            arrayOf(id.toString()),
            null, null, null
        )
        cursor.moveToFirst()
        val myEmployee = MyEmployee(
            cursor.getInt(0),
            cursor.getString(1),
            cursor.getString(2)
        )
        return myEmployee
    }

    override fun getCustomerById(id: Int): MyCustomer {
        val database = readableDatabase

        /** cursor ga database dan o'qib olingan ma'lumotlarni beramiz, Bu yerda bitta so'rov bo'yicha ma'lumotlarni o'qib olamiz*/
        val cursor = database.query(
            CUSTOMER_TABLE,
            arrayOf(CUSTOMER_ID, CUSTOMER_NAME, CUSTOMER_NUMBER, CUSTOMER_ADDRESS),
            "$CUSTOMER_ID = ?",
            /** arrayOf olishimizga sabab id lar ko'p bo'lishi mumkun*/
            arrayOf(id.toString()),
            null, null, null
        )
        cursor.moveToFirst()
        val myCustomer = MyCustomer(
            cursor.getInt(0),
            cursor.getString(1),
            cursor.getString(2),
            cursor.getString(3)
        )
        return myCustomer
    }
}