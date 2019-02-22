package com.github.icarohs7.unoxandroidarch.extensions

import android.text.Editable
import android.text.TextWatcher
import arrow.core.Try
import com.github.icarohs7.unoxandroid.extensions.orEmpty
import com.santalu.maskedittext.MaskEditText

/**
 * Map of stored masks per edit text id
 */
val masks: MutableMap<Int, MutableMap<IntRange, String>> = mutableMapOf()

/**
 * Map storing the masks of the edit text
 */
val MaskEditText.adaptativeMasks: MutableMap<IntRange, String>
    get() {
        return if (masks[this.id] == null) {
            masks += this.id to mutableMapOf()
            masks[this.id]!!
        } else {
            masks[this.id]!!
        }
    }

/**
 * Add a mask to the list of masks in the edit text, applying it when
 * the length of the unmasked field is lesser or equal than the parameterized
 */
fun MaskEditText.addAdaptativeMask(lengthRange: IntRange, mask: String) {
    val maskWithExtraChar = mask.formattedMask()
    this.adaptativeMasks += lengthRange to maskWithExtraChar
}

/**
 * Add an extra masked character to the mask
 * to allow the increasing of the text
 */
private fun String?.formattedMask(): String {
    this ?: return ""
    val indexOfLastMask = indexOfLast { it == '#' }
    return substring(0..indexOfLastMask) + "#" + Try { substring(indexOfLastMask + 1) }.orEmpty()
}

/**
 * Remove the last masked character from the mask
 */
private fun String?.unformattedMask(): String {
    this ?: return ""
    val indexOfLastMask = indexOfLast { it == '#' }
    return substring(0 until indexOfLastMask) + Try { substring(indexOfLastMask + 1) }.orEmpty()
}

/**
 * Enable multiple masks on a masked edit text
 */
fun MaskEditText.enableAdaptativeMask(maxLength: Int = Int.MAX_VALUE) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            val field = this@enableAdaptativeMask
            val text = field.rawText ?: ""

            val range = field.adaptativeMasks.keys.find { text.length in it } ?: return
            val newMask = field.adaptativeMasks[range]

            if (textHasMaxLength(text)) {
                useFullMaskAtField(field, newMask)
            } else if (fieldHasDifferentMaskThan(field, newMask)) {
                field.mask = newMask
            }
        }

        private fun useFullMaskAtField(field: MaskEditText, newMask: String?) {
            if (fieldHasDifferentMaskThan(field, newMask.unformattedMask())) {
                field.mask = newMask.unformattedMask()
            }
        }

        private fun fieldHasDifferentMaskThan(field: MaskEditText, newMask: String?) =
                field.mask != newMask

        private fun textHasMaxLength(text: String) = text.length == maxLength

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        }
    })
}