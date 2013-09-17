/**
 * Copyright (c) 2013 Baidu.com, Inc. All Rights Reserved
 */
package com.joke.activity;

import android.os.Bundle;

import com.baidu.sharesdk.weixin.WXEventHandlerActivity;


/**
 * 处理微信回调
 * @author chenhetong(chenhetong@baidu.com)
 *
 */
public class WXEntryActivity extends WXEventHandlerActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        finish();
    }
    
}
