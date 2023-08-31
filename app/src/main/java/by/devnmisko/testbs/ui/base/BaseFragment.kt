package by.devnmisko.testbs.ui.base

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import by.devnmisko.testbs.R

abstract class BaseFragment<ViewBindingType : ViewBinding> : Fragment() {
    private var _binding: ViewBindingType? = null
    val binding: ViewBindingType
    get() = _binding as ViewBindingType
    abstract val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> ViewBindingType

    override fun onCreateView(
         inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
         _binding = bindingInflater.invoke(inflater, container, false)
        return requireNotNull(_binding).root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
    }

    abstract fun setupUI()

    protected fun buildErrorDialog(message: String?): AlertDialog.Builder {
        val errorDialog = AlertDialog.Builder(context)
        errorDialog.apply {
            setTitle(getString(R.string.error))
            setMessage(message)
            setPositiveButton(
                getString(R.string.ok)
            ) { dialog, _ -> dialog.dismiss() }
            create()
        }
        return errorDialog
    }

    protected fun buildProgressDialog(): AlertDialog {
        val builder: AlertDialog.Builder = AlertDialog.Builder(context)
        val customLayout: View =
            layoutInflater.inflate(R.layout.progress_dialog, null)
        builder.setView(customLayout)
        val dialog = builder.create()
        dialog.setCanceledOnTouchOutside(false)
        return dialog
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}