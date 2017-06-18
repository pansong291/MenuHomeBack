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

public class MainActivity extends Zactivity 
{
 int VERSION_CODE;
 String VERSION_NAME;
 
 @Override
 protected void onCreate(Bundle savedInstanceState)
 {
  super.onCreate(savedInstanceState);
  setContentView(R.layout.activity_main);
  
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
  
  int oldVerCode=sp.getInt(V_CODE,99999999);
  if(oldVerCode<VERSION_CODE)
  {
   //用户更新了本应用
   new AlertDialog.Builder(this)
    .setTitle("版本升级")
    .setMessage(String.format("感谢您对本应用的支持！\n本应用已成功升级到%1$s版本。\n%2$s",VERSION_NAME,getString(R.string.update_msg)))
    .setPositiveButton("确定",null)
    .show();
   sp.edit().putInt(V_CODE,VERSION_CODE).commit();
  }else if(oldVerCode==99999999)
  {
   //用户第一次安装本应用
   new AlertDialog.Builder(this)
    .setTitle("声明")
    .setMessage(String.format("感谢您安装本应用！\n%s",getString(R.string.hello_user)))
    .setPositiveButton("确定",null)
    .show();
   sp.edit().putInt(V_CODE,VERSION_CODE).commit();
  }
  
  
 }
 
 public void onStartClick(View v)
 {
  startService(new Intent(MainActivity.this,MyService.class));
 }
 
 public void onStopClick(View v)
 {
  stopService(new Intent(MainActivity.this,MyService.class));
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
