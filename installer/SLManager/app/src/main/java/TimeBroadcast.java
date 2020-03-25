import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class TimeBroadcast extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "广播我已经启动服务了", Toast.LENGTH_SHORT).show();
        System.out.println("广播我已经启动服务了");

//        Intent service = new Intent(context,TestService.class);
//        service.setAction(TestActivity.ACTION_Service);
////		在广播中启动服务必须加上startService(intent)的修饰语。Context是对象
//        context.startService(service);
    }
}
