package com.hsns.laor.fragements;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hsns.laor.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class ForecastDetailActivityFragment extends Fragment {

    public ForecastDetailActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_forecast_detail, container, false);
    }
}
