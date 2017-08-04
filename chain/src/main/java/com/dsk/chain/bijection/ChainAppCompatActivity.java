package com.dsk.chain.bijection;

import android.app.Fragment;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import com.dsk.chain.Chain;
import com.gyf.barlibrary.ImmersionBar;


/**
 * Copyright (c) 2015. LiaoPeiKun Inc. All rights reserved.
 */
public abstract class ChainAppCompatActivity<PresenterType extends Presenter> extends AppCompatActivity {

    private ActivityLifeCycleDelegate mLifeCycleDelegate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preCreatePresenter();
        if (mLifeCycleDelegate != null) mLifeCycleDelegate.onCreate(savedInstanceState);
        mHelper.onCreate(savedInstanceState);
    }

    public void preCreatePresenter() {
        mLifeCycleDelegate = Chain.createActivityLifeCycleDelegate(this);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        if (mLifeCycleDelegate != null) mLifeCycleDelegate.onPostCreate(savedInstanceState);
        mHelper.onPostCreate();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mLifeCycleDelegate != null) mLifeCycleDelegate.onDestroy();
        mHelper.onDestroyView();
        if (isFinishing())
            mHelper.onDestroy();
        ImmersionBar.with(this).destroy();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mLifeCycleDelegate != null) mLifeCycleDelegate.onSaveInstanceState(outState);
        mHelper.onSave(outState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mLifeCycleDelegate != null) mLifeCycleDelegate.onResume();
        mHelper.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mLifeCycleDelegate != null) mLifeCycleDelegate.onPause();
        mHelper.onPause();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (mLifeCycleDelegate != null) mLifeCycleDelegate.onActivityResult(requestCode, resultCode, data);
        mHelper.onResult(requestCode, resultCode, data);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (mLifeCycleDelegate!=null) mLifeCycleDelegate.onNewIntent(intent);
        mHelper.onNewIntent(intent);
    }

    public PresenterType getPresenter() {
        return mHelper.getPresenter();
    }

    private ViewHelper<PresenterType> mHelper = new ViewHelper<>(this);

    @Override
    public void onContentChanged() {
        super.onContentChanged();
        if (mLifeCycleDelegate!=null)mLifeCycleDelegate.onContentChanged();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mLifeCycleDelegate!=null)mLifeCycleDelegate.onStart();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (mLifeCycleDelegate!=null) mLifeCycleDelegate.onRestart();
    }

    @Override
    public void onPostCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onPostCreate(savedInstanceState, persistentState);
        if (mLifeCycleDelegate!=null) mLifeCycleDelegate.onPostCreate(savedInstanceState, persistentState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (mLifeCycleDelegate!=null) mLifeCycleDelegate.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onRestoreInstanceState(savedInstanceState, persistentState);
        if (mLifeCycleDelegate!=null) mLifeCycleDelegate.onRestoreInstanceState(savedInstanceState, persistentState);
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        if (mLifeCycleDelegate!=null) mLifeCycleDelegate.onSaveInstanceState(outState, outPersistentState);
    }

    @Override
    protected void onUserLeaveHint() {
        super.onUserLeaveHint();
        if (mLifeCycleDelegate!=null) mLifeCycleDelegate.onUserLeaveHint();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        if (mLifeCycleDelegate!=null) mLifeCycleDelegate.onLowMemory();
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        if (mLifeCycleDelegate!=null) mLifeCycleDelegate.onTrimMemory(level);
    }

    @Override
    public void onAttachFragment(Fragment fragment) {
        super.onAttachFragment(fragment);
        if (mLifeCycleDelegate!=null) mLifeCycleDelegate.onAttachFragment(fragment);
    }

    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
        if (mLifeCycleDelegate!=null) mLifeCycleDelegate.onResumeFragments();
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (mLifeCycleDelegate!=null) mLifeCycleDelegate.onBackPressed();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mLifeCycleDelegate!=null) mLifeCycleDelegate.onStop();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        if (mLifeCycleDelegate!=null) mLifeCycleDelegate.onPostResume();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (mLifeCycleDelegate!=null) mLifeCycleDelegate.onConfigurationChanged(newConfig);
    }
}
