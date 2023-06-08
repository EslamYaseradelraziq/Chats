package com.example.chatapplication.base

import android.app.ProgressDialog

import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel


abstract class BaseActivity<vb : ViewDataBinding,
        vm : BaseViewModel<*>> : AppCompatActivity(), BaseNavigator {
    lateinit var viewBinding: vb
    lateinit var viewModel: vm
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = DataBindingUtil.setContentView(this, getLayoutId())
        viewModel = generateViewModel()

    }

    abstract fun generateViewModel(): vm
    abstract fun getLayoutId(): Int

    var alertDialog: AlertDialog? = null
    var progressDoialog: ProgressDialog? = null
    override fun showMessage(
        message: String,
        posActionTitle: String?,
        posAction: OnDialogClickListener?,
        negActionTitle: String?,
        negAction: OnDialogClickListener?
    ) {
        val builder =
            AlertDialog.Builder(this)
                .setMessage(message)
        if (posActionTitle != null) {
            builder.setPositiveButton(posActionTitle) { dialogInterface, i ->
                dialogInterface.dismiss()
                posAction?.onClick()


            }
        }
        if (negActionTitle != null) {
            builder.setNegativeButton(negActionTitle) { dialogInterface, i ->
                dialogInterface.dismiss()
                negAction?.onClick()


            }

        }
        alertDialog = builder.show()
    }

    override fun hideDialogue() {
        alertDialog?.dismiss()
        progressDoialog?.dismiss()
        progressDoialog = null
    }

    override fun showLoading(message: String) {
        progressDoialog = ProgressDialog(this)
        progressDoialog?.setMessage(message)
        progressDoialog?.show()
    }


}


interface BaseNavigator {
    fun showMessage(
        message: String,
        posActionTitle: String? = null,
        posAction: OnDialogClickListener? = null,
        negActionTitle: String? = null,
        negAction: OnDialogClickListener? = null
    )

    fun hideDialogue()
    fun showLoading(message: String)

}

open class BaseViewModel<N : BaseNavigator> : ViewModel() {
    var navigator: N? = null

}