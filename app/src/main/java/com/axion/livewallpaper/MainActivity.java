package com.axion.livewallpaper;
import android.app.Activity;
import android.app.WallpaperManager;
import android.content.ComponentName;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
public final class MainActivity extends Activity {
    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LinearLayout page=new LinearLayout(this); page.setOrientation(LinearLayout.VERTICAL); page.setGravity(Gravity.CENTER);
        page.setPadding(dp(28),dp(32),dp(28),dp(32)); page.setBackgroundColor(Color.rgb(8,11,24));
        TextView title=new TextView(this); title.setText("AXION\nMOTION WALLPAPER"); title.setTextColor(Color.WHITE);
        title.setTextSize(28); title.setGravity(Gravity.CENTER); title.setTypeface(null,android.graphics.Typeface.BOLD);
        page.addView(title,new LinearLayout.LayoutParams(-1,-2));
        TextView detail=new TextView(this); detail.setText("A moving wallpaper that loops continuously.\n\nTap below, preview it, then choose Set wallpaper.");
        detail.setTextColor(Color.LTGRAY); detail.setTextSize(16); detail.setGravity(Gravity.CENTER);
        LinearLayout.LayoutParams dp=new LinearLayout.LayoutParams(-1,-2); dp.setMargins(0,dp(22),0,dp(28)); page.addView(detail,dp);
        Button set=new Button(this); set.setText("Preview and set wallpaper"); set.setAllCaps(false); set.setOnClickListener(v->openWallpaperPicker());
        page.addView(set,new LinearLayout.LayoutParams(-1,-2));
        TextView note=new TextView(this); note.setText("Choose Home screen, or Home screen and Lock screen if supported.");
        note.setTextColor(Color.GRAY); note.setTextSize(13); note.setGravity(Gravity.CENTER);
        LinearLayout.LayoutParams np=new LinearLayout.LayoutParams(-1,-2); np.setMargins(0,dp(22),0,0); page.addView(note,np);
        setContentView(page);
    }
    private void openWallpaperPicker() {
        Intent i=new Intent(WallpaperManager.ACTION_CHANGE_LIVE_WALLPAPER);
        i.putExtra(WallpaperManager.EXTRA_LIVE_WALLPAPER_COMPONENT,new ComponentName(this,AxionWallpaperService.class));
        try { startActivity(i); } catch(Exception e) { Toast.makeText(this,"Could not open wallpaper picker on this device.",Toast.LENGTH_LONG).show(); }
    }
    private int dp(int v) { return Math.round(v*getResources().getDisplayMetrics().density); }
}
