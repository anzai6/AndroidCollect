---
title: WebView中软键盘会遮挡输入框
---
要想实现这种软键盘出现的时候会自动把输入框的布局顶上去的效果，需要设置输入法的属性，有以下两种设置方式：  

一、在java代码中设置如下：  

``` java
getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZ |WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
```

二、在androidmanifest.xml中设置与其对应的activity的属性如下： 

``` java
android:windowSoftInputMode="stateHidden|adjustPan" 
```

正常情况下，设置了该属性之后，软键盘在弹出时，输入框便不会被遮挡。但是该属性在有些情况下是不生效的，目前我所知的是以下两种情况：
1.当前activity被设置了全屏属性，即：

``` java
android:theme="@android:styleTheme.Black.NoTitleBar.Fullscreen"  
```

2.webview的相关布局被固定了高度，这里也分为两种情况：  
a. 根布局固定了高度，这里的根布局是webview所在的Activity的最外层布局；  
b. 根布局未固定高度，但是根布局是FrameLayout布局，而webview或者其父控件被固定了高度。（项目中遇到的就是这种情况，经过反复对比测试，终于找到原因，希望分享出来，能帮助其他人）
以上的这两种固定的高度都会导致软键盘遮挡输入框的问题，需格外留意。  

**ps：固定了高度，是指layout_height属性被赋予了具体多少dp或px。**