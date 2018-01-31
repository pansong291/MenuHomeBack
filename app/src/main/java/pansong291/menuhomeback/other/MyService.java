package pansong291.menuhomeback.other;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.os.IBinder;
import android.os.Vibrator;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;
import pansong291.menuhomeback.R;
import pansong291.menuhomeback.ui.Zactivity;
import android.os.Handler;
import pansong291.menuhomeback.ui.MainActivity;

public class MyService extends Service 
{
 
 //定义浮动窗口布局
 LinearLayout mFloatLayout;
 LayoutParams wmParams;
 //创建浮动窗口设置布局参数的对象
 WindowManager mWindowManager;

 View viewLayoutBtn;
 Button btn_main,btn_menu,btn_home,btn_back;
 
 SharedPreferences sp;
 
 int screenW,screenH,statusH;

 private static final String TAG="MyService";

 @Override
 public void onCreate() 
 {
  // TODO Auto-generated method stub
  super.onCreate();
  Log.i(TAG,"oncreat");
  sp=getSharedPreferences(getPackageName()+"_preferences",0);
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
  statusH=(int)getStatusHeight(this);
  wmParams=new LayoutParams();
  //获取的是WindowManagerImpl.CompatModeWrapper
  mWindowManager=(WindowManager)getApplication().getSystemService(getApplication().WINDOW_SERVICE);
  Log.i(TAG,"mWindowManager--->"+mWindowManager);
  screenW=mWindowManager.getDefaultDisplay().getWidth();
  screenH=mWindowManager.getDefaultDisplay().getHeight();
  //设置window type
  wmParams.type=LayoutParams.TYPE_PHONE; 
  //设置图片格式，效果为背景透明
  wmParams.format=PixelFormat.RGBA_8888; 
  //设置浮动窗口不可聚焦（实现操作除浮动窗口外的其他可见窗口的操作）
  wmParams.flags=LayoutParams.FLAG_NOT_FOCUSABLE;      
  //调整悬浮窗显示的停靠位置为左侧置顶
  wmParams.gravity=Gravity.LEFT|Gravity.TOP;       
  //以屏幕左上角为原点，设置x、y初始值，相对于gravity
  wmParams.x=sp.getInt(Zactivity.W_X,0);
  wmParams.y=sp.getInt(Zactivity.W_Y,0);

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
  viewLayoutBtn.setVisibility(sp.getInt(Zactivity.VISI,0));
  //设置监听浮动窗口的触摸移动
  btn_main.setOnTouchListener(new OnTouchListener() 
   {
    Point pBefore=new Point(),pAfter=new Point(),pFirst=new Point();
    Handler longPressHandler=new Handler();
    boolean longPressed=false;
    
    @Override
    public boolean onTouch(View v,MotionEvent event) 
    {
     switch(event.getAction())
     {
      case MotionEvent.ACTION_DOWN:
       pBefore.set((int)event.getRawX(),(int)event.getRawY());
       pFirst.set(pBefore.x,pBefore.y);
       longPressed=false;
       longPressHandler.postDelayed(new Runnable()
       {
         @Override
         public void run()
         {
          //长按事件
          longPressed=true;
          Vibrator vibrator=(Vibrator)MyService.this.getSystemService(MyService.this.VIBRATOR_SERVICE);
          vibrator.vibrate(50);
          Intent it=new Intent(MyService.this,MainActivity.class);
          it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
          startActivity(it);
         }
        },1000);
      break;
      case MotionEvent.ACTION_MOVE:
       pAfter.set((int)event.getRawX(),(int)event.getRawY());
       if(viewLayoutBtn.getVisibility()==0&&pBefore.y>screenH-mFloatLayout.getMeasuredHeight())
        wmParams.y=screenH-statusH-mFloatLayout.getMeasuredHeight();
       //getRawX是触摸位置相对于屏幕的坐标
       wmParams.x+=pAfter.x-pBefore.x;
       if(wmParams.x<0||wmParams.x>screenW-btn_main.getMeasuredWidth())
        wmParams.x-=pAfter.x-pBefore.x;
       //y减去状态栏的高度
       wmParams.y+=pAfter.y-pBefore.y;
       if((viewLayoutBtn.getVisibility()==0&&wmParams.y>=screenH-statusH-mFloatLayout.getMeasuredHeight())||
       (wmParams.y<0||wmParams.y>screenH-statusH-btn_main.getMeasuredHeight()))
        wmParams.y-=pAfter.y-pBefore.y;
       //刷新
       mWindowManager.updateViewLayout(mFloatLayout,wmParams);
       pBefore.set(pAfter.x,pAfter.y);
       
       if(!longPressed&&Math.abs(event.getRawX()-pFirst.x)>9&&Math.abs(event.getRawY()-pFirst.y)>9)
       {
        longPressHandler.removeCallbacksAndMessages(null);
        longPressed=true;
       }
      break;
      case MotionEvent.ACTION_UP:
       longPressHandler.removeCallbacksAndMessages(null);
       //移动距离小于某值，则为点击事件或长按事件
       if(Math.abs(event.getRawX()-pFirst.x)<10&&Math.abs(event.getRawY()-pFirst.y)<10)
       {
        //如果时间间隔小于某值(或长按未触发)，则为点击事件
        if(!longPressed)
        {
         viewLayoutBtn.setVisibility(8-viewLayoutBtn.getVisibility());
        }
       }
      break;
     }
     return true;//返回真使得主按钮获取不到其它监听
    }
   }); 
  
  MyBtnClickListener mbcl=new MyBtnClickListener(viewLayoutBtn);
  //btn_main.setOnClickListener(mbcl);
  btn_menu.setOnClickListener(mbcl);
  btn_home.setOnClickListener(mbcl);
  btn_back.setOnClickListener(mbcl);
  
 }
 
 //获取状态栏高度
 public float getStatusHeight(Service a1)
 {
  int i1=0;
  try{
   Class<?>c1=Class.forName("com.android.internal.R$dimen");
   Object o1=c1.newInstance();
   int i2=Integer.parseInt(c1.getField("status_bar_height").get(o1).toString());
   i1=a1.getResources().getDimensionPixelSize(i2);
  }catch(Exception e)
  {
   e.printStackTrace();
  }
  return i1;
 }

 @Override
 public void onDestroy() 
 {
  // TODO Auto-generated method stub
  super.onDestroy();
  sp.edit().putInt(Zactivity.W_X,wmParams.x).commit();
  sp.edit().putInt(Zactivity.W_Y,wmParams.y).commit();
  sp.edit().putInt(Zactivity.VISI,viewLayoutBtn.getVisibility()).commit();
  if(mFloatLayout!=null)
  {
   //移除悬浮窗口
   mWindowManager.removeView(mFloatLayout);
  }
  RootShellCmd.getRSC().close();
 }

}
