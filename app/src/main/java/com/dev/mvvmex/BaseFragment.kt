package com.dev.mvvmex

import android.app.ProgressDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.dev.mvvmex.utils.getProgressDialog

/**
 * BaseFragment.kt - A simple class contains some modifications to the native fragment.
 * @author  Meet Kachhadiya
 * @date 24-11-2021
 */

abstract class BaseFragment : Fragment(), View.OnClickListener {

    lateinit var mContext: FragmentActivity // Context of the current activity
    lateinit var mProgressDialog: ProgressDialog
    lateinit var mView: View

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mView = inflater.inflate(getLayoutId(), container, false)
        return mView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mContext = requireActivity()
        mProgressDialog = mContext.getProgressDialog
        initAds()
        initData()
        initActions()
        initListener()
    }

    abstract fun getLayoutId(): Int

    /**
     * ToDo. Use this method to setup ads.
     */
    protected open fun initAds() {}

    /**
     * ToDo. Use this method to initialize data to view components.
     */

    abstract fun initData()

    /**
     * ToDo. Use this method to initialize action on view components.
     */
    open fun initActions() {}

    /**
     * For Set Your All Type Of Listeners
     */
    open fun initListener() {}


}
