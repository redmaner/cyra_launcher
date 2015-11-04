/*
 * Copyright (C) 2015, Redmaner
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
import android.content.res.TypedArray;
import android.os.Parcel;
import android.os.Parcelable;
import android.preference.Preference;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

public class SeekBarPreference extends Preference {

    private int mProgress;
    private int mMax;
	private int mMin;
	private int mNegative = 0;
	private String mSymbol;
	private String mProgressSummary;
    private boolean mTrackingTouch;

    public SeekBarPreference(
            Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        TypedArray a = context.obtainStyledAttributes(
                attrs, R.styleable.ProgressBar, defStyle, 0);
        setMax(a.getInt(R.styleable.ProgressBar_maximum, mMax));
		setMin(a.getInt(R.styleable.ProgressBar_minimum, mMin));
		mSymbol = a.getString(R.styleable.ProgressBar_symbol);
		mNegative = a.getInt(R.styleable.ProgressBar_negative, 0);
		mProgressSummary = a.getString(R.styleable.ProgressBar_summary);
        a.recycle();

        a = context.obtainStyledAttributes(attrs,
                R.styleable.SeekBarPreference, defStyle, 0);
        final int layoutResId = a.getResourceId(
                R.styleable.SeekBarPreference_layout,
                R.layout.preference_widget_seekbar);
        a.recycle();
		
        setLayoutResource(layoutResId);
    }

    public SeekBarPreference(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SeekBarPreference(Context context) {
        this(context, null);
    }

    @Override
    protected void onBindView(View view) {
        super.onBindView(view);
        SeekBar seekBar = (SeekBar) view.findViewById(
                R.id.seekbar);
		final TextView mSeekBarProgress = (TextView) view.findViewById(R.id.progress_summary);
        seekBar.setMax(mMax);
        seekBar.setProgress(mProgress);
        seekBar.setEnabled(isEnabled());

		mSeekBarProgress.setText(String.format(mProgressSummary, String.valueOf(mProgress - mNegative) + mSymbol));
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){ 

    		@Override
    		public void onProgressChanged(
            		SeekBar seekBar, int progress, boolean fromUser) {
        		if (fromUser && !mTrackingTouch) {
            		syncProgress(seekBar);
        		}
				if (progress < mMin) {
					progress = mMin;
				}
				mSeekBarProgress.setText(String.format(mProgressSummary, String.valueOf(progress - mNegative) + mSymbol));
    		}

    		@Override
    		public void onStartTrackingTouch(SeekBar seekBar) {
        		mTrackingTouch = true;
    		}

    		@Override
    		public void onStopTrackingTouch(SeekBar seekBar) {
        		mTrackingTouch = false;
        		if (seekBar.getProgress() != mProgress) {
            		syncProgress(seekBar);
        		}
    		}
		});
    }

    @Override
    public CharSequence getSummary() {
        return null;
    }

    @Override
    protected void onSetInitialValue(boolean restoreValue, Object defaultValue) {
        setProgress(restoreValue ? getPersistedInt(mProgress)
                : (Integer) defaultValue);
    }

    @Override
    protected Object onGetDefaultValue(TypedArray a, int index) {
        return a.getInt(index, 0);
    }

    public void setMax(int max) {
        if (max != mMax) {
            mMax = max;
            notifyChanged();
        }
    }

    public void setMin(int min) {
        if (min != mMin) {
            mMin = min;
            notifyChanged();
        }
    }

    public void setProgress(int progress) {
        setProgress(progress, true);
    }

    private void setProgress(int progress, boolean notifyChanged) {
        if (progress > mMax) {
            progress = mMax;
        }
        if (progress < mMin) {
            progress = mMin;
        }
        if (progress != mProgress) {
            mProgress = progress;
            persistInt(progress);
            if (notifyChanged) {
                notifyChanged();
            }
        }
    }

    public int getProgress() {
        return mProgress;
    }

    /**
     * Persist the seekBar's progress value if callChangeListener
     * returns true, otherwise set the seekBar's progress to the stored value
     */
    void syncProgress(SeekBar seekBar) {
        int progress = seekBar.getProgress();
        if (progress != mProgress) {
            if (callChangeListener(progress)) {
                setProgress(progress, false);
            } else {
                seekBar.setProgress(mProgress);
            }
        }
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        /*
         * Suppose a client uses this preference type without persisting. We
         * must save the instance state so it is able to, for example, survive
         * orientation changes.
         */

        final Parcelable superState = super.onSaveInstanceState();
        if (isPersistent()) {
            // No need to save instance state since it's persistent
            return superState;
        }

        // Save the instance state
        final SavedState myState = new SavedState(superState);
        myState.progress = mProgress;
        myState.max = mMax;
        return myState;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        if (!state.getClass().equals(SavedState.class)) {
            // Didn't save state for us in onSaveInstanceState
            super.onRestoreInstanceState(state);
            return;
        }

        // Restore the instance state
        SavedState myState = (SavedState) state;
        super.onRestoreInstanceState(myState.getSuperState());
        mProgress = myState.progress;
        mMax = myState.max;
        notifyChanged();
    }

    /**
     * SavedState, a subclass of {@link BaseSavedState}, will store the state
     * of MyPreference, a subclass of Preference.
     * <p>
     * It is important to always call through to super methods.
     */
    private static class SavedState extends BaseSavedState {
        int progress;
        int max;

        public SavedState(Parcel source) {
            super(source);

            // Restore the click counter
            progress = source.readInt();
            max = source.readInt();
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            super.writeToParcel(dest, flags);

            // Save the click counter
            dest.writeInt(progress);
            dest.writeInt(max);
        }

        public SavedState(Parcelable superState) {
            super(superState);
        }

        @SuppressWarnings("unused")
        public static final Parcelable.Creator<SavedState> CREATOR =
                new Parcelable.Creator<SavedState>() {
            public SavedState createFromParcel(Parcel in) {
                return new SavedState(in);
            }

            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        };
    }
}

