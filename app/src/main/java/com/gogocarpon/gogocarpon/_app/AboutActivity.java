package com.gogocarpon.gogocarpon._app;

import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;

import com.gogocarpon.gogocarpon.R;
import com.gogocarpon.gogocarpon._app.baseclass.AppConfig;
import com.gogocarpon.gogocarpon._app.baseclass.BaseActivity;
import com.gogocarpon.gogocarpon._app.baseclass.IntentManager;
import com.mikepenz.aboutlibraries.Libs;
import com.mikepenz.aboutlibraries.ui.LibsFragment;

/**
 * Created by David Mulder on 4/14/2015.
 */
public class AboutActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.act_about);
        LibsFragment fragment = new Libs.Builder()
                .withFields(R.string.class.getFields())
                .withLibraries("crouton", "activeandroid", "actionbarsherlock", "showcaseview")
                .withVersionShown(true)
                .withLicenseShown(true)
                .withLibraryModification("aboutlibraries", Libs.LibraryFields.LIBRARY_NAME, "_AboutLibraries")
                .fragment();

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(android.R.id.content, fragment).commit();

        Drawable colorDrawable = new ColorDrawable(getResources().getColor(R.color.blue_light));
        Drawable bottomDrawable = new ColorDrawable(getResources().getColor(android.R.color.transparent));
        LayerDrawable ld = new LayerDrawable(new Drawable[]{colorDrawable, bottomDrawable});
        getSupportActionBar().setBackgroundDrawable(ld);
        AppConfig.isAbout = true;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    //hanh edit actionbar login
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        super.initActionBarControl(menu);
        ActionBar ab = getSupportActionBar();

        ab.setHomeButtonEnabled(true);
        ab.setDisplayHomeAsUpEnabled(true); // Hien thi bieu tuong nut back
        getSupportActionBar().setIcon(R.drawable.emu_category);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void invalidateOptionsMenu() {
        // TODO Auto-generated method stub
        super.invalidateOptionsMenu();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            // Overwrite button home function
            //menuCats.toggle();
            AppConfig.isAbout = false;
            finish();

            return true;
        } else if (item.getItemId() == MNI_USER_ABOUT) {
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {

        Log.i("LOG APP", "****** onStart : HomeActivity");
        super.onStart();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if (keyCode == KeyEvent.KEYCODE_BACK)
        {
            AppConfig.isAbout = false;
            finish();
            // perform your actons.
        }
        return false;
    }
}
