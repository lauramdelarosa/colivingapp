package com.naez.colivingapp.ui.spacesDetail

import android.content.Context
import android.util.AttributeSet
import android.widget.TextView
import androidx.core.text.buildSpannedString
import com.naez.domain.Space

class SpaceDetailInfoView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : TextView(context, attrs, defStyleAttr) {

    fun setSpace(space: Space) = with(space) {
        text = buildSpannedString {

        }
    }
}