package com.dicoding.warnapedia.ui.settings

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.dicoding.warnapedia.R

class CustomSpinnerAdapter(context: Context, resource: Int, objects: Array<out Any>) :
    ArrayAdapter<Any>(context, resource, objects) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = super.getView(position, convertView, parent)
        val itemText = getItem(position) as? String
        val firstWord = itemText?.split(" ")?.get(0)
        (view as? TextView)?.text = firstWord
        return view
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = super.getDropDownView(position, convertView, parent)
        val itemText = getItem(position) as? String
        (view as? TextView)?.text = itemText
        return view
    }
}