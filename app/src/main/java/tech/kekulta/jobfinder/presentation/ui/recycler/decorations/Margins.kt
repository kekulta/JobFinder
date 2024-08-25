package tech.kekulta.jobfinder.presentation.ui.recycler.decorations

import androidx.annotation.Px

data class Margins(
    @Px val marginTop: Int = 0,
    @Px val marginRight: Int = 0,
    @Px val marginBottom: Int = 0,
    @Px val marginLeft: Int = 0,
) {
    constructor(@Px marginVertical: Int = 0, @Px marginHorizontal: Int = 0) : this(
        marginVertical,
        marginHorizontal,
        marginVertical,
        marginHorizontal,
    )

    constructor(@Px margin: Int = 0) : this(margin, margin)
}