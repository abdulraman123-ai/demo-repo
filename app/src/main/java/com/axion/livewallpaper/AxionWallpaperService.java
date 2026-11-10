package com.axion.livewallpaper;
import android.service.wallpaper.WallpaperService;
import android.media.MediaPlayer;
import android.view.SurfaceHolder;
public final class AxionWallpaperService extends WallpaperService {
    @Override public Engine onCreateEngine() { return new VideoWallpaperEngine(); }
    private final class VideoWallpaperEngine extends Engine {
        private MediaPlayer player; private boolean visible; private boolean surfaceReady;
        VideoWallpaperEngine() { setTouchEventsEnabled(false); }
        @Override public void onSurfaceCreated(SurfaceHolder holder) { super.onSurfaceCreated(holder); surfaceReady=true; startVideoIfReady(); }
        @Override public void onSurfaceChanged(SurfaceHolder holder,int format,int width,int height) {
            super.onSurfaceChanged(holder,format,width,height); if(player!=null) releasePlayer(); surfaceReady=true; startVideoIfReady();
        }
        @Override public void onSurfaceDestroyed(SurfaceHolder holder) { surfaceReady=false; releasePlayer(); super.onSurfaceDestroyed(holder); }
        @Override public void onVisibilityChanged(boolean isVisible) { visible=isVisible; if(isVisible) startVideoIfReady(); else releasePlayer(); }
        @Override public void onDestroy() { releasePlayer(); super.onDestroy(); }
        private void startVideoIfReady() {
            if(!visible || !surfaceReady || player!=null) return;
            SurfaceHolder holder=getSurfaceHolder();
            if(holder==null || holder.getSurface()==null || !holder.getSurface().isValid()) return;
            try {
                MediaPlayer mp=MediaPlayer.create(AxionWallpaperService.this,R.raw.axion_wallpaper);
                if(mp==null) return;
                mp.setSurface(holder.getSurface()); mp.setLooping(true); mp.setVolume(0f,0f);
                mp.setVideoScalingMode(MediaPlayer.VIDEO_SCALING_MODE_SCALE_TO_FIT_WITH_CROPPING);
                player=mp; mp.setOnErrorListener((p,what,extra)->{releasePlayer();return true;}); mp.start();
            } catch(RuntimeException error) { releasePlayer(); }
        }
        private void releasePlayer() {
            if(player==null) return;
            try { if(player.isPlaying()) player.stop(); } catch(RuntimeException ignored) {}
            try { player.reset(); } catch(RuntimeException ignored) {}
            player.release(); player=null;
        }
    }
}
