package pansong291.menuhomeback.ui;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import pansong291.menuhomeback.R;
import pansong291.menuhomeback.other.MyService;
import pansong291.menuhomeback.other.RootShellCmd;
import pansong291.menuhomeback.other.MyBtnClickListener;
import android.widget.CheckBox;
import android.widget.RadioButton;

public class MainActivity extends Zactivity 
{
 int VERSION_CODE;
 String VERSION_NAME;
 CheckBox cbShowToast,cbAutoHide,cbVibrate;
 RadioButton rbVibrateLow,rbVibrateMiddle,rbVibrateHigh;
 
 @Override
 protected void onCreate(Bundle savedInstanceState)
 {
  super.onCreate(savedInstanceState);
  setContentView(R.layout.activity_main);
  
  cbShowToast=(CheckBox)findViewById(R.id.cb_main_show_toast);
  cbAutoHide=(CheckBox)findViewById(R.id.cb_main_auto_hide);
  cbVibrate=(CheckBox)findViewById(R.id.cb_main_vibrate_feedback);
  rbVibrateLow=(RadioButton)findViewById(R.id.rb_main_vibrate_low);
  rbVibrateMiddle=(RadioButton)findViewById(R.id.rb_main_vibrate_middle);
  rbVibrateHigh=(RadioButton)findViewById(R.id.rb_main_vibrate_high);
  
  try{
   PackageInfo pi=getPackageManager().getPackageInfo(getPackageName(),0);
   VERSION_CODE=pi.versionCode;
   VERSION_NAME=pi.versionName;
  }catch(Exception e)
  {
   Log.e("VersionInfo","Exception",e);    
  }
  
  if(!RootShellCmd.getRSC().checkRootPermission())
  {
   new AlertDialog.Builder(this)
    .setTitle("无root权限")
    .setMessage("您的手机没有root权限，将无法正常使用本程序。")
    .setPositiveButton("确定",null)
    .show();
  }
  
  int oldVerCode=opInt(V_CODE,99999999);
  if(oldVerCode<VERSION_CODE)
  {
   //用户更新了本应用
   new AlertDialog.Builder(this)
    .setTitle("版本升级")
    .setMessage(String.format("感谢您对本应用的支持！\n本应用已成功升级到%1$s版本。\n%2$s",VERSION_NAME,getString(R.string.update_msg)))
    .setPositiveButton("确定",null)
    .show();
   ipInt(V_CODE,VERSION_CODE);
  }else if(oldVerCode==99999999)
  {
   //用户第一次安装本应用
   new AlertDialog.Builder(this)
    .setTitle("声明")
    .setMessage(String.format("感谢您安装本应用！\n%s",getString(R.string.hello_user)))
    .setPositiveButton("确定",null)
    .show();
   ipInt(V_CODE,VERSION_CODE);
  }

  MyBtnClickListener.showToast=opBoolean(S_T,true);
  cbShowToast.setChecked(MyBtnClickListener.showToast);
  MyBtnClickListener.autoHide=opBoolean(A_H,true);
  cbAutoHide.setChecked(MyBtnClickListener.autoHide);
  MyBtnClickListener.vibrateFeedback=opBoolean(V_F,true);
  cbVibrate.setChecked(MyBtnClickListener.vibrateFeedback);
  MyBtnClickListener.vibrateStrong=opInt(V_S,MyBtnClickListener.VIBRATE_LOW);
  switch(MyBtnClickListener.vibrateStrong)
  {
   case MyBtnClickListener.VIBRATE_LOW:
    rbVibrateLow.setChecked(true);
   break;
   case MyBtnClickListener.VIBRATE_MIDDLE:
    rbVibrateMiddle.setChecked(true);
   break;
   case MyBtnClickListener.VIBRATE_HIGH:
    rbVibrateHigh.setChecked(true);
   break;
  }
  
 }

 @Override
 protected void onResume()
 {
  super.onResume();
 }

 @Override
 protected void onStop()
 {
  super.onStop();
  ipBoolean(S_T,MyBtnClickListener.showToast);
  ipBoolean(A_H,MyBtnClickListener.autoHide);
  ipBoolean(V_F,MyBtnClickListener.vibrateFeedback);
  ipInt(V_S,MyBtnClickListener.vibrateStrong);
 }

 public void onStartClick(View v)
 {
  startService(new Intent(this,MyService.class));
 }
 
 public void onStopClick(View v)
 {
  stopService(new Intent(this,MyService.class));
 }
 
 public void onCheckBoxClick(View v)
 {
  switch(v.getId())
  {
   case R.id.cb_main_show_toast:
    MyBtnClickListener.showToast=!MyBtnClickListener.showToast;
   break;
   case R.id.cb_main_auto_hide:
    MyBtnClickListener.autoHide=!MyBtnClickListener.autoHide;
   break;
   case R.id.cb_main_vibrate_feedback:
    MyBtnClickListener.vibrateFeedback=!MyBtnClickListener.vibrateFeedback;
   break;
  }
 }
 
 public void onRadioBtnClick(View v)
 {
  switch(v.getId())
  {
   case R.id.rb_main_vibrate_low:
    MyBtnClickListener.vibrateStrong=MyBtnClickListener.VIBRATE_LOW;
   break;
   case R.id.rb_main_vibrate_middle:
    MyBtnClickListener.vibrateStrong=MyBtnClickListener.VIBRATE_MIDDLE;
   break;
   case R.id.rb_main_vibrate_high:
    MyBtnClickListener.vibrateStrong=MyBtnClickListener.VIBRATE_HIGH;
   break;
  }
 }
 
 //获取状态栏高度
 public float getStatusHeight(Zactivity a1)
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
 
}
