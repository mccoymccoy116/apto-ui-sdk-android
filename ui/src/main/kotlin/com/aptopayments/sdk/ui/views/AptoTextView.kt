package com.aptopayments.sdk.ui.views

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import com.aptopayments.mobile.extension.localized
import com.aptopayments.sdk.R

class AptoTextView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    AppCompatTextView(context, attrs, defStyleAttr) {

    var localizedText: String = ""
        set(value) = localizeAndSet(value)

    init {
        attrs?.let {
            val typedArray =
                context.obtainStyledAttributes(it, R.styleable.AptoTextView, 0, 0)

            val localize = typedArray.getString(R.styleable.AptoTextView_localize)

            if (!localize.isNullOrEmpty()) {
                text = if (isInEditMode) {
                    getEditModeStringResource(localize)
                } else {
                    localize.localized()
                }
            }
            typedArray.recycle()
        }
    }

    private fun localizeAndSet(value: String) {
        text = value.localized()
    }

    private fun getEditModeStringResource(localize: String): String {
        return try {
            val packageName = this.context.packageName
            val resId = resources.getIdentifier(localize, "string", packageName)
            this.context.getString(resId)
        } catch (ex: Exception) {
            ""
        }
    }
}