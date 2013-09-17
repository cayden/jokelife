/**
 * Utility.java
 * 版权所有(C) 2013 
 * 创建:cuiran 2013-7-6 下午1:11:20
 */
package com.joke.util;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

/**
 * TODO Android之ScrollView嵌套ListView
 * @author cuiran
 * @version TODO
 */
public class Utility {
	
	 public static void setListViewHeightBasedOnChildren(ListView listView) {
         //获取ListView对应的Adapter
         ListAdapter listAdapter = listView.getAdapter();
         if (listAdapter == null) {
                return;
         }
        
         int totalHeight = 0;
         for (int i = 0, len = listAdapter.getCount(); i < len; i++) {   //listAdapter.getCount()返回数据项的数目
                View listItem = listAdapter.getView(i, null, listView);
                listItem.measure(0, 0);  //计算子项View 的宽高
                totalHeight += listItem.getMeasuredHeight();  //统计所有子项的总高度
         }
        
         ViewGroup.LayoutParams params = listView.getLayoutParams();
         params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1))+200;
         //listView.getDividerHeight()获取子项间分隔符占用的高度
         //params.height最后得到整个ListView完整显示需要的高度
         listView.setLayoutParams(params);
  }

}
