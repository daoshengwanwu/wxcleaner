package com.example.wechat_clean;


import android.accessibilityservice.AccessibilityService;
import android.util.Log;
import android.view.KeyEvent;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.accessibility.AccessibilityWindowInfo;

import java.util.List;


public class CleanService extends AccessibilityService {

    List<AccessibilityNodeInfo> nodeInfos;
    AccessibilityNodeInfo rootNodeInfo;

    String preString = "com.tencent.mm:id/";

    public CleanService() {
    }

    @Override
    protected boolean onGesture(int gestureId) {
        return super.onGesture(gestureId);
    }

    @Override
    protected boolean onKeyEvent(KeyEvent event) {
        return super.onKeyEvent(event);
    }

    @Override
    public List<AccessibilityWindowInfo> getWindows() {
        return super.getWindows();
    }

    @Override
    public AccessibilityNodeInfo getRootInActiveWindow() {
        return super.getRootInActiveWindow();
    }

    @Override
    public AccessibilityNodeInfo findFocus(int focus) {
        return super.findFocus(focus);
    }

    @Override
    public Object getSystemService(String name) {
        return super.getSystemService(name);
    }

    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();
    }

    @Override
    public synchronized void onAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
//    	微信清理助手工作原理在于当用户选中聊天记录并选择更多时
//    	对所有聊天记录复选框进行选择，
//    	若用户需要选择更多，可以上拉划出更多聊天记录
    	
        rootNodeInfo = getRootInActiveWindow();
        Log.v("Accessibility", "第一步");
        if (rootNodeInfo == null) {
            return;
        }

        // 获取所有复选框
        // ad和a8分别对应删除复选框和复选框状态
        List<AccessibilityNodeInfo> adNodes =
                rootNodeInfo.findAccessibilityNodeInfosByViewId(preString + "ad");

        List<AccessibilityNodeInfo> a8Nodes =
                rootNodeInfo.findAccessibilityNodeInfosByViewId(preString + "a8");

        int size = adNodes.size();
        Log.v("Accessibility", "adNodes" + size);
        System.out.println("Accessibility:adNodes" + size);
        Log.v("Accessibility", "a8Nodes" + a8Nodes.size());
        System.out.println("Accessibility:a8Nodes" + a8Nodes.size());

        // 过去复选框列表并对其逐个点击选中
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                AccessibilityNodeInfo adNode = adNodes.get(i);
                AccessibilityNodeInfo a8Node = a8Nodes.get(i);
                if (!a8Node.isChecked()) {
                    performClick(adNode);
                } else {
                    break;
                }
            }
        }
    }
    
    //模拟点击事件
    public static void performClick(AccessibilityNodeInfo nodeInfo) {
        if (nodeInfo == null) {
            return;
        }
        if (nodeInfo.isClickable()) {
            nodeInfo.performAction(AccessibilityNodeInfo.ACTION_CLICK);
        } else {
            performClick(nodeInfo.getParent());
        }
    }

    //模拟选择事件
    public static void performSelect(AccessibilityNodeInfo nodeInfo) {
        if (nodeInfo == null) {
            return;
        }
        nodeInfo.performAction(AccessibilityNodeInfo.ACTION_SELECT);
    }

    @Override
    public void onInterrupt() {
    	Log.d("Accessibility", "中断");
    }
}
