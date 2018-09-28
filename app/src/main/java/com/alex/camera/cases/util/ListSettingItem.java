package com.alex.camera.cases.util;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.alex.camera.R;

import java.util.List;

import androidx.core.content.ContextCompat;

/**
 * SettingItem its value is constrained by List class.
 */
public class ListSettingItem<K, V> extends SettingItem<K, V> {
    /**
     * A list of value can be used with SCaptureRequest.Key
     */
    final private List<V> mValueList;
    /**
     * A list of value that is supported,
     */
    final private List<V> mAvailableValueList;

    /**
     * Constructor for ListSettingItem
     *
     * @param name               String representation of this item.
     * @param key                Key of this item
     * @param valueNameList      String representation of value list
     * @param valueList          value list for this item
     * @param availableValueList value list that is available for this device
     */
    ListSettingItem(Activity activity, String name, K key, List<String> valueNameList, List<V> valueList, List<V> availableValueList) {
        super(activity, name, key);

        mValueList = valueList;
        mAvailableValueList = availableValueList;

        mView = mActivity.getLayoutInflater().inflate(R.layout.setting_list_item, null);

        ((TextView) mView.findViewById(R.id.title)).setText(name);

        Spinner spinner = mView.findViewById(R.id.itemlist);
        spinner.setAdapter(new ArrayAdapter<String>(mActivity,
                R.layout.spinner_item, valueNameList) {
            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                TextView view = (TextView) super.getDropDownView(position, convertView, parent);
                if (isEnabled(position))
                    view.setTextColor(ContextCompat.getColor(mActivity, android.R.color.primary_text_light));
                else
                    view.setTextColor(ContextCompat.getColor(mActivity, android.R.color.secondary_text_dark));

                return view;
            }

            @Override
            public boolean isEnabled(int position) {
                return mAvailableValueList.contains(mValueList.get(position));
            }
        });

        for (int index = 0; index < mValueList.size(); index++) {
            if (mAvailableValueList.contains(mValueList.get(index))) {
                spinner.setSelection(index);
                break;
            }
        }

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (mListener != null)
                    mListener.onSettingItemValueChanged(mKey, mValueList.get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    @Override
    public V getValue() {
        return mValueList.get(((Spinner) mView.findViewById(R.id.itemlist)).getSelectedItemPosition());
    }

    @Override
    public void setValue(V value) {
        if (mAvailableValueList.contains(value))
            ((Spinner) mView.findViewById(R.id.itemlist)).setSelection(mValueList.indexOf(value));
    }

    @Override
    public boolean isEnabled() {
        return mView.findViewById(R.id.itemlist).isEnabled();
    }

    @Override
    public void setEnabled(boolean enable) {
        mView.findViewById(R.id.itemlist).setEnabled(enable);
        if (enable && mListener != null)
            mListener.onSettingItemValueChanged(mKey, mValueList.get(((Spinner) mView.findViewById(R.id.itemlist)).getSelectedItemPosition()));
    }
}
