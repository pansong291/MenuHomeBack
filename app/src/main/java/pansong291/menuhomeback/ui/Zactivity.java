package pansong291.menuhomeback.ui;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;
import pansong291.crash.ActivityControl;

public class Zactivity extends Activity
{
 public SharedPreferences sp;
 
 public static final String
  V_CODE="v_code",QZGX="qzGX",
  W_X="win_x",W_Y="win_y",VISI="visible",
  S_T="show_t",A_H="auto_h",
  V_F="vibrate_f",V_S="vibrate_s";
 
 @Override
 protected void onResume()
 {
  super.onResume();
  
 }
 
 @Override
 protected void onCreate(Bundle savedInstanceState)
 {
  super.onCreate(savedInstanceState);
  ActivityControl.getActivityControl().addActivity(this);

  sp=getSharedPreferences(getPackageName()+"_preferences",0);

 }

 @Override
 protected void onDestroy()
 {
  super.onDestroy();
  ActivityControl.getActivityControl().removeActivity(this);
 }
 
 public void ipInt(String k,int v)
 {
  sp.edit().putInt(k,v).commit();
 }
 
 public int opInt(String k,int v)
 {
  return sp.getInt(k,v);
 }
 public void ipBoolean(String k,boolean v)
 {
  sp.edit().putBoolean(k,v).commit();
 }

 public boolean opBoolean(String k,boolean v)
 {
  return sp.getBoolean(k,v);
 }
 
 public void toast(String s)
 {
  toast(s,0);
 }
 
 public void toast(String s,int i)
 {
  Toast.makeText(this,s,i).show();
 }
 
}
