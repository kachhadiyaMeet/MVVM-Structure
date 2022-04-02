package com.dev.mvvmex

import android.app.ProgressDialog
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import com.dev.mvvmex.utils.getProgressDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
abstract class BaseActivity : AppCompatActivity()/*, View.OnClickListener*/ {

    lateinit var mActivity: FragmentActivity
    lateinit var mProgressDialog: ProgressDialog

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun setContentView(layoutResID: Int) {
        super.setContentView(layoutResID)
        mActivity = this
        mProgressDialog = mActivity.getProgressDialog
        initView()
        initAds()
        initViewAction()
        initViewListener()
    }

    /**
     * If You Using viewBinding
     * @param view pass Your Layout File Lick 'ActivityMainBinding.inflate(getLayoutInflater()).getRoot()'
     */
    override fun setContentView(view: View) {
        super.setContentView(view)
        mActivity = this
        mProgressDialog = mActivity.getProgressDialog
        initView()
        initAds()
        initViewAction()
        initViewListener()
    }

    /**
     * For Init All Ads.
     */
    open fun initAds() {}

    /**
     * For Init Your Layout File's All View
     */
    open fun initView() {}

    /**
     * For Init Your Default Action Performance On View
     */
    open fun initViewAction() {}

    /**
     * For Set Your All Type Of Listeners
     */
    open fun initViewListener() {}


}