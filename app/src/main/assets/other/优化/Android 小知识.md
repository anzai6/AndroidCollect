##Android 小知识

1.编辑AndroidManifest.xml中的Application节点，增加属性largeheap="true"参数.为应用申请最大内存  

2.如果试图从非activity的非正常途径启动一个activity，比如从一个service中启动一个activity，或者第三方应用启动，则intent要添加FLAG_ACTIVITY_NEW_TASK 标记

3.应用内部广播使用LocalBroadcastManager发送

4.启动第三方apk时防止其运行在当前apk的进程上需要加上intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);  

5.防止fragment混乱方法:  
a.实现Activity的onSaveInstanceState方法，注释掉super,Activity被销毁后就不会保存fragment信息，重新启动页面后也就不会保留之前的fragment导致重叠混乱：  
``` Java
	@Override
	protected void onSaveInstanceState(Bundle outState) {   
		// 注释掉它的super方法，内存回收关闭页面就不会保存信息
	}
```
b.使用ViewPager做Fragment的容器

6.动态设置视图的shape的颜色:
``` Java  
 	//rl_name是要改变背景的shape颜色的布局
	GradientDrawable myGrad = (GradientDrawable)rl_name.getBackground();
	int i = (int) ((Math.random()*16777215+1));
	myGrad.setColor(Color.parseColor("#"+Integer.toHexString(i))); 
```

7.textview--垂直显示一段文字:
设置两个属性就可以搞定，就是layout_width设置为wrap_content，然后设置ems属性为1这个属性则代表一行能显示几个字符   
``` Java
//属性代表一行能显示几个字符,林外还有maxEms和minEms
android:ems="1"
```

8.android:clipChild表示是否限制子view在其范围内，在animation动画以及文本的情况下可以发挥最大作用，默认值是true
- 在布局文件中，如果只是为了占位，可以用 Space 来取代 View。 最棒的一点是Space可以跳过 Draw 这个过程。 

9.SparseArray 目前有很多地方从性能优化方说使用SparseArray来替换hashMap，来节省内存，提高性能。

10.模块间有消息需要传递时，使用LocalBroadcastManager替代Listener进行模块解耦。除了解耦，这样发送消息和执行消息差一个线程循环，可以减小方法的调用链，我这就碰到一次方法调用链太长导致StackOverflow的问题。

11.不少人在子线程中更新View时喜欢使用Context.runOnUiThread，这个方法有个缺点，就是一但Context生命周期结束，比如Activity已经销毁时，一调用就会崩溃。

12.SharedPreferences.Editor.commit这个方法是同步的，一直到把数据同步到Flash上面之后才会返回，由IO操作的不可控，尽量使用apply方法代替。apply只在API Level>=9才会支持，需要做兼容。不过，最新的 support v4 包已经为我们做好了处理，使用 SharedPreferencesCompat.EditorCompat.getInstance().apply(editor) 即可。

13.Application的生命周期就是进程的生命周期。只有进程被干掉时，Application才会销毁。哪怕是没有Activity、Service在运行，Application也会存在。所以，为了减少内存压力，尽量不要在Application里面引用大对象、Context等。

14.设置全屏方法有2种:1.通过代码设置，2通过manifest文件设置。用代码设置全屏时app在我们应用运行后，可能会看到短暂的状态栏，然后才全屏，而第二种方法是不会有这种情况的,所以推荐第二种。

15.遍历HashMap的最佳方法  

代码

	public static void printMap(Map mp) {  
    	for (Map.Entry m : mp.entrySet()) {
    		System.out.println(m.getKey() + ":" + m.getValue());
    	}
    }

16.在activity中调用 moveTaskToBack (boolean nonRoot)方法即可将activity 退到后台，注意不是finish()退出。

17.getParent().requestDisallowInterceptTouchEvent(true);剥夺父view对touch事件的处理权，谁用谁知道。

18.IntentService,一个可以干完活后自己去死且不需要我们去管理子线程的Service

19.Activity.recreate重新创建Activity。有什么用呢？可以在程序更换主题后，立马刷新当前Activity，而不会有明显的重启Activity的动画。

20.6.0之后getResources().getColor()方法被废弃了，大家可以用ContextCompat.getColor(context, R.color.color_name)替换，ContextCompat 是 v4 包里的，请放心使用，另外还有getDrawable()等方法

21.图片的资源文件官方推荐只把launcher放在mipmap文件夹下面，而app用到的资源文件建议放在drawable下面。  














