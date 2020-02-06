package com.coronationmb.activityModule.activityModule.ui.referFriend;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.core.app.ShareCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.coronationmb.R;
import com.coronationmb.activityModule.activityModule.DashboardActivity;
import com.coronationmb.activityModule.activityModule.ui.helpDesk.HelpDeskViewModel;
import com.coronationmb.service.GlobalRepository;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class ReferFriendFragment extends Fragment {


    @BindView(R.id.send)
    LinearLayout send;
    GlobalRepository repo;
    private ProgressDialog progress;
    //Context context;


    private ReferFriendViewModel referFriendViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        referFriendViewModel = ViewModelProviders.of(this).get(ReferFriendViewModel.class);
        View root = inflater.inflate(R.layout.fragment_refer_friend, container, false);
        ButterKnife.bind(this, root);
        ((DashboardActivity)getContext()).changeToolbarTitle("REFER A FRIEND");
        ((DashboardActivity)getContext()).changeHamburgerIconClor();

        return root;
    }

    @OnClick(R.id.send)
    public void send(){

     //   Intent sendIntent = new Intent();
       // sendIntent.setAction(Intent.ACTION_SEND);
      //  sendIntent.putExtra(Intent.EXTRA_TEXT, "This is my text to send.");
      //  sendIntent.setType("text/plain");
      //  startActivity(sendIntent);

        ShareCompat.IntentBuilder

                .from(getActivity())
               // .setText("Check out a new app: https://play.google.com/store/apps/details?id=vatebra.com.vateepversion2")
                .setText("Check out a new app: https://play.google.com/store/apps/details?id=com.instagram.android")
                .setType("text/plain")
                .setChooserTitle("Share with your friends")
                .startChooser();


    }
}