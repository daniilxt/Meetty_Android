package ru.daniilxt.common.widgets.masked_edit_text

import android.content.Context
import android.util.AttributeSet
import br.com.sapereaude.maskedEditText.MaskedEditText

// By default lib haven't imm action method
class MaskedEditText @JvmOverloads constructor(context: Context, attrs: AttributeSet?) :
    MaskedEditText(context, attrs) {
    init {
        this.setOnEditorActionListener { _, _, _ ->
            false
        }
    }
}
