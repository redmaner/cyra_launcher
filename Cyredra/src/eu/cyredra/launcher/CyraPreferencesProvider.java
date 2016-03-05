package eu.cyredra.launcher;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.preference.PreferenceScreen;
import android.util.Log;
import android.view.View;

public final class CyraPreferencesProvider{

	/**
	 ** This key is used by launcher to determine if Cyra Launcher settings are set on first run
	 **/
	public static final String INITIAL_SET = "CYRA_SETTINGS_SET";

	// Workspace - Preference keys
	public static final String KEY_WORKSPACE_ROWS = "pref_key_workspaceRows";
	public static final String KEY_WORKSPACE_COLS = "pref_key_workspaceCols";
	public static final String KEY_WORKSPACE_ICONSIZE = "pref_key_workspaceIconSize";
	public static final String KEY_WORKSPACE_LABEL_COLOR = "pref_key_workspaceLabelColor";
	public static final String KEY_WORKSPACE_WALLPAPER_SCROLL = "pref_key_workspaceWallpaperScroll";
	public static final String KEY_WORKSPACE_INFINITE_SCROLL = "pref_key_workspaceInfiniteScroll";
	public static final String KEY_WORKSPACE_EDGE = "pref_key_workspaceEdgeMargin";

	// Overview - Preference keys
	public static final String KEY_OVERVIEW_WALLPAPER = "pref_key_overviewTileWallpaper";
	public static final String KEY_OVERVIEW_WIDGETS = "pref_key_overviewTileWidgets";
	public static final String KEY_OVERVIEW_APPS = "pref_key_overviewTileApps";
	public static final String KEY_OVERVIEW_SETTINGS = "pref_key_overviewTileSettings";

	// Hotseat - Preference keys
	public static final String KEY_HOTSEAT_ICONSIZE = "pref_key_hotseatIconSize";
	public static final String KEY_HOTSEAT_ICONS = "pref_key_hotseatIcons";

	// Icons - Preference keys
	public static final String KEY_ICON_BRIGHTNESS = "pref_key_iconBrightness";
	public static final String KEY_ICON_SATURATION = "pref_key_iconSaturation";
	public static final String KEY_ICON_CONTRAST = "pref_key_iconContrast";
	public static final String KEY_ICON_HUE = "pref_key_iconHue";
	public static final String KEY_ICON_ALPHA = "pref_key_iconAlpha";
	public static final String KEY_ICON_MASK = "pref_key_iconMask";
	public static final String KEY_ICON_MASK_COLOR = "pref_key_iconMaskColor";
	public static final String KEY_ICON_MASK_RANDOM = "pref_key_iconMaskRandom";
	public static final String KEY_ICON_PRESET = "pref_key_iconPreset";	
	public static final String KEY_ICON_PACK = "pref_key_iconpack";

	// App drawer - Preference keys
    public static final String KEY_DRAWER_STYLE = "pref_key_drawerStyle";
	public static final String KEY_DRAWER_ICONSIZE = "pref_key_drawerIconSize";
    public static final String KEY_DRAWER_LABEL_COLOR = "pref_key_drawerLabelColor";

	// Gestures - Preference keys
	public static final String KEY_GESTURE_DOWN = "pref_key_gestureDown";
	public static final String KEY_GESTURE_UP = "pref_key_gestureUp";
	public static final String KEY_GESTURE_LONGPRESS = "pref_key_gestureLongpress";

	// General - Preference keys
	public static final String KEY_ANIMATION_PROFILE = "pref_key_animationProfile";
	public static final String KEY_ALLOW_ROTATION = "pref_allowRotation";
	public static final String KEY_CYRA_ADMIN = "pref_key_cyraAdmin";

	// Animations - Preference keys
	public static final String ANIM_OVERLAY_REVEAL_TIME = "pref_anim_overlayRevealTime";
	public static final String ANIM_OVERLAY_TRANSITION_TIME = "pref_anim_overlayTransitionTime";
	public static final String ANIM_ALLAPPS_TRANSITION_TIME = "pref_anim_allAppsTransitionTime";
	public static final String ANIM_OVERVIEW_TRANSITION_TIME = "pref_anim_overviewTransitionTime";

	// Cyra initial load
	private static boolean isInitialLoaded = false;

	// Workspace - Variables
	private static int mWorkspaceRows;
	private static int mWorkspaceCols;
	private static int mWorkspaceIconSize = 95;
	private static int mWorkspaceLabelColor;
	private static boolean mWorkspaceWallpaperScroll = false;
	private static boolean mWorkspaceInfiniteScroll = false;
	private static String mWorkspaceEdgeMargin = "MARGIN_TIGHT";

	// Overview - Variables
	private static boolean mOverviewWallpaper = true;
	private static boolean mOverviewWidgets = true;
	private static boolean mOverviewApps = false;
	private static boolean mOverviewSettings = false;

	// Hotseat - Variables
	private static int mHotseatIconSize = 95;
	private static int mHotseatIcons;

	// Icons - Variables
	private static int mIconBrightness = 100;
	private static int mIconSaturation = 100;
	private static int mIconContrast = 100;
	private static int mIconHue = 180;
	private static int mIconAlpha = 100;
	private static boolean mIconMask = false;
	private static int mIconMaskColor;
	private static boolean mIconMaskRandom = false;
	private static String mIconPreset;
	private static String mIconPack;

	// App drawer - Variables
	private static String mDrawerStyle = "DRAWER_ZERO";
	private static int mDrawerIconSize = 95;
	private static int mDrawerLabelColor = -1;

	// Gestures - Variables
	private static String mGestureDown = "GES_NOTIFICATIONS";
	private static String mGestureUp = "GES_LOCKSCREEN";
	private static String mGestureLongpress = "GES_OVERVIEW";

	// General - Variables
	private static String mAnimationProfile = "ANIMATION_CYRA";
	private static boolean mAllowRotation = false;
	private static boolean mCyraAdmin = false;

	// Animations - Variables
	private static int mAnimOverlayRevealTime = 220;
	private static int mAnimOverlayTransitionTime = 300;
	private static int mAnimAllAppsTransitionTime = 100;
	private static int mAnimOverviewTransitionTime = 250;

	// Load non deviceprofile preferences in memory at first run
	public static void loadInitialCyraPreferences(Context context) {

		// Workspace 
		mWorkspaceLabelColor = PreferenceManager.getDefaultSharedPreferences(context)
                        .getInt(KEY_WORKSPACE_LABEL_COLOR, 0);
		mWorkspaceWallpaperScroll = PreferenceManager.getDefaultSharedPreferences(context)
                        .getBoolean(KEY_WORKSPACE_WALLPAPER_SCROLL, true);
		mWorkspaceInfiniteScroll = PreferenceManager.getDefaultSharedPreferences(context)
                        .getBoolean(KEY_WORKSPACE_INFINITE_SCROLL, true);	

		// Overview
		mOverviewWallpaper = PreferenceManager.getDefaultSharedPreferences(context)
                        .getBoolean(KEY_OVERVIEW_WALLPAPER, true);	
		mOverviewWidgets = PreferenceManager.getDefaultSharedPreferences(context)
                        .getBoolean(KEY_OVERVIEW_WIDGETS, true);
		mOverviewApps = PreferenceManager.getDefaultSharedPreferences(context)
                        .getBoolean(KEY_OVERVIEW_APPS, true);
		mOverviewSettings = PreferenceManager.getDefaultSharedPreferences(context)
                        .getBoolean(KEY_OVERVIEW_SETTINGS, true);

		// Icons
		mIconBrightness = PreferenceManager.getDefaultSharedPreferences(context)
                        .getInt(KEY_ICON_BRIGHTNESS, 0);
		mIconSaturation = PreferenceManager.getDefaultSharedPreferences(context)
                        .getInt(KEY_ICON_SATURATION, 0);
		mIconContrast = PreferenceManager.getDefaultSharedPreferences(context)
                        .getInt(KEY_ICON_CONTRAST, 0);
		mIconHue = PreferenceManager.getDefaultSharedPreferences(context)
                        .getInt(KEY_ICON_HUE, 0);
		mIconAlpha = PreferenceManager.getDefaultSharedPreferences(context)
                        .getInt(KEY_ICON_ALPHA, 0);
		mIconMask = PreferenceManager.getDefaultSharedPreferences(context)
                        .getBoolean(KEY_ICON_MASK, true);
		mIconMaskColor = PreferenceManager.getDefaultSharedPreferences(context)
                        .getInt(KEY_ICON_MASK_COLOR, 0);
		mIconMaskRandom = PreferenceManager.getDefaultSharedPreferences(context)
                        .getBoolean(KEY_ICON_MASK_RANDOM, true);
		mIconPreset = PreferenceManager.getDefaultSharedPreferences(context)
                        .getString(KEY_ICON_PRESET, "");
		mIconPack = PreferenceManager.getDefaultSharedPreferences(context)
                        .getString(KEY_ICON_PACK, "");

		// App Drawer
		mDrawerStyle = PreferenceManager.getDefaultSharedPreferences(context)
                        .getString(KEY_DRAWER_STYLE, "DRAWER_ZERO");
		mDrawerLabelColor = PreferenceManager.getDefaultSharedPreferences(context)
                        .getInt(KEY_DRAWER_LABEL_COLOR, 0);

		// Gestures		
		mGestureDown = PreferenceManager.getDefaultSharedPreferences(context)
                        .getString(KEY_GESTURE_DOWN, "GES_NONE");
		mGestureUp = PreferenceManager.getDefaultSharedPreferences(context)
                        .getString(KEY_GESTURE_UP, "GES_NONE");
		mGestureLongpress = PreferenceManager.getDefaultSharedPreferences(context)
                        .getString(KEY_GESTURE_LONGPRESS, "GES_NONE");

		// General
		mAnimationProfile = PreferenceManager.getDefaultSharedPreferences(context)
                        .getString(KEY_ANIMATION_PROFILE, "ANIMATION_CYRA");
		mAllowRotation = PreferenceManager.getDefaultSharedPreferences(context)
                        .getBoolean(KEY_ALLOW_ROTATION, false);
		mCyraAdmin = PreferenceManager.getDefaultSharedPreferences(context)
                        .getBoolean(KEY_CYRA_ADMIN, false);

		// Animations
		mAnimOverlayRevealTime = PreferenceManager.getDefaultSharedPreferences(context)
                        .getInt(ANIM_OVERLAY_REVEAL_TIME, 0);
		mAnimOverlayTransitionTime = PreferenceManager.getDefaultSharedPreferences(context)
                        .getInt(ANIM_OVERLAY_TRANSITION_TIME, 0);
		mAnimAllAppsTransitionTime = PreferenceManager.getDefaultSharedPreferences(context)
                        .getInt(ANIM_ALLAPPS_TRANSITION_TIME, 0);
		mAnimOverviewTransitionTime = PreferenceManager.getDefaultSharedPreferences(context)
                        .getInt(ANIM_OVERVIEW_TRANSITION_TIME, 0);

		isInitialLoaded = true;

	}	

	public static void loadCyraWorkspacePreferences(Context context) {

		// Workspace 
		mWorkspaceLabelColor = PreferenceManager.getDefaultSharedPreferences(context)
                        .getInt(KEY_WORKSPACE_LABEL_COLOR, 0);
		mWorkspaceWallpaperScroll = PreferenceManager.getDefaultSharedPreferences(context)
                        .getBoolean(KEY_WORKSPACE_WALLPAPER_SCROLL, true);
		mWorkspaceInfiniteScroll = PreferenceManager.getDefaultSharedPreferences(context)
                        .getBoolean(KEY_WORKSPACE_INFINITE_SCROLL, true);	

	}

	public static void loadCyraOverviewPreferences(Context context) {

		// Overview
		mOverviewWallpaper = PreferenceManager.getDefaultSharedPreferences(context)
                        .getBoolean(KEY_OVERVIEW_WALLPAPER, true);	
		mOverviewWidgets = PreferenceManager.getDefaultSharedPreferences(context)
                        .getBoolean(KEY_OVERVIEW_WIDGETS, true);
		mOverviewApps = PreferenceManager.getDefaultSharedPreferences(context)
                        .getBoolean(KEY_OVERVIEW_APPS, true);
		mOverviewSettings = PreferenceManager.getDefaultSharedPreferences(context)
                        .getBoolean(KEY_OVERVIEW_SETTINGS, true);

	}

	public static void loadCyraIconPreferences(Context context) {

		// Icons
		mIconBrightness = PreferenceManager.getDefaultSharedPreferences(context)
                        .getInt(KEY_ICON_BRIGHTNESS, 0);
		mIconSaturation = PreferenceManager.getDefaultSharedPreferences(context)
                        .getInt(KEY_ICON_SATURATION, 0);
		mIconContrast = PreferenceManager.getDefaultSharedPreferences(context)
                        .getInt(KEY_ICON_CONTRAST, 0);
		mIconHue = PreferenceManager.getDefaultSharedPreferences(context)
                        .getInt(KEY_ICON_HUE, 0);
		mIconAlpha = PreferenceManager.getDefaultSharedPreferences(context)
                        .getInt(KEY_ICON_ALPHA, 0);
		mIconMask = PreferenceManager.getDefaultSharedPreferences(context)
                        .getBoolean(KEY_ICON_MASK, true);
		mIconMaskColor = PreferenceManager.getDefaultSharedPreferences(context)
                        .getInt(KEY_ICON_MASK_COLOR, 0);
		mIconMaskRandom = PreferenceManager.getDefaultSharedPreferences(context)
                        .getBoolean(KEY_ICON_MASK_RANDOM, true);
		mIconPreset = PreferenceManager.getDefaultSharedPreferences(context)
                        .getString(KEY_ICON_PRESET, "");
		mIconPack = PreferenceManager.getDefaultSharedPreferences(context)
                        .getString(KEY_ICON_PACK, "");

	}

	public static void loadCyraAllAppsPreferences(Context context) {

		// App Drawer
		mDrawerStyle = PreferenceManager.getDefaultSharedPreferences(context)
                        .getString(KEY_DRAWER_STYLE, "DRAWER_ZERO");
		mDrawerLabelColor = PreferenceManager.getDefaultSharedPreferences(context)
                        .getInt(KEY_DRAWER_LABEL_COLOR, 0);

	}

	public static void loadCyraGesturePreferences(Context context) {

		// Gestures		
		mGestureDown = PreferenceManager.getDefaultSharedPreferences(context)
                        .getString(KEY_GESTURE_DOWN, "GES_NONE");
		mGestureUp = PreferenceManager.getDefaultSharedPreferences(context)
                        .getString(KEY_GESTURE_UP, "GES_NONE");
		mGestureLongpress = PreferenceManager.getDefaultSharedPreferences(context)
                        .getString(KEY_GESTURE_LONGPRESS, "GES_NONE");

	}

	public static void loadCyraGeneralPreferences(Context context) {

		// General
		mAnimationProfile = PreferenceManager.getDefaultSharedPreferences(context)
                        .getString(KEY_ANIMATION_PROFILE, "ANIMATION_CYRA");
		mAllowRotation = PreferenceManager.getDefaultSharedPreferences(context)
                        .getBoolean(KEY_ALLOW_ROTATION, false);
		mCyraAdmin = PreferenceManager.getDefaultSharedPreferences(context)
                        .getBoolean(KEY_CYRA_ADMIN, false);

	}

	public static void loadCyraAnimationPreferences(Context context) {

		// Animations
		mAnimOverlayRevealTime = PreferenceManager.getDefaultSharedPreferences(context)
                        .getInt(ANIM_OVERLAY_REVEAL_TIME, 0);
		mAnimOverlayTransitionTime = PreferenceManager.getDefaultSharedPreferences(context)
                        .getInt(ANIM_OVERLAY_TRANSITION_TIME, 0);
		mAnimAllAppsTransitionTime = PreferenceManager.getDefaultSharedPreferences(context)
                        .getInt(ANIM_ALLAPPS_TRANSITION_TIME, 0);
		mAnimOverviewTransitionTime = PreferenceManager.getDefaultSharedPreferences(context)
                        .getInt(ANIM_OVERVIEW_TRANSITION_TIME, 0);

	}

	public static void loadCyraDeviceProfile(Context context) {

		// Workspace
		mWorkspaceRows = PreferenceManager.getDefaultSharedPreferences(context)
                        .getInt(KEY_WORKSPACE_ROWS, 0);
		mWorkspaceCols = PreferenceManager.getDefaultSharedPreferences(context)
                        .getInt(KEY_WORKSPACE_COLS, 0);
		mWorkspaceIconSize = PreferenceManager.getDefaultSharedPreferences(context)
                        .getInt(KEY_WORKSPACE_ICONSIZE, 0);
		mWorkspaceEdgeMargin = PreferenceManager.getDefaultSharedPreferences(context)
                        .getString(KEY_WORKSPACE_EDGE, "");

		// Hotseat
		mHotseatIconSize = PreferenceManager.getDefaultSharedPreferences(context)
                        .getInt(KEY_HOTSEAT_ICONSIZE, 0);
		mHotseatIcons = PreferenceManager.getDefaultSharedPreferences(context)
                        .getInt(KEY_HOTSEAT_ICONS, 0);

		// App drawer
		mDrawerIconSize = PreferenceManager.getDefaultSharedPreferences(context)
                        .getInt(KEY_DRAWER_ICONSIZE, 0);
	}

	public static boolean isInitialLoaded() {
		return isInitialLoaded;
	}

	// Workspace
	public static int getWorkspaceRows() {
		return mWorkspaceRows;
	}

	public static int getWorkspaceCols() {
		return mWorkspaceCols;
	}

	public static int getWorkspaceIconSize() {
		return mWorkspaceIconSize;
	}

	public static int getWorkspaceLabelColor() {
		return mWorkspaceLabelColor;
	}

	public static boolean getWorkspaceWallpaperScroll() {
		return mWorkspaceWallpaperScroll;
	}

	public static boolean getWorkspaceInfiniteScroll() {
		return mWorkspaceInfiniteScroll;
	}

	public static String getWorkspaceEdgeMargin() {
		return mWorkspaceEdgeMargin;
	}

	// Overview 
	public static boolean getOverviewWallpaper() {
		return mOverviewWallpaper;
	}
	public static boolean getOverviewWidgets() {
		return mOverviewWidgets;
	}
	public static boolean getOverviewApps() {
		return mOverviewApps;
	}
	public static boolean getOverviewSettings() {
		return mOverviewSettings;
	}

	// Hotseat
	public static int getHotseatIconSize() {
		return mHotseatIconSize;
	}
	public static int getHotseatIcons() {
		return mHotseatIcons;
	}

	// Icons
	public static int getIconBrightness() {
		return mIconBrightness;
	}
	public static int getIconSaturation() {
		return mIconSaturation;
	}
	public static int getIconContrast() {
		return mIconContrast;
	}
	public static int getIconHue() {
		return mIconHue;
	}
	public static int getIconAlpha() {
		return mIconAlpha;
	}
	public static boolean getIconMask() {
		return mIconMask;
	}
	public static int getIconMaskColor() {
		return mIconMaskColor;	
	}
	public static boolean getIconMaskRandom() {
		return mIconMaskRandom;
	}
	public static String getIconPreset() {
		return mIconPreset;
	}
	public static String getIconPack() {
		return mIconPack;
	}

	// App drawer
	public static String getDrawerStyle() {
		return mDrawerStyle;
	}
	public static int getDrawerIconSize() {
		return mDrawerIconSize;
	}
	public static int getDrawerLabelColor() {
		return mDrawerLabelColor;
	}

	// Gestures
	public static String getGestureDown() {
		return mGestureDown;
	}
	public static String getGestureUp() {
		return mGestureUp;
	}
	public static String getGestureLongpress() {
		return mGestureLongpress;
	}

	// General
	public static String getAnimationProfile() {
		return mAnimationProfile;
	}
	public static boolean getAllowRotation() {
		return mAllowRotation;
	}
	public static boolean getCyraAdmin() {
		return mCyraAdmin;
	}

	// Animations 
	public static int getAnimOverlayRevealTime() {
		return mAnimOverlayRevealTime;
	}
	public static int getAnimOverlayTransitionTime() {
		return mAnimOverlayTransitionTime;
	}
	public static int getAnimAllAppsTransitionTime() {
		return mAnimAllAppsTransitionTime;
	}
	public static int getAnimOverviewTransitionTime() {
		return mAnimOverviewTransitionTime;
	}

    public static boolean isCyraPreference(String key) {
        return key.equals(KEY_WORKSPACE_LABEL_COLOR)
 			|| key.equals(KEY_WORKSPACE_WALLPAPER_SCROLL)
 			|| key.equals(KEY_WORKSPACE_INFINITE_SCROLL)
 			|| key.equals(KEY_OVERVIEW_WALLPAPER)
 			|| key.equals(KEY_OVERVIEW_WIDGETS)
 			|| key.equals(KEY_OVERVIEW_APPS)
 			|| key.equals(KEY_OVERVIEW_SETTINGS)
 			|| key.equals(KEY_ICON_BRIGHTNESS)
 			|| key.equals(KEY_ICON_SATURATION)
 			|| key.equals(KEY_ICON_CONTRAST)
 			|| key.equals(KEY_ICON_HUE)
 			|| key.equals(KEY_ICON_ALPHA)
 			|| key.equals(KEY_ICON_MASK)
 			|| key.equals(KEY_ICON_MASK_COLOR)
 			|| key.equals(KEY_ICON_MASK_RANDOM)
 			|| key.equals(KEY_ICON_PRESET)
 			|| key.equals(KEY_ICON_PACK)
			|| key.equals(KEY_DRAWER_STYLE)
			|| key.equals(KEY_DRAWER_LABEL_COLOR)
 			|| key.equals(KEY_GESTURE_DOWN)
 			|| key.equals(KEY_GESTURE_UP)
 			|| key.equals(KEY_GESTURE_LONGPRESS)
			|| key.equals(KEY_ANIMATION_PROFILE)
			|| key.equals(KEY_ALLOW_ROTATION)
			|| key.equals(KEY_CYRA_ADMIN)
			|| key.equals(ANIM_OVERLAY_REVEAL_TIME)
			|| key.equals(ANIM_OVERLAY_TRANSITION_TIME)
			|| key.equals(ANIM_ALLAPPS_TRANSITION_TIME)
			|| key.equals(ANIM_OVERVIEW_TRANSITION_TIME);
    }

    public static boolean isCyraWorkspacePreference(String key) {
        return key.equals(KEY_WORKSPACE_LABEL_COLOR)
 			|| key.equals(KEY_WORKSPACE_WALLPAPER_SCROLL)
 			|| key.equals(KEY_WORKSPACE_INFINITE_SCROLL);

	}

	public static boolean isCyraOverviewPreference(String key) {
 		return key.equals(KEY_OVERVIEW_WALLPAPER)
 			|| key.equals(KEY_OVERVIEW_WIDGETS)
 			|| key.equals(KEY_OVERVIEW_APPS)
 			|| key.equals(KEY_OVERVIEW_SETTINGS);
	}

	public static boolean isCyraIconPreference(String key) {
 		return key.equals(KEY_ICON_BRIGHTNESS)
 			|| key.equals(KEY_ICON_SATURATION)
 			|| key.equals(KEY_ICON_CONTRAST)
 			|| key.equals(KEY_ICON_HUE)
 			|| key.equals(KEY_ICON_ALPHA)
 			|| key.equals(KEY_ICON_MASK)
 			|| key.equals(KEY_ICON_MASK_COLOR)
 			|| key.equals(KEY_ICON_MASK_RANDOM)
 			|| key.equals(KEY_ICON_PRESET)
 			|| key.equals(KEY_ICON_PACK);
	}

	public static boolean isCyraAllAppsPreference(String key) {
		return key.equals(KEY_DRAWER_STYLE)
			|| key.equals(KEY_DRAWER_LABEL_COLOR);
	}

	public static boolean isCyraGesturePreference(String key) {
 		return key.equals(KEY_GESTURE_DOWN)
 			|| key.equals(KEY_GESTURE_UP)
 			|| key.equals(KEY_GESTURE_LONGPRESS);
	}

	public static boolean isCyraGeneralPreference(String key) {
		return key.equals(KEY_ALLOW_ROTATION)
			|| key.equals(KEY_ANIMATION_PROFILE)
			|| key.equals(KEY_CYRA_ADMIN);
	}

	public static boolean isCyraAnimationPreference(String key) {
		return key.equals(ANIM_OVERLAY_REVEAL_TIME)
			|| key.equals(ANIM_OVERLAY_TRANSITION_TIME)
			|| key.equals(ANIM_ALLAPPS_TRANSITION_TIME)
			|| key.equals(ANIM_OVERVIEW_TRANSITION_TIME);
    }

    public static boolean isCyraDeviceProfile(String key) {
        return key.equals(KEY_WORKSPACE_ROWS)
			|| key.equals(KEY_WORKSPACE_COLS)
			|| key.equals(KEY_WORKSPACE_ICONSIZE)
			|| key.equals(KEY_WORKSPACE_EDGE)
			|| key.equals(KEY_HOTSEAT_ICONSIZE)
			|| key.equals(KEY_HOTSEAT_ICONS)
			|| key.equals(KEY_DRAWER_ICONSIZE);
	}
}
