package tech.kekulta.navigation

import androidx.annotation.AnimRes
import androidx.annotation.AnimatorRes

data class Animations(
    @AnimatorRes @AnimRes val enter: Int,
    @AnimatorRes @AnimRes val exit: Int,
    @AnimatorRes @AnimRes val popEnter: Int,
    @AnimatorRes @AnimRes val popExit: Int,
) {
    constructor(
        @AnimatorRes @AnimRes enter: Int,
        @AnimatorRes @AnimRes exit: Int,
    ) : this(enter, exit, enter, exit)
}