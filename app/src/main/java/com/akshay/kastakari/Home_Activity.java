package com.akshay.kastakari;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuAdapter;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.view.ViewCompat;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import java.util.ArrayList;
import java.util.Arrays;
import de.hdodenhof.circleimageview.CircleImageView;
import nl.psdcompany.duonavigationdrawer.views.DuoDrawerLayout;
import nl.psdcompany.duonavigationdrawer.views.DuoMenuView;
import nl.psdcompany.duonavigationdrawer.widgets.DuoDrawerToggle;

public class Home_Activity extends AppCompatActivity implements DuoMenuView.OnMenuClickListener {

    private FirebaseAuth mAuth;
    private Toolbar toolbar;
    private menuadapter mMenuAdapter;
    private ViewHolder mViewHolder;
    CircleImageView header;

    DatabaseReference mDatabase , mref , msubref;
    ProgressDialog pd;
    private ArrayList<String> mTitles = new ArrayList<>();
    TextView name , mail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        toolbar=(Toolbar)findViewById(R.id.toolbar) ;
        mAuth= FirebaseAuth.getInstance();

        name = findViewById(R.id.header_name);
        mail = findViewById(R.id.header_mail);

        pd = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mref = mDatabase.child("Users").child(mAuth.getCurrentUser().getUid());
        mTitles = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.menuOptions)));
        header = findViewById(R.id.image_header);

        // Initialize the views
        mViewHolder = new ViewHolder();
        // Handle toolbar actions
        handleToolbar();

        // Handle menu actions
        handleMenu();

        // Handle drawer actions
        handleDrawer();
mMenuAdapter.setViewSelected(0,true);

        setTitle(mTitles.get(0));

    }
    private void handleToolbar()
    {
        setSupportActionBar(mViewHolder.mToolbar);
    }

    private void handleDrawer()
    {
        DuoDrawerToggle duoDrawerToggle = new DuoDrawerToggle(this , mViewHolder.mDuoDrawerLayout , mViewHolder.mToolbar , R.string.navigation_drawer_open , R.string.navigation_drawer_close);

        mViewHolder.mDuoDrawerLayout.setDrawerListener(duoDrawerToggle);
        duoDrawerToggle.syncState();

    }

    private void handleMenu()
    {
        mMenuAdapter = new menuadapter(mTitles);

        mViewHolder.mDuoMenuView.setOnMenuClickListener(this);
        mViewHolder.mDuoMenuView.setAdapter(mMenuAdapter);
    }
    @Override
    public void onFooterClicked()
    {
        pd.setTitle("Logging out");
        pd.show();
        mAuth.signOut();
        startActivity(new Intent(Home_Activity.this , MainActivity.class));
        finish();
    }
    @Override
    public void onHeaderClicked()
    {
        Intent i = new Intent(this , Home_Activity.class);
        ActivityOptionsCompat actop = ActivityOptionsCompat.makeSceneTransitionAnimation(this , header , ViewCompat.getTransitionName(header));
        startActivity(i , actop.toBundle());
        overridePendingTransition(R.anim.fadein , R.anim.fadeout);
    }
    @Override
    public void onOptionClicked(int position , Object objectClicked)
    {
        switch(position)
        {
            case 2:
                startActivity(new Intent(Home_Activity.this , About_me.class));
                break;
            case 1:
                Intent i = new Intent(this , Profile.class);
                ActivityOptionsCompat actop = ActivityOptionsCompat.makeSceneTransitionAnimation(this , header , ViewCompat.getTransitionName(header));
                startActivity(i , actop.toBundle());
                overridePendingTransition(R.anim.fadein , R.anim.fadeout);
                break;
            default:
                break;
        }

        // Close the drawer
        mViewHolder.mDuoDrawerLayout.closeDrawer();
    }
    private class ViewHolder
    {
        private DuoDrawerLayout mDuoDrawerLayout;
        private DuoMenuView mDuoMenuView;
        private Toolbar mToolbar;

        ViewHolder()
        {
            mDuoDrawerLayout = findViewById(R.id.drawer);
            mDuoMenuView = (DuoMenuView)mDuoDrawerLayout.getMenuView();
            mToolbar = findViewById(R.id.toolbar);
        }
    }


}