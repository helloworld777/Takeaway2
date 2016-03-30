package util;



import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;

public class DialogUtil {
	private static AlertDialog dialog=null;
	/**
	 * @param context
	 */
	public static void showToast(Context context,String msg){
		TopNoticeDialog.showToast(context,msg);
	}
	
	/**
	 * @param context
	 */
	public static  void showAlertDialog(Context context,String title,String[] items,OnClickListener dialogInterface){
		AlertDialog.Builder builder=new AlertDialog.Builder(context);
		builder.setTitle(title).setItems(items,dialogInterface);
		dialog=builder.create();
		dialog.show();
	}
	/**
	 */
	public static  void showAlertDialog(Context context,String title,int items,OnClickListener dialogInterface){
		AlertDialog.Builder builder=new AlertDialog.Builder(context);
		builder.setTitle(title).setItems(items,dialogInterface);
		dialog=builder.create();
		dialog.show();
	}
	/**
	 */
	public static  void showMessageAlertDialog(Context context,String title, String msg,OnClickListener dialogInterface){
		AlertDialog.Builder builder=new AlertDialog.Builder(context);
		builder.setTitle(title).setMessage(msg).setPositiveButton("OK",dialogInterface);
		dialog=builder.create();
		dialog.show();
	}
	/**
	 */
	public static void closeAlertDialog(){
		if(dialog!=null && dialog.isShowing()){
			dialog.dismiss();
		}
	}
	/**
	 * show a closeDialog
	 * @param context
	 */
	public static void showExitAlertDialog(Context context,OnClickListener dialogInterface){
		AlertDialog.Builder builder=new AlertDialog.Builder(context);
		builder.setTitle("close ").setMessage("sure close?")
		.setNegativeButton("ok", new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				closeAlertDialog();
			}
		})
		.setPositiveButton("cancel", dialogInterface);
		dialog=builder.create();
		dialog.show();
	}
	
	
	public static void showWaitDialog(Context context,String title,String message){
		dialog=ProgressDialog.show(context, title, message);
	}
}
