package com.example.myapplication.activity

import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.myapplication.R
import com.xiaoxin.sdk.Xiaoxin
import com.xiaoxin.sdk.call.OnCallListener
import com.xiaoxin.sdk.data.Person
import com.xiaoxin.sdk.data.Result
import kotlinx.android.synthetic.main.activity_contacts.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.toast

class ContactsActivity : AppCompatActivity() {

    private lateinit var adapter: BaseQuickAdapter<Person, BaseViewHolder>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contacts)

        autoPlay.isChecked = Xiaoxin.isAutoPlayMessage()
        autoPlay.setOnCheckedChangeListener { _, isChecked ->
            Xiaoxin.setAutoPlayMessage(isChecked)
        }

        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        adapter = object :
            BaseQuickAdapter<Person, BaseViewHolder>(android.R.layout.simple_list_item_1, null) {
            override fun convert(helper: BaseViewHolder, item: Person) {
                helper.setText(android.R.id.text1, item.name)
            }
        }
        adapter.setOnItemLongClickListener { adapter, _, position ->
            val person = adapter.getItem(position) as Person
            person.toUserInfo()
            val id: String = person.id ?: ""
            AlertDialog.Builder(this).setItems(arrayOf("删除")) { dialog, _ ->
                dialog.dismiss()
                removeContact(id)
            }.show()
            true
        }
        recyclerView.adapter = adapter
        getContacts()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menu.add(Menu.NONE, 1, 0, "添加好友")
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            1 -> {
                alert {
                    val editText = EditText(this@ContactsActivity)
                    positiveButton("添加") {
                        it.dismiss()
                        val imei = editText.text.toString()
                        addContact(imei)
                    }
                    negativeButton("取消") { it.dismiss() }
                    customView = editText
                }.show()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun getContacts() {
        Xiaoxin.getContacts(Xiaoxin.getUniqueid(), object : OnCallListener<List<Person>> {
            override fun onSuccess(t: List<Person>) {
                if (!isDestroyed) {
                    adapter.replaceData(t)
                }
            }

            override fun onError(throwable: Throwable) {
                if (!isDestroyed) {
                    toast(throwable.message ?: "")
                }
            }
        })
    }

    private fun addContact(imei: String) {
        Xiaoxin.addContact(imei, Xiaoxin.getUniqueid(),
            object : OnCallListener<Result> {
                override fun onSuccess(t: Result) {
                    if (!isDestroyed) {
                        toast(t.toString())
                        getContacts()
                    }
                }

                override fun onError(throwable: Throwable) {
                    if (!isDestroyed) {
                        toast(throwable.message ?: "")
                    }
                }
            })
    }


    private fun removeContact(id: String) {
        Xiaoxin.removeContact(id, Xiaoxin.getUniqueid(), object : OnCallListener<Result> {
            override fun onSuccess(t: Result) {
                if (!isDestroyed) {
                    toast(t.toString())
                    getContacts()
                }
            }

            override fun onError(throwable: Throwable) {
                if (!isDestroyed) {
                    toast(throwable.message ?: throwable.toString())
                }
            }

        })
    }
}
