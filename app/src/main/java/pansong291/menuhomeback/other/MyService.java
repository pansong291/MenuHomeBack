package pansong291.menuhomeback.other;

import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.WindowManager;
import android.view.View.OnTouchListener;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;
import pansong291.menuhomeback.R;
import pansong291.menuhomeback.ui.MainActivity;
import android.graphics.Point;

public class MyService extends Service 
{
 
 //定义浮动窗口布局
 LinearLayout mFloatLayout;
 LayoutParams wmParams;
 //创建浮动窗口设置布局参数的对象
 WindowManager mWindowManager;

 View viewLayoutBtn;
 Button btn_main,btn_menu,btn_home,btn_back;

 private static final String TAG="MyService";

 @Override
 public void onCreate() 
 {
  // TODO Auto-generated method stub
  super.onCreate();
  Log.i(TAG,"oncreat");
  createFloatView();      
 }

 @Override
 public IBinder onBind(Intent intent)
 {
  // TODO Auto-generated method stub
  return null;
 }

 private void createFloatView()
 {
  wmParams=new LayoutParams();
  //获取的是WindowManagerImpl.CompatModeWrapper
  mWindowManager=(WindowManager)getApplication().getSystemService(getApplication().WINDOW_SERVICE);
  Log.i(TAG,"mWindowManager--->"+mWindowManager);
  //设置window type
  wmParams.type=LayoutParams.TYPE_PHONE; 
  //设置图片格式，效果为背景透明
  wmParams.format=PixelFormat.RGBA_8888; 
  //设置浮动窗口不可聚焦（实现操作除浮动窗口外的其他可见窗口的操作）
  wmParams.flags=LayoutParams.FLAG_NOT_FOCUSABLE;      
  //调整悬浮窗显示的停靠位置为左侧置顶
  wmParams.gravity=Gravity.LEFT|Gravity.TOP;       
  // 以屏幕左上角为原点，设置x、y初始值，相对于gravity
  wmParams.x=0;
  wmParams.y=0;

  //设置悬浮窗口长宽数据  
  wmParams.width=LayoutParams.WRAP_CONTENT;
  wmParams.height=LayoutParams.WRAP_CONTENT;

  /*// 设置悬浮窗口长宽数据
   wmParams.width=200;
   wmParams.height=80;*/

  LayoutInflater inflater=LayoutInflater.from(getApplication());
  //获取浮动窗口视图所在布局
  mFloatLayout=(LinearLayout)inflater.inflate(R.layout.float_window,null);
  //添加mFloatLayout
  mWindowManager.addView(mFloatLayout,wmParams);
  viewLayoutBtn=mFloatLayout.findViewById(R.id.float_layout_btn);
  //浮动窗口按钮
  btn_main=(Button)mFloatLayout.findViewById(R.id.float_btn_main);
  btn_menu=(Button)mFloatLayout.findViewById(R.id.float_btn_menu);
  btn_home=(Button)mFloatLayout.findViewById(R.id.float_btn_home);
  btn_back=(Button)mFloatLayout.findViewById(R.id.float_btn_back);

  mFloatLayout.measure(MeasureSpec.makeMeasureSpec(0,MeasureSpec.UNSPECIFIED),MeasureSpec.makeMeasureSpec(0,MeasureSpec.UNSPECIFIED));
  //设置监听浮动窗口的触摸移动
  btn_main.setOnTouchListener(new OnTouchListener() 
   {
    Point po=new Point();
    @Override
    public boolean onTouch(View v,MotionEvent event) 
    {
     switch(event.getAction())
     {
      case MotionEvent.ACTION_DOWN:
       po.set((int)event.getRawX(),(int)event.getRawY());
      break;
      case MotionEvent.ACTION_MOVE:
       //getRawX是触摸位置相对于屏幕的坐标
       wmParams.x+=(int)event.getRawX()-po.x;
       //y减去状态栏的高度
       wmParams.y+=(int)event.getRawY()-po.y;
       //刷新
       mWindowManager.updateViewLayout(mFloatLayout,wmParams);
       po.set((int)event.getRawX(),(int)event.getRawY());
      break;
      case MotionEvent.ACTION_UP:
      break;
     }
     return false;//此处必须返回false，否则OnClickListener获取不到监听
    }
   }); 
  
  MyBtnClickListener mbcl=new MyBtnClickListener(viewLayoutBtn);
  btn_main.setOnClickListener(mbcl);
  btn_menu.setOnClickListener(mbcl);
  btn_home.setOnClickListener(mbcl);
  btn_back.setOnClickListener(mbcl);
  
 }

 @Override
 public void onDestroy() 
 {
  // TODO Auto-generated method stub
  super.onDestroy();
  if(mFloatLayout!=null)
  {
   //移除悬浮窗口
   mWindowManager.removeView(mFloatLayout);
  }
  RootShellCmd.getRSC().close();
 }

}
