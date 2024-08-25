package tech.kekulta.navigation

import android.view.MenuItem
import androidx.core.view.forEach
import androidx.core.view.iterator
import com.google.android.material.bottomnavigation.BottomNavigationView

fun BottomNavigationView.getItems(): List<MenuItem> {
    val i = menu.iterator()

    return buildList {
        while (i.hasNext()) {
            add(i.next())
        }
    }
}

interface BottomNavigator<D : Enum<D>> {
    fun setOnNavigatedListener(listener: ((dest: D?, root: D?) -> Unit))
    fun openTab(destination: D)
    fun destinationToItem(destination: D): Int
    fun itemToDestination(item: Int): D
}

fun <D : Enum<D>> BottomNavigationView.setupWithBottomNavigator(
    navigator: BottomNavigator<D>,
) {
    navigator.setOnNavigatedListener { _, root ->
        if (root == null) {
            menu.forEach { it.setChecked(false) }
        } else {
            getItems()[navigator.destinationToItem(root)].setChecked(true)
        }
    }

    setOnItemSelectedListener { item ->
        navigator.openTab(navigator.itemToDestination(getItems().indexOf(item)))
        false
    }
}
