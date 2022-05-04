package com.example.petme.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

abstract class BaseFragment<BIND : ViewBinding> : Fragment() {

    protected lateinit var bind : BIND
    protected lateinit var mCtx : Context

    override fun onCreateView(inflater : LayoutInflater, container : ViewGroup?, savedInstanceState : Bundle?) : View? {
        bind = getBind(inflater , container)
        mCtx = inflater.context

        return bind.root
    }


    abstract fun getBind(inflater : LayoutInflater , container : ViewGroup?) : BIND
}