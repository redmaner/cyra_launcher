package eu.cyredra.launcher;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.DisplayMetrics;

import eu.cyredra.launcher.CyraPreferencesProvider;

public final class DpiChanger {

	public static void ChangeDpi (Context mContext, Resources res, DisplayMetrics dm) {

		CyraPreferencesProvider.loadCyraGeneralPreferences(mContext);
		String mCyraDpi = CyraPreferencesProvider.getCyraDpi();
		int mDpi = 0;

		switch (mCyraDpi) {
			case "DPI_DEFAULT":
				break;
			case "DPI_LOW":
				mDpi = DisplayMetrics.DENSITY_LOW;
				break;
			case "DPI_MEDIUM":
				mDpi = DisplayMetrics.DENSITY_MEDIUM;
				break;
			case "DPI_HIGH":
				mDpi = DisplayMetrics.DENSITY_HIGH;
				break;
			case "DPI_280":
				mDpi = DisplayMetrics.DENSITY_280;
				break;
			case "DPI_XHIGH":
				mDpi = DisplayMetrics.DENSITY_XHIGH;
				break;
			case "DPI_360":
				mDpi = DisplayMetrics.DENSITY_360;
				break;
			case "DPI_400":
				mDpi = DisplayMetrics.DENSITY_400;
				break;
			case "DPI_420":
				mDpi = DisplayMetrics.DENSITY_420;
				break;
			case "DPI_XXHIGH":
				mDpi = DisplayMetrics.DENSITY_XXHIGH;
				break;
			case "DPI_560":
				mDpi = DisplayMetrics.DENSITY_560;
				break;
			case "DPI_XXXHIGH":
				mDpi = DisplayMetrics.DENSITY_XXXHIGH;
				break;
			default:
				break;
		}				

		Configuration config = res.getConfiguration();
		dm.densityDpi = mDpi;
		config.densityDpi = mDpi;

		res.updateConfiguration(config, dm);
	}

}
