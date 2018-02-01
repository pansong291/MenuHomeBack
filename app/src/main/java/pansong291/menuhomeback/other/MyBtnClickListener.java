package pansong291.menuhomeback.other;

import android.os.Vibrator;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;
import pansong291.menuhomeback.R;
import android.content.Context;

public class MyBtnClickListener implements OnClickListener
{
 public static final int VIBRATE_LOW=1,VIBRATE_MIDDLE=2,VIBRATE_HIGH=3;
 public static boolean showToast=true,autoHide=true,vibrateFeedback=true;
 public static int vibrateStrong=VIBRATE_LOW;
 private String keyName,key_Menu="菜单键",key_Home="主页键",key_Back="返回键",key_Other="未知按键";
 private View view;
 
 public MyBtnClickListener(View v)
 {
  view=v;
 }
 
 @Override
 public void onClick(View p1)
 {
  switch(p1.getId())
  {
   case R.id.float_btn_main:
   break;
   case R.id.float_btn_menu:
    RootShellCmd.getRSC().simulateKey(KeyEvent.KEYCODE_MENU);
    keyName=key_Menu;
   break;
   case R.id.float_btn_home:
    RootShellCmd.getRSC().simulateKey(KeyEvent.KEYCODE_HOME);
    keyName=key_Home;
   break;
   case R.id.float_btn_back:
    RootShellCmd.getRSC().simulateKey(KeyEvent.KEYCODE_BACK);
    keyName=key_Back;
   break;
   default:
    keyName=key_Other;
  }
  if(showToast)
   Toast.makeText(p1.getContext(),keyName,0).show();
  if(autoHide)
   changeVisible();
  if(vibrateFeedback)
   vibrate(view.getContext(),vibrateStrong);
 }
 
 private void changeVisible()
 {
  view.setVisibility(8-view.getVisibility());
 }
 
 public static void vibrate(Context c,int i)
 {
  long[]vt=null;
  switch(i)
  {
   case VIBRATE_MIDDLE:
    vt=new long[]{0,10,10,40};
   break;
   case VIBRATE_HIGH:
    vt=new long[]{0,50,0,50};
   break;
   default:
    vt=new long[]{0,1,20,21};
  }
  Vibrator vibrator=(Vibrator)c.getSystemService(c.VIBRATOR_SERVICE);
  vibrator.vibrate(vt,-1);
 }
 
}
