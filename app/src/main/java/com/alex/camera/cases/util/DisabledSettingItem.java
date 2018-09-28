package com.alex.camera.cases.util;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import com.alex.camera.R;

import androidx.core.content.ContextCompat;

/**
 * Dummy Setting Item for setting feature that is not supported by the device.
 * This item only show the gary outed text label.
 */
public class DisabledSettingItem extends SettingItem<Object, Object> {
    public DisabledSettingItem(Activity activity, String name) {
        super(activity, name, null);
        mView = mActivity.getLayoutInflater().inflate(R.layout.setting_list_item, null);
        ((TextView) mView.findViewById(R.id.title)).setText(name);
        ((TextView) mView.findViewById(R.id.title)).setTextColor(ContextCompat.getColor(mActivity, android.R.color.secondary_text_dark));
        mView.findViewById(R.id.itemlist).setVisibility(View.GONE);
    }

    @Override
    public Object getValue() {
        return null;
    }

    @Override
    public void setValue(Object value) {
    }

    @Override
    public boolean isEnabled() {
        return false;
    }

    @Override
    public void setEnabled(boolean enable) {
    }
}
