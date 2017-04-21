/*
 * Copyright (C) 2016 Nishant Srivastava
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.miguan.yjy.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static android.content.ContentValues.TAG;
import static android.view.View.MeasureSpec;

/**
 * View截图工具类
 */
public class ScreenShot {

    private static final ScreenShot ourInstance = new ScreenShot();


    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static ScreenShot getInstance() {
        return ourInstance;
    }

    private ScreenShot() {
    }

    /**
     * Take screen shot of the View with spaces as per constraints
     *
     * @param v the v
     * @return the bitmap
     */
    public Bitmap takeScreenShotOfView(View v) {
        Bitmap bitmap = Bitmap.createBitmap(v.getWidth(), v.getHeight(), Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        v.draw(canvas);
        return bitmap;
    }

    /**
     * Take screen shot of root view.
     *
     * @param v the v
     * @return the bitmap
     */
    public Bitmap takeScreenShotOfRootView(View v) {
        v = v.getRootView();
        return takeScreenShotOfView(v);
    }

    /**
     * Take screen shot of just the View without any constraints
     *
     * @param v the v
     * @return the bitmap
     */
    public void takeScreenShotOfJustView(View v, OnSaveListener listener) {
        v.measure(MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED),
                MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
        v.layout(0, 0, v.getMeasuredWidth(), v.getMeasuredHeight());
        Bitmap bitmap = takeScreenShotOfView(v);
//        new SaveTask(v.getContext(), bitmap, listener).execute();
    }

    public void saveRecyclerViewScreenshot(RecyclerView view , OnSaveListener listener) {
        RecyclerArrayAdapter adapter = (RecyclerArrayAdapter) view.getAdapter();

        int bitmapHeight = 0;

        // 测量Header
        View header = null;
        if (adapter.getHeader(0) != null) {
            header = view.getChildAt(0);
            measureView(header, view.getWidth());
            bitmapHeight = bitmapHeight + header.getHeight();
        }

        // 测量Item
        BaseViewHolder holder = adapter.onCreateViewHolder(view, 0);
        adapter.onBindViewHolder(holder, 0);
        measureView(holder.itemView, view.getMeasuredWidth());
        bitmapHeight = bitmapHeight + holder.itemView.getMeasuredHeight() * adapter.getCount();

        // 测量footer
        View footer = null;
        if (adapter.getFooter(0) != null) {
            footer = adapter.getFooter(0).onCreateView(view);
            measureView(footer, view.getWidth());
            bitmapHeight = bitmapHeight + footer.getHeight();
        }

        Bitmap bigBitmap = Bitmap.createBitmap(view.getMeasuredWidth(), bitmapHeight, Bitmap.Config.ARGB_8888);
        Canvas bigCanvas = new Canvas(bigBitmap);
        bigCanvas.drawColor(Color.WHITE);
        Paint paint = new Paint();

        int iHeight = 0;

        if (header != null) {
            header.setDrawingCacheEnabled(true);
            header.buildDrawingCache();
            bigCanvas.drawBitmap(header.getDrawingCache(), 0f, iHeight, paint);
            header.setDrawingCacheEnabled(false);
            header.destroyDrawingCache();
            iHeight += header.getMeasuredHeight();
        }

        for (int i = 0; i < adapter.getCount(); i++) {
            adapter.onBindViewHolder(holder, i);
            holder.itemView.setDrawingCacheEnabled(true);
            holder.itemView.buildDrawingCache();
            bigCanvas.drawBitmap(holder.itemView.getDrawingCache(), 0f, iHeight, paint);
            iHeight += holder.itemView.getMeasuredHeight();
            holder.itemView.setDrawingCacheEnabled(false);
            holder.itemView.destroyDrawingCache();
        }

        if (footer != null) {
            footer.setDrawingCacheEnabled(true);
            footer.buildDrawingCache();
            bigCanvas.drawBitmap(footer.getDrawingCache(), 0f, iHeight, paint);
            footer.setDrawingCacheEnabled(false);
            footer.destroyDrawingCache();
        }

        saveScreenshotToPicturesFolder(view.getContext(), bigBitmap, listener);
    }

    /**
     * Save screenshot to pictures folder.
     *
     * @param context  the context
     * @param image    the image
     */
    public void saveScreenshotToPicturesFolder(Context context, Bitmap image, OnSaveListener listener) {
        new SaveTask(context, image, listener).execute();
    }

    private void measureView(View view, int width) {
        view.measure(View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.EXACTLY),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
    }

    private class SaveTask extends AsyncTask<View, Void, Void> {

        private Context mContext;

        private Bitmap mBitmap;

        private OnSaveListener mListener;

        private Handler mHandler;

        public SaveTask(Context context, Bitmap bitmap, OnSaveListener listener) {
            mContext = context;
            mBitmap = bitmap;
            mListener = listener;
            mHandler = new Handler();
        }

        @Override
        protected Void doInBackground(View... params) {
            saveScreenshotToPicturesFolder(mBitmap, "yjy");
            return null;
        }

        /**
         * Save screenshot to pictures folder.
         *
         * @param image    the image
         * @param filename the filename
         */
        private void saveScreenshotToPicturesFolder(Bitmap image, String filename) {
            File bitmapFile = getOutputMediaFile(filename);
            if (bitmapFile == null) {
                Log.d(TAG, "Error creating media file, check storage permissions: ");// e.getMessage());
                return ;
            }
            try {
                FileOutputStream fos = new FileOutputStream(bitmapFile);
                image.compress(Bitmap.CompressFormat.PNG, 90, fos);
                fos.flush();
                fos.close();

                // Initiate media scanning to make the image available in gallery apps
                MediaScannerConnection.scanFile(mContext, new String[]{bitmapFile.getPath()},
                        new String[]{"image/jpeg"}, (path, uri) -> {
                            if (mListener != null) {
                                mHandler.post(() -> mListener.onPictureSaved(uri));
                            }
                        });
            } catch (FileNotFoundException e) {
                Log.d(TAG, "File not found: " + e.getMessage());
            } catch (IOException e) {
                Log.d(TAG, "Error accessing file: " + e.getMessage());
            }
        }

        private File getOutputMediaFile(String filename) {
            // 检查存储卡是否可用
            if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                return null;
            }
            File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);

            File mediaStorageDirectory = new File(path, "YJY/" + filename);
            // Create the storage directory if it does not exist
            if (!mediaStorageDirectory.exists()) {
                if (!mediaStorageDirectory.mkdirs()) {
                    return null;
                }
            }
            // Create a media file name
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmm", Locale.CHINA).format(new Date());
            File mediaFile;
            String mImageName = filename + timeStamp + ".jpg";
            mediaFile = new File(mediaStorageDirectory.getPath() + File.separator + mImageName);
            return mediaFile;
        }

    }

    public interface OnSaveListener {
        void onPictureSaved(Uri uri);
    }

}
