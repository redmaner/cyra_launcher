package eu.cyredra.launcher;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceActivity;
import android.preference.PreferenceActivity.Header;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.preference.PreferenceScreen;
import android.util.Log;
import android.view.View;
import android.widget.ListAdapter;

import com.android.launcher3.DeviceProfile;
import com.android.launcher3.IconPackHelper;
import com.android.launcher3.InvariantDeviceProfile;
import com.android.launcher3.LauncherAppState;

import java.util.List;

public final class LauncherPreferencesActivity extends PreferenceActivity {

	public static final String KEY_WORKSPACE_ROWS = "pref_key_workspaceRows";
	public static final String KEY_WORKSPACE_COLS = "pref_key_workspaceCols";

	public static final String KEY_DRAWER_ROWS = "pref_key_drawerRows";
	public static final String KEY_DRAWER_COLS = "pref_key_drawerCols";

 	private static final String TAG = "LauncherPreferences";

	public static LauncherPreferencesActivity instance = null;

	private static Context context;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
		instance = this;
		LauncherPreferencesActivity.context = this;
		
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);

		/**
        InvariantDeviceProfile inv = LauncherAppState.getInstance().getInvariantDeviceProfile();

        if(inv != null) {
          	// initialize default values from current Profile

            SharedPreferences.Editor editor = prefs.edit();

			// Default workspace grid
            if(prefs.getInt(KEY_WORKSPACE_ROWS, 0) < 1) {
          	    Log.i(TAG,"Loading r default value from: "+inv.toString());
                editor.putInt(KEY_WORKSPACE_ROWS, (int)inv.numRows);
            } 
            if(prefs.getInt(KEY_WORKSPACE_COLS, 0) < 1) {
                Log.i(TAG,"Loading c default value from: "+inv.toString());
                editor.putInt(KEY_WORKSPACE_COLS, (int)inv.numColumns);
            }

			// Default app drawer grid
            if(prefs.getInt(KEY_DRAWER_ROWS, 0) < 1) {
          	    Log.i(TAG,"Loading adr default value from: "+inv.toString());
                editor.putInt(KEY_DRAWER_ROWS, (int)inv.numFolderRows);
            } 
            if(prefs.getInt(KEY_DRAWER_COLS, 0) < 1) {
                Log.i(TAG,"Loading adc default value from: "+inv.toString());
                editor.putInt(KEY_DRAWER_COLS, (int)inv.numFolderColumns);
            }

            editor.apply();
        } else {
            Log.w(TAG, "No DynamicGrid to get default values!");
        }
		**/

    }

    @Override
    public void onBuildHeaders(List<Header> target) {
        loadHeadersFromResource(R.xml.preferences_headers, target);
    }

 	public static class WorkspaceFragment extends PreferenceFragment {
		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			addPreferencesFromResource(R.xml.preferences_workspace);
		}
	}

 	public static class OverviewFragment extends PreferenceFragment {
		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			addPreferencesFromResource(R.xml.preferences_overview);
		}
	}

 	public static class DrawerFragment extends PreferenceFragment {
		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			addPreferencesFromResource(R.xml.preferences_drawer);
		}
	}

 	public static class IconsFragment extends PreferenceFragment {
		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			addPreferencesFromResource(R.xml.preferences_icons);

			Preference mIconPack = (Preference) findPreference("pref_key_iconpack");
			mIconPack.setOnPreferenceClickListener(new OnPreferenceClickListener() {
            	public boolean onPreferenceClick(Preference preference) {
                	IconPackHelper.pickIconPack(getAppContext(), false);
					return false;
             	}
         	});
		}
	}

 	public static class GesturesFragment extends PreferenceFragment {
		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			addPreferencesFromResource(R.xml.preferences_gestures);
		}
	}

 	public static class GeneralFragment extends PreferenceFragment {
		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			addPreferencesFromResource(R.xml.preferences_general);
		}
	}

	@Override
	protected boolean isValidFragment(String fragmentName) {
   		return WorkspaceFragment.class.getName().equals(fragmentName)
            || OverviewFragment.class.getName().equals(fragmentName)
            || DrawerFragment.class.getName().equals(fragmentName)
            || IconsFragment.class.getName().equals(fragmentName)
            || GesturesFragment.class.getName().equals(fragmentName)
            || GeneralFragment.class.getName().equals(fragmentName);
	}

	@Override
	public void finish() {
    	super.finish();
    	instance = null;
	}

    public static Context getAppContext() {
        return LauncherPreferencesActivity.context;
    }
}
