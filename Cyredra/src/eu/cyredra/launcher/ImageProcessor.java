/*
 * Copyright (C) 2015 Jake van der Putten
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package eu.cyredra.launcher;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffColorFilter;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.PaintDrawable;
import android.preference.PreferenceManager;

import java.util.Random;

public final class ImageProcessor {

	public static Drawable enhanceIcon(Drawable image, int mIconBrightness, int mIconSaturation,
										int mIconContrast, int mIconHue, int mIconAlpha) {
		ColorMatrix matrix = new ColorMatrix();

		matrix.postConcat(adjustBrightness(mIconBrightness));
		matrix.postConcat(adjustSaturation(mIconSaturation));
		matrix.postConcat(adjustContrast(mIconContrast));
		matrix.postConcat(adjustHue(mIconHue));
		matrix.postConcat(adjustAlpha(mIconAlpha));

		ColorMatrixColorFilter filter = new ColorMatrixColorFilter(matrix);
		image.setColorFilter(filter);

		return image;
	}

	public static int randomizeColor() {

		Random rand = new Random();

		int rc = rand.nextInt(5);
		int r;
		int g;
		int b;

		switch (rc) {
			case 0: //Material Red: #f44336
				r = 244;
				g = 67;
				b = 54;
				break;
			case 1: //Material Pink: #e91e63
				r = 233;
				g = 30;
				b = 99;
				break;
			case 2: //Material Blue: #2196f3
				r = 33;
				g = 150;
				b = 243;
				break;	
			case 3: //Material Green: #8bc34a
				r = 139;
				g = 195;
				b = 74;
				break;
			case 4: //Material Yellow: #ffeb3b
				r = 255;
				g = 235;
				b = 59;
				break;
			default:
				r = 33;
				g = 150;
				b = 243;
				break;					
		}
		
		int randomColor = Color.rgb(r,g,b);
		return randomColor;
	}

	public static int makeColor(int r, int g, int b) {
		int color = Color.rgb(r, g, b);
		return color;
	}


   /**
    * See the following links for reference
    * http://groups.google.com/group/android-developers/browse_thread/thread/9e215c83c3819953
    * http://gskinner.com/blog/archives/2007/12/colormatrix_cla.html
    * @param value
    */
    public static ColorMatrix adjustHue(int hueVal) {
    	ColorMatrix cm = new ColorMatrix();
		hueVal = hueVal - 180;
        float value = hueVal / 180 * (float) Math.PI;
        if (value != 0) {
    	    float cosVal = (float) Math.cos(value);
            float sinVal = (float) Math.sin(value);
            float lumR = 0.213f;
            float lumG = 0.715f;
            float lumB = 0.072f;
            float[] mat = new float[]{
    	        lumR + cosVal * (1 - lumR) + sinVal * (-lumR),
                lumG + cosVal * (-lumG) + sinVal * (-lumG),
                lumB + cosVal * (-lumB) + sinVal * (1 - lumB), 0, 0,
                lumR + cosVal * (-lumR) + sinVal * (0.143f),
                lumG + cosVal * (1 - lumG) + sinVal * (0.140f),
                lumB + cosVal * (-lumB) + sinVal * (-0.283f), 0, 0,
                lumR + cosVal * (-lumR) + sinVal * (-(1 - lumR)),
                lumG + cosVal * (-lumG) + sinVal * (lumG),
                lumB + cosVal * (1 - lumB) + sinVal * (lumB), 0, 0,
                0, 0, 0, 1, 0,
                0, 0, 0, 0, 1};
           cm.set(mat);
        }
        return cm;
    }

    public static ColorMatrix adjustSaturation(int saturationVal) {
        float saturation = (float) saturationVal / 100;
        ColorMatrix cm = new ColorMatrix();
        cm.setSaturation(saturation);

        return cm;
    }

    public static ColorMatrix adjustBrightness(float brightnessVal) {
        float brightness = (float) brightnessVal / 100;
        ColorMatrix cm = new ColorMatrix();
        cm.setScale(brightness, brightness, brightness, 1);

        return cm;
    }

    public static ColorMatrix adjustContrast(float contrastVal) {
		contrastVal = contrastVal - 100;
        float contrast = (float) contrastVal / 100 + 1;
        float o = (-0.5f * contrast + 0.5f) * 255;
        float[] matrix = {
  	    	contrast, 0, 0, 0, o, //red
            0, contrast, 0, 0, o, //green
            0, 0, contrast, 0, o, //blue
            0, 0, 0, 1, 0 //alpha
        };

    return new ColorMatrix(matrix);
    }

    public static ColorMatrix adjustAlpha(float alphaVal) {
       float alpha = (float) alphaVal / 100;
       ColorMatrix cm = new ColorMatrix();
       cm.setScale(1, 1, 1, alpha);
 
       return cm;
    }

    // Icon mask related functions
	public static Bitmap applyXfermode(Bitmap bitmap, Mode mode) {
		Canvas canvas = new Canvas();
		canvas.setBitmap(bitmap);

		Paint paint = new Paint();
    	paint.setFilterBitmap(false);
		paint.setXfermode(new PorterDuffXfermode(mode));

		canvas.drawBitmap(bitmap, 0, 0, paint);
		paint.setXfermode(null);

		return bitmap;
	}

	public static Bitmap applyColorMask(Bitmap bitmap, Mode xfermode, int color) {
		Canvas canvas = new Canvas();
		canvas.setBitmap(bitmap);

		Paint paint = new Paint();
    	paint.setFilterBitmap(false);

		//Color bitmap
		paint.setColorFilter(null);
    	paint.setColorFilter(new PorterDuffColorFilter(color, xfermode));
		canvas.drawBitmap(bitmap, 0, 0, paint);

		return bitmap;
	}
}
