package pansong291.menuhomeback.other;

import android.view.View.OnClickListener;
import android.view.View;
import pansong291.menuhomeback.R;
import android.view.KeyEvent;
import android.widget.Toast;

public class MyBtnClickListener implements OnClickListener
{
 public static boolean showToast=true,autoHide=true;
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
 }
 
 private void changeVisible()
 {
  view.setVisibility(8-view.getVisibility());
 }
 
}
