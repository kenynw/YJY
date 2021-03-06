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
import android.graphics.Matrix;
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
import java.lang.ref.SoftReference;
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
    public Bitmap takeScreenShotOfView(View v, Bitmap.Config config) {
        Bitmap bitmap = Bitmap.createBitmap(v.getWidth(), v.getHeight(), config);
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
        return takeScreenShotOfView(v, Bitmap.Config.RGB_565);
    }

    /**
     * Take screen shot of just the View without any constraints
     *
     * @param v the v
     * @return the bitmap
     */
    public Bitmap takeScreenShotOfJustView(View v) {
        v.measure(MeasureSpec.makeMeasureSpec(LUtils.getScreenWidth(), MeasureSpec.EXACTLY),
                MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
        v.layout(0, 0, v.getMeasuredWidth(), v.getMeasuredHeight());
        return takeScreenShotOfView(v, Bitmap.Config.RGB_565);
    }

    /**
     * Take screen shot of just the View without any constraints
     *
     * @param v the v
     * @return the bitmap
     */
    public Bitmap takeScreenShotOfJustView(View v, Bitmap.Config config) {
        v.measure(MeasureSpec.makeMeasureSpec(LUtils.getScreenWidth(), MeasureSpec.EXACTLY),
                MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
        v.layout(0, 0, v.getMeasuredWidth(), v.getMeasuredHeight());
        return takeScreenShotOfView(v, config);
    }

    /**
     * Take screen shot of just the View without any constraints
     *
     * @param v the v
     * @return the bitmap
     */
    public void takeScreenShotOfJustView(View v, OnSaveListener listener) {
        Bitmap bitmap = takeScreenShotOfJustView(v, Bitmap.Config.RGB_565);
        new SaveTask(v.getContext(), bitmap, listener).execute();
    }

    /**
     * 自定义图片质量
     *
     * @param v the v
     * @return the bitmap
     */
    public void takeScreenShotOfJustView(View v, Bitmap.Config config, OnSaveListener listener) {
        Bitmap bitmap = takeScreenShotOfJustView(v, config);
        new SaveTask(v.getContext(), bitmap, listener).execute();
    }

    public void saveRecyclerViewScreenshot(RecyclerView view, OnSaveListener listener) {
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
     * @param context the context
     * @param image   the image
     */
    public void saveScreenshotToPicturesFolder(Context context, Bitmap image, OnSaveListener listener) {
        new SaveTask(context, image, listener).execute();
    }

    public void measureView(View view, int width) {
        view.measure(View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.EXACTLY),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
    }

    private class SaveTask extends AsyncTask<View, Void, Void> {

        private Context mContext;

        private SoftReference<Bitmap> mBitmap;

        private OnSaveListener mListener;

        private Handler mHandler;

        public SaveTask(Context context, Bitmap bitmap, OnSaveListener listener) {
            mContext = context;
            mBitmap = new SoftReference<Bitmap>(bitmap);
            mListener = listener;
            mHandler = new Handler();
        }

        @Override
        protected Void doInBackground(View... params) {
            saveScreenshotToPicturesFolder();
            return null;
        }

        /**
         * Save screenshot to pictures folder.
         *
         */
        private void saveScreenshotToPicturesFolder() {
            File bitmapFile = getOutputMediaFile("yjy");
            if (bitmapFile == null) {
                Log.d(TAG, "Error creating media file, check storage permissions: ");// e.getMessage());
                return;
            }
            try {
                FileOutputStream fos = new FileOutputStream(bitmapFile);
                mBitmap.get().compress(Bitmap.CompressFormat.PNG, 90, fos);
                fos.flush();
                fos.close();
                if (mBitmap != null) {
                    mBitmap.get().recycle();
                    mBitmap = null;
                }

                // Initiate media scanning to make the image available in gallery apps
                MediaScannerConnection.scanFile(mContext, new String[]{bitmapFile.getPath()},
                        new String[]{"image/*"}, (path, uri) -> {
                            if (mListener != null) {
                                mHandler.post(() -> mListener.onPictureSaved(path, uri));
                            }
//                            // 其次把文件插入到系统图库
//                            try {
//                                MediaStore.Images.Media.insertImage(mContext.getContentResolver(),
//                                        bitmapFile.getAbsolutePath(), "yjy", null);
//                            } catch (FileNotFoundException e) {
//                                e.printStackTrace();
//                            }
//                            // 最后通知图库更新
//                            mContext.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri));

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
            File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);

            File mediaStorageDirectory = new File(path, "Camera/");
            // Create the storage directory if it does not exist
            if (!mediaStorageDirectory.exists()) {
                if (!mediaStorageDirectory.mkdirs()) {
                    return null;
                }
            }
            // Create a media file name
            String timeStamp = new SimpleDateFormat("MMdd_HHmmss", Locale.CHINA).format(new Date());
            File mediaFile;
            String mImageName = filename + timeStamp + ".jpg";
            mediaFile = new File(mediaStorageDirectory.getPath(), mImageName);
            return mediaFile;
        }

    }

    public interface OnSaveListener {
        void onPictureSaved(String path, Uri uri);
    }

    /**
     * @param first 原始图
     * @param mark  水印图
     */
    public static Bitmap waterMark(Bitmap first, Bitmap mark) {
        float scale = ((float) first.getWidth()) / mark.getWidth();
        Log.e("--缩放比例scale--", scale+"----");
        mark = scaleImg(mark, 1f);
        int width = first.getWidth();
        int height = first.getHeight();
        Bitmap result = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_4444);
        Canvas canvas = new Canvas(result);
        canvas.drawBitmap(first, 0, 0, null);
        int h = 0;
        int w = 0;
        int count = 0;
        int num = 3;
        for (int k = 0; k < first.getHeight(); k++) {
            if (k % num == 0) {
                count += 1;
                if (count % 2 == 0) {
                    w = mark.getWidth()+50;
                    h = h + mark.getHeight() + 150;
                } else {
                    w = 50;
                    h = h + mark.getHeight() + 150;
                }

            } else {
                w = w + mark.getWidth() + LUtils.getScreenWidth()/4;
            }
            canvas.drawBitmap(mark, w, h, null);
        }

        //            Random rand = new Random();
//            int i = rand.nextInt(width);
//            int j = rand.nextInt(height);


//        while (h < height + mark.getHeight()) {
//            Random rand = new Random();
//            int i = rand.nextInt(10000);
//            int j= rand.nextInt(10000);
//            canvas.drawBitmap(mark, i, j, null);
//            h = h + mark.getHeight();
//        }
        return result;
    }

    private static Bitmap scaleImg(Bitmap bitmap, float scale) {
        Matrix matrix = new Matrix();
        matrix.postScale(scale, scale); //长和宽放大缩小的比例
        Bitmap resizeBmp = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        return resizeBmp;
    }

    /**
     * 横向拼接
     * <功能详细描述>
     *
     * @param first
     * @param second
     * @return
     */
    private Bitmap add2Bitmap(Bitmap first, Bitmap second) {
        int width = first.getWidth() + second.getWidth();
        int height = Math.max(first.getHeight(), second.getHeight());
        Bitmap result = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(result);
        canvas.drawBitmap(first, 0, 0, null);
        canvas.drawBitmap(second, first.getWidth(), 0, null);
        return result;
    }


    /**
     * 纵向拼接
     * <功能详细描述>
     *
     * @param first
     * @param second
     * @return
     */
    public static Bitmap addBitmap(Bitmap first, Bitmap second) {
        int width = Math.max(first.getWidth(), second.getWidth());
        int height = first.getHeight() + second.getHeight();
        Bitmap result = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(result);
        canvas.drawBitmap(first, 0, 0, null);
        canvas.drawBitmap(second, 0, first.getHeight(), null);
        return result;
    }


}
