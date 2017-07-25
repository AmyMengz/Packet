package com.aissure.packet.packet.utils;

import android.accessibilityservice.AccessibilityService;
import android.os.Build;
import android.view.accessibility.AccessibilityNodeInfo;

import java.util.List;

/**
 * Created by Administrator on 2017/7/17.
 */

public class AccessibilityHelper {
    /** 通过id查找*/
    public static AccessibilityNodeInfo findNodeInfosById(AccessibilityNodeInfo nodeInfo, String resId) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            List<AccessibilityNodeInfo> list = nodeInfo.findAccessibilityNodeInfosByViewId(resId);
            if(list != null && !list.isEmpty()) {
                return list.get(0);
            }
        }
        return null;
    }
    public static AccessibilityNodeInfo findNodeInfoByText(AccessibilityNodeInfo nodeInfo, String text){
        List<AccessibilityNodeInfo> list = nodeInfo.findAccessibilityNodeInfosByText(text);
        if(list==null||list.isEmpty()){
            return  null;
        }
        Logger.i(list==null?"null":"size:"+list.size());
        for (AccessibilityNodeInfo node:list){
            Logger.i("node: "+node.getText());
        }
        return list.get(0);
    }

    public static void performClick(AccessibilityNodeInfo nodeInfo) {
        if(nodeInfo ==null){
            return;
        }
        Logger.i("performClick():"+nodeInfo.getText()+" class:"+ nodeInfo.getClassName()+"  "+nodeInfo.isClickable()+" parent:"+nodeInfo.getParent().getClassName());
        if (nodeInfo.isClickable()){
            nodeInfo.performAction(AccessibilityNodeInfo.ACTION_CLICK);
        }else {
            performClick(nodeInfo.getParent());
        }
    }

    /** 返回主界面事件*/
    public static void performHome(AccessibilityService service) {
        if(service == null) {
            return;
        }
        service.performGlobalAction(AccessibilityService.GLOBAL_ACTION_HOME);
    }
    /** 返回事件*/
    public static void performBack(AccessibilityService service) {
        if(service == null) {
            return;
        }
        service.performGlobalAction(AccessibilityService.GLOBAL_ACTION_BACK);
    }
}
