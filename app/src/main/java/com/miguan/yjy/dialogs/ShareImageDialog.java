package com.miguan.yjy.dialogs;

import android.app.Dialog;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.miguan.yjy.R;
import com.miguan.yjy.utils.LUtils;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;

import java.io.File;

import static com.miguan.yjy.model.constant.Constants.EXTRA_IMAGE_URI;

/**
 * Copyright (c) 2017/9/27. LiaoPeiKun Inc. All rights reserved.
 */

public class ShareImageDialog extends BaseAlertDialog {

    public static ShareImageDialog newInstance(String path, Object callback) {
        ShareImageDialog dialog = new ShareImageDialog();

        Bundle bundle = new Bundle();
        bundle.putString(EXTRA_IMAGE_URI, path);
        dialog.setArguments(bundle);

        if (callback instanceof Fragment) {
            dialog.setTargetFragment((Fragment) callback, 0);
        }

        return dialog;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        String uri = getArguments().getString(EXTRA_IMAGE_URI);

        View.OnClickListener shareOnClickListener = v -> {
            dismiss();

            if (!UMShareAPI.get(getActivity()).isInstall(getActivity(), SHARE_MEDIA.WEIXIN)) {
                LUtils.toast("未安装微信");
                return;
            }
            if (TextUtils.isEmpty(uri)) return;

            UMImage image = new UMImage(getActivity(), new File(uri));
            image.compressStyle = UMImage.CompressStyle.QUALITY;

            ShareAction action = new ShareAction(getActivity())
                    .withMedia(image);

            switch (v.getId()) {
                case R.id.tv_share_shot_friend:
                    action.setPlatform(SHARE_MEDIA.WEIXIN);
                    break;
                case R.id.tv_share_shot_circle:
                    action.setPlatform(SHARE_MEDIA.WEIXIN_CIRCLE);
                    break;
            }
            action.share();
        };

        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_share_screenshot, null);
        ImageView iv = view.findViewById(R.id.iv_share_shot_image);
        iv.setImageURI(Uri.parse(uri));
        TextView tvFriend = view.findViewById(R.id.tv_share_shot_friend);
        tvFriend.setOnClickListener(shareOnClickListener);
        TextView tvCircle = view.findViewById(R.id.tv_share_shot_circle);
        tvCircle.setOnClickListener(shareOnClickListener);

        AlertDialog dialog = new AlertDialog.Builder(getActivity())
                .setView(view)
                .create();

        if (dialog.getWindow() != null) dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        return dialog;
    }

}
