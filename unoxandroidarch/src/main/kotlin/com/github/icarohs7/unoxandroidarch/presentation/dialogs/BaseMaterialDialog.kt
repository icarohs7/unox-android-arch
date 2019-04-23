package com.github.icarohs7.unoxandroidarch.presentation.dialogs

import android.content.Context
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.callbacks.onCancel
import com.afollestad.materialdialogs.callbacks.onDismiss
import com.afollestad.materialdialogs.customview.customView
import com.github.icarohs7.unoxcore.extensions.coroutines.cancelCoroutineScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import org.jetbrains.anko.layoutInflater

/**
 * Base class used to hold and handle
 * dialogs
 */
abstract class BaseMaterialDialog<T : ViewDataBinding>(
        protected val context: Context,
        @LayoutRes dialogLayout: Int
) : CoroutineScope by MainScope() {
    val binding: T by lazy {
        DataBindingUtil.inflate<T>(context.layoutInflater, dialogLayout, null, false)
                .also { launch { onCreateBinding() } }
    }
    val dialog: MaterialDialog by lazy {
        MaterialDialog(context)
                .customView(view = binding.root, noVerticalPadding = true)
                .onCancel { onCancel() }
                .onDismiss { onDismiss() }
    }

    fun show() {
        dialog.show()
    }

    /**
     * Called after the binding is created,
     * either by a need of the dialog or for
     * another reason
     */
    abstract suspend fun onCreateBinding()

    open fun onCancel() {
    }

    open fun onDismiss() {
        cancelCoroutineScope()
    }
}