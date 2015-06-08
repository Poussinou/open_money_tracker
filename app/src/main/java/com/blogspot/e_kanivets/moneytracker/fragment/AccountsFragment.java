package com.blogspot.e_kanivets.moneytracker.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.blogspot.e_kanivets.moneytracker.R;
import com.blogspot.e_kanivets.moneytracker.activity.NavDrawerActivity;
import com.blogspot.e_kanivets.moneytracker.adapter.AccountAdapter;
import com.blogspot.e_kanivets.moneytracker.adapter.RecordAdapter;
import com.blogspot.e_kanivets.moneytracker.helper.MTHelper;

import java.util.Observable;
import java.util.Observer;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AccountsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AccountsFragment extends Fragment implements View.OnClickListener, Observer {
    public static final String TAG = "AccountsFragment";

    private ListView listView;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment AccountsFragment.
     */
    public static AccountsFragment newInstance() {
        AccountsFragment fragment = new AccountsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public AccountsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_accounts, container, false);
        initViews(rootView);
        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        initActionBar();

        ((NavDrawerActivity) activity).onSectionAttached(TAG);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_add_account:
                ((NavDrawerActivity) getActivity()).showAddAccountFragment();
                break;

            default:
                break;
        }
    }

    @Override
    public void update(Observable observable, Object data) {
        ((BaseAdapter) listView.getAdapter()).notifyDataSetChanged();
    }

    private void initViews(View rootView) {
        if (rootView != null) {
            listView = (ListView) rootView.findViewById(R.id.list_view);

            rootView.findViewById(R.id.btn_add_account).setOnClickListener(this);

            listView.setAdapter(new AccountAdapter(getActivity(), MTHelper.getInstance().getAccounts()));
            ((BaseAdapter) listView.getAdapter()).notifyDataSetChanged();

            //Subscribe to helper
            MTHelper.getInstance().addObserver(this);

            ((NavDrawerActivity) getActivity()).onSectionAttached(TAG);
        }
    }

    private void initActionBar() {
        ActionBar actionBar = ((ActionBarActivity) getActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setCustomView(null);
        }
    }
}