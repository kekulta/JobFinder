package tech.kekulta.jobfinder.presentation.ui.recycler.decorations

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration

class RecyclerViewMargins(
    private val adapter: RecyclerView.Adapter<*>,
    private val margins: List<Margins>,
) : ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State
    ) {
        val position = parent.getChildAdapterPosition(view)
        if (position == RecyclerView.NO_POSITION) return
        val type = adapter.getItemViewType(position)
        val margin = margins.getOrNull(type) ?: Margins()

        outRect.top = margin.marginTop
        outRect.right = margin.marginRight
        outRect.bottom = margin.marginBottom
        outRect.left = margin.marginLeft
    }
}