package com.example.onlinewolf.onlinewolf.app;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A placeholder fragment containing a simple view.
 */

public class MainActivityFragment extends Fragment {



    public MainActivityFragment() {

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
      //  String[] menu_string ={"Play","Score","Help","Exit"};
      //  ArrayList<String> menu_list = new ArrayList<String>(Arrays.asList(menu_string));
     //   ArrayAdapter<String> MenuAdapter =  new ArrayAdapter<String>(getActivity(),R.layout.menu_text,menu_list);

        //ListView menu = (ListView)
        return inflater.inflate(R.layout.fragment_main, container, false);
    }
}
