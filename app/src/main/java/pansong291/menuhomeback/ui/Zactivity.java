package pansong291.menuhomeback.ui;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;
import pansong291.crash.ActivityControl;

public class Zactivity extends Activity
{
 public SharedPreferences sp;
 
 public static final String V_CODE="v_code",QZGX="qzGX",W_X="win_x",W_Y="win_y";
 
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
 
 public void toast(String s)
 {
  toast(s,0);
 }
 
 public void toast(String s,int i)
 {
  Toast.makeText(this,s,i).show();
 }
 
}
