package com.chanakyabhardwaj.bdgt;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;


/*
* TODO
* color generation logic for categories
*
* reminder notifciations
* settings panel for category updates
*/

public class MainActivity extends AppCompatActivity {
    public static int activeExpenseId = -1;
    private CoordinatorLayout mainLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ExpenseCategory.init();
        mainLayout = (CoordinatorLayout) findViewById(R.id.mainLayout);

        if (findViewById(R.id.fragment_container) != null) {
            if (savedInstanceState != null) {
                return;
            }

            ExpenseListFragment expenseListFragment = new ExpenseListFragment();

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, expenseListFragment).commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.menu_settings) {
            Snackbar snackbar = Snackbar.make(mainLayout, "Settings Clicked", Snackbar.LENGTH_LONG);
            snackbar.show();
            return true;
        }

        if (id == R.id.menu_remind) {
            Snackbar snackbar = Snackbar.make(mainLayout, "Remind Clicked", Snackbar.LENGTH_LONG);
            snackbar.show();
            scheduleNotification(getNotification(), 5000);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private Notification getNotification() {
        Notification.Builder builder = new Notification.Builder(this);
        builder.setContentTitle("Hey there!");
        builder.setContentText("Don't forget to add your expenses!");
        builder.setSmallIcon(R.drawable.ic_launcher);
        return builder.build();
    }

    private void scheduleNotification(Notification notification, int delay) {
        Intent notificationIntent = new Intent(this, NotificationPublisher.class);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION_ID, 1);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION, notification);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        long futureInMillis = SystemClock.elapsedRealtime() + delay;
        AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, futureInMillis, pendingIntent);
    }
}
