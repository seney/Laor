package com.hsns.laor.fragements;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hsns.laor.models.Car;
import com.hsns.laor.R;
import com.hsns.laor.models.User;
import com.hsns.laor.databinding.FragmentOneBinding;

/**
 * Created by seney on 12/14/15.
 */
public class OneFragment extends Fragment {

    FragmentOneBinding binding;

    public OneFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_one, container, false);
        binding = DataBindingUtil.bind(v);

        User user = new User("Seney", "Sean");
        Car car = new Car("LN", "PP_008");
        binding.setCar(car);
        binding.setUser(user);

        return binding.getRoot();
    }

    public void bindData(User user) {
        binding.setUser(user);
    }
}
