package com.miguan.yjy.model.constant;

import com.miguan.yjy.utils.LUtils;

/**
 * @作者 cjh
 * @日期 2017/4/17 15:47
 * @描述
 */

public interface Constants {

//    http://api.beta.yjyapp.com/api/index?action=productInfo&from=ios&id=345895&time=1492588976&user_id=10
//    public static final String testLink = "http://m.yjyapp.com/site/skin-test?type=1&user_id=90004";
    String testLink = "http://m." + (LUtils.isDebug?"beta.": "")+"yjyapp.com/site/skin-test?type=";

    String API_VERSION = "121"; // 接口版本号

    String KEY_CHECK_IS_BIND_TIME = "check_is_bind_time";

    String TAG_DIALOG_NEW_BILL = "new_bill"; // 创建订单窗口

    String EXTRA_BILL_ID = "bill_id";

    String EXTRA_BILL_NAME = "bill_name";

    String EXTRA_IMAGE_URI = "image_uri";

}
