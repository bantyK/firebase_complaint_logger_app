package com.banty.firebasedb

import android.os.Bundle
import android.text.Html
import android.view.Menu
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment

open class BaseFragment: Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        val menuItem = menu.findItem(R.id.menu_logout)
        menuItem?.isVisible = false
    }

    fun setupRichText(
        view: TextView?,
        i: Int,
        onClick: (View) -> Unit
    ) {
        view?.text = Html.fromHtml(getString(i))
        view?.setOnClickListener(onClick)
    }
}