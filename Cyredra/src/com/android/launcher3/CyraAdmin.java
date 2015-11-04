package com.android.launcher3;  
  
import android.app.admin.DeviceAdminReceiver;  
import android.content.Context;  
import android.content.Intent;  
import android.widget.Toast;  

import eu.cyredra.launcher.R;
  
public class CyraAdmin extends DeviceAdminReceiver{  
  
  
    void showToast(Context context, CharSequence msg) {  
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();  
    }  
  
    @Override  
    public void onEnabled(Context context, Intent intent) {  
        showToast(context, context.getString(R.string.cyra_admin_enabled));  
    }  
  
    @Override  
    public CharSequence onDisableRequested(Context context, Intent intent) {  
        return context.getString(R.string.cyra_admin_disable_requested);  
    }  
  
    @Override  
    public void onDisabled(Context context, Intent intent) {  
        showToast(context, context.getString(R.string.cyra_admin_disabled));  
    }   
}   
