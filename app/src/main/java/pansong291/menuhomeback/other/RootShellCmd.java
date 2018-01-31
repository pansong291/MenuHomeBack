package pansong291.menuhomeback.other;

import java.io.DataOutputStream;
import java.io.OutputStream;

public class RootShellCmd
{
 private OutputStream os=null;
 private static RootShellCmd rsc=new RootShellCmd();

 /**
  * 执行shell指令
  * 
  * @param cmd
  *        指令
  */
 public final void exec(String cmd)
 {
  try
  {
   if(os==null)
   {
    os=Runtime.getRuntime().exec("su").getOutputStream();
   }
   os.write(cmd.getBytes());
   os.flush();
  }catch(Exception e)
  {
   e.printStackTrace();
  }
 }

 /**
  * 后台模拟全局按键
  * 
  * @param keyCode
  *          键值
  */
 public final void simulateKey(int keyCode)
 {
  exec("input keyevent "+keyCode+"\n");
 }
 
 public void close()
 {
  try{
   if(os!=null)
   {
    os.close();
    os=null;
   }
  }catch(Exception e){}
 }
 
 public boolean checkRootPermission()
 {
  int result=-1;

  Process process=null;
  String command="echo root";

  DataOutputStream dos=null;
  try
  {
   process=Runtime.getRuntime().exec("su");
   dos=new DataOutputStream(process.getOutputStream());

   // donnot use os.writeBytes(commmand), avoid chinese charset error
   dos.write(command.getBytes());
   dos.writeBytes("\nexit\n");
   dos.flush();

   result=process.waitFor();

   if(process!=null)
   {
    process.destroy();
   }
  }catch(Exception e)
  {
  }finally
  {
   try{
    if(dos!=null)
     dos.close();
   }catch(Exception e){}
  }
  return result==0;
 }
 
 private RootShellCmd(){}
 
 public static RootShellCmd getRSC()
 {
  return rsc;
 }
 
}
