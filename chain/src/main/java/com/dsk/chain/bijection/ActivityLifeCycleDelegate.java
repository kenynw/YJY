package com.dsk.chain.bijection;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.PersistableBundle;

/**
 * Copyright (c) 2015. LiaoPeiKun Inc. All rights reserved.
 */
public class ActivityLifeCycleDelegate {

    private Activity mActivity;

    public ActivityLifeCycleDelegate(Activity activity) {
        mActivity = activity;
    }

    public Activity getActivity() {
        return mActivity;
    }

    protected void onCreate(Bundle savedInstanceState) {
    }

    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
    }

    protected void onRestoreInstanceState(Bundle savedInstanceState) {
    }

    public void onRestoreInstanceState(Bundle savedInstanceState, PersistableBundle persistentState) {
    }

    protected void onPostCreate(Bundle savedInstanceState) {
    }

    protected void onStart() {
    }

    public void onPostCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
    }

    protected void onRestart() {
    }

    protected void onResume() {
    }

    protected void onPostResume() {
    }

    protected void onNewIntent(Intent intent) {
    }

    protected void onSaveInstanceState(Bundle outState) {
    }

    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
    }

    protected void onPause() {
    }

    protected void onUserLeaveHint() {
    }

    protected void onStop() {
    }

    protected void onDestroy() {
    }

    public void onConfigurationChanged(Configuration newConfig) {
    }

    public void onLowMemory() {
    }

    public void onTrimMemory(int level) {
    }

    public void onAttachFragment(Fragment fragment) {
    }

    public void onBackPressed() {
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {

    }

    public void onContentChanged() {

    }

    public void onResumeFragments() {

    }
}
