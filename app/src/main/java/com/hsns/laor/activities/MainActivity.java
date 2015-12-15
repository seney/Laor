package com.hsns.laor.activities;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.NotificationCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.hsns.laor.R;
import com.hsns.laor.models.User;
import com.hsns.laor.adapters.MainViewPagerAdapter;
import com.hsns.laor.fragements.OneFragment;
import com.hsns.laor.fragements.ThreeFragment;
import com.hsns.laor.fragements.TwoFragment;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, ViewPager.OnPageChangeListener {

    private static final int REQUEST_CAMERA = 1;
    private final int NOTIFICATION_ID = 9999;
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private int[] tabIcons = {
            R.drawable.ic_home_white_24px,
            R.drawable.ic_stars_black_24px,
            R.drawable.ic_favorite_black_24px
    };
    private Context context;
    private OneFragment oneFragment;
    private ArrayList<User> users = new ArrayList<>();
    private int counter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        context = getApplicationContext();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //sendNotification();

                if(counter == users.size())
                    counter = 0;
                oneFragment.bindData(users.get(counter));
                counter ++;
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);
        viewPager.addOnPageChangeListener(this);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        setupTabIcons();
        setUpUsers();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void setupViewPager(ViewPager viewPager) {
        MainViewPagerAdapter adapter = new MainViewPagerAdapter(getSupportFragmentManager());
        oneFragment = new OneFragment();
        adapter.addFragment(oneFragment, "ONE");
        adapter.addFragment(new TwoFragment(), "TWO");
        adapter.addFragment(new ThreeFragment(), "THREE");
//        adapter.addFragment(new FourFragment(), "FOUR");
//        adapter.addFragment(new FiveFragment(), "FIVE");
//        adapter.addFragment(new SixFragment(), "SIX");
        viewPager.setAdapter(adapter);
    }

    private void setupTabIcons() {
        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
        tabLayout.getTabAt(2).setIcon(tabIcons[2]);
    }

    private void switchTabIcon(int position) {
        switch (position) {
            case 0:
                tabLayout.getTabAt(0).setIcon(R.drawable.ic_home_white_24px);
                tabLayout.getTabAt(1).setIcon(R.drawable.ic_stars_black_24px);
                tabLayout.getTabAt(2).setIcon(R.drawable.ic_favorite_black_24px);
                break;
            case 1:
                tabLayout.getTabAt(0).setIcon(R.drawable.ic_home_black_24px);
                tabLayout.getTabAt(1).setIcon(R.drawable.ic_stars_white_24px);
                tabLayout.getTabAt(2).setIcon(R.drawable.ic_favorite_black_24px);
                break;
            case 2:
                tabLayout.getTabAt(0).setIcon(R.drawable.ic_home_black_24px);
                tabLayout.getTabAt(1).setIcon(R.drawable.ic_stars_black_24px);
                tabLayout.getTabAt(2).setIcon(R.drawable.ic_favorite_white_24px);
                break;
        }

    }

    private void setUpUsers() {
        users.add(new User("Data", "Kiki"));
        users.add(new User("Kaka", "Kiki"));
        users.add(new User("KoKo", "Kiki"));
        users.add(new User("KeKe", "Kiki"));
    }

    private
    @CheckResult
    String toTrim(String s) {
        return s.trim();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        switchTabIcon(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    public String trim(@NonNull String s) {
        return s.trim();
    }

    /**
     * Send simple notification using the NotificationCompat API.
     */
    public void sendNotification() {

        // Use NotificationCompat.Builder to set up our notification.
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);

        //icon appears in device notification bar and right hand corner of notification
        builder.setSmallIcon(R.drawable.ic_stars_white_24px );

        // This intent is fired when notification is clicked
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://javatechig.com/"));
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        // Set the intent that will fire when the user taps the notification.
        //builder.setContentIntent(pendingIntent);
        builder.addAction(new NotificationCompat.Action(R.drawable.ic_favorite_black_24px, "Like", pendingIntent));

        // Large icon appears on the left of the notification
        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher));

        // Content title, which appears in large type at the top of the notification
        builder.setContentTitle("Notifications Title");

        // Content text, which appears in smaller text below the title
        builder.setContentText("Your notification content here.");

        // The subtext, which appears under the text on newer devices.
        // This will show-up in the devices with Android 4.2 and above only
        //builder.setSubText("Tap to view documentation about notifications.");

        builder.setStyle(new NotificationCompat.BigTextStyle().bigText("Tap to view documentation about notifications. Tap to view documentation about notifications."));

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        // Will display the notification in the notification bar
        notificationManager.notify(NOTIFICATION_ID, builder.build());
    }
}