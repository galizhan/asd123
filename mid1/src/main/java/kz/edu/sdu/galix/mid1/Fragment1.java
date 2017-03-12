package kz.edu.sdu.galix.mid1;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;


/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment1 extends Fragment implements View.OnClickListener{

    Button btn;
    public Fragment1() {
        // Required empty public constructor
    }
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        view = inflater.inflate(R.layout.fragment_1, container, false);
        btn = (Button)view.findViewById(R.id.frag1_btn1);
        btn.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        MainActivity mainActivity = (MainActivity)getActivity();
        mainActivity.loadFragment2();
    }

}
