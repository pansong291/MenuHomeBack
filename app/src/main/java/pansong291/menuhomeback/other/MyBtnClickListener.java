package pansong291.menuhomeback.other;

import android.view.View.OnClickListener;
import android.view.View;
import pansong291.menuhomeback.R;
import android.view.KeyEvent;

public class MyBtnClickListener implements OnClickListener
{
// private View view;
// 
// public MyBtnClickListener(View v)
// {
//  view=v;
// }
 
 @Override
 public void onClick(View p1)
 {
  switch(p1.getId())
  {
//   case R.id.float_btn_main:
//    view.setVisibility(8-view.getVisibility());
//   break;
   case R.id.float_btn_menu:
    RootShellCmd.getRSC().simulateKey(KeyEvent.KEYCODE_MENU);
   break;
   case R.id.float_btn_home:
    RootShellCmd.getRSC().simulateKey(KeyEvent.KEYCODE_HOME);
   break;
   case R.id.float_btn_back:
    RootShellCmd.getRSC().simulateKey(KeyEvent.KEYCODE_BACK);
   break;
  }
 }
 
}
