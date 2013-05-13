package com.example.dbproject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import org.andengine.audio.music.Music;
import org.andengine.audio.music.MusicFactory;
import org.andengine.audio.sound.Sound;
import org.andengine.audio.sound.SoundFactory;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.WakeLockOptions;
import org.andengine.engine.options.resolutionpolicy.FillResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.ButtonSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.sprite.ButtonSprite.OnClickListener;
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.BuildableBitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource;
import org.andengine.opengl.texture.atlas.buildable.builder.BlackPawnTextureAtlasBuilder;
import org.andengine.opengl.texture.atlas.buildable.builder.ITextureAtlasBuilder.TextureAtlasBuilderException;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.ui.activity.BaseGameActivity;
import org.andengine.util.HorizontalAlign;
import org.andengine.util.debug.Debug;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class DBViewActivity extends BaseGameActivity implements OnClickListener {

	//target width
	private static final int WIDTH = 800;
	//target height
	private static final int HEIGHT = 480;
	//the camera
	private Camera mCamera;
	// the inventory scene
	private Scene invScene;
	private Scene Scene;
	// a sound
	private Sound Rsound;
	// bg image for the initial view
	private ITextureRegion BGTextureRegion;
	private ITextureRegion mRUTextureRegion;
	private ITextureRegion mRDTextureRegion;
	private BuildableBitmapTextureAtlas mBitmapTextureAtlas;
	// declare a background image sprite
	private Sprite BGsprite;
	private Sprite RButton;
	
	private Music music2;

/*
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dbview);
		// Show the Up button in the action bar.
		setupActionBar();
	}
	*/
	private String weapon[];
	private String armor[];
	private Text wText[];
	private Text aText[];
	private myDatabase myDatabase2;
	private myDatabase myDatabase3;
	private Font iFont;
	public int n;
	
	public int weapCount;
	public int armorCount;
	private void weapons() {
		myDatabase2 = new myDatabase(this);
		weapCount = myDatabase2.getWeapCount();
		weapon = new String[100];
		for(int i = 0; i < weapCount; i++)
		{
			String myReturn = myDatabase2.returnWeapMat(i) +" "+myDatabase2.returnWeapName(i);
			weapon[i] = myReturn;
		}
	}
	
	private void armor() {
		myDatabase3 = new myDatabase(this);
		armorCount = myDatabase3.getArmorCount();
		armor = new String[100];
		for(int i = 0; i < armorCount; i++)
		{
			String myReturn = myDatabase3.returnArmorMat(i) +" "+myDatabase3.returnArmorName(i);
			armor[i] = myReturn;
		}
	}

	
	@Override
	public EngineOptions onCreateEngineOptions() {
		//set up the camera
		mCamera = new Camera(0, 0, WIDTH, HEIGHT);
		//set the engine options
		EngineOptions engineOptions = new EngineOptions(true, ScreenOrientation.LANDSCAPE_SENSOR, new FillResolutionPolicy(), mCamera);
		//keep the screen on
		engineOptions.setWakeLockOptions(WakeLockOptions.SCREEN_ON);
		//allow music
		engineOptions.getAudioOptions().setNeedsMusic(true);
		//allow sound
		engineOptions.getAudioOptions().setNeedsSound(true);
		//done setting the engine options
		return engineOptions;
	}


	@Override
	public void onCreateResources(
			OnCreateResourcesCallback pOnCreateResourcesCallback)
			throws Exception {
		
		//set path to look for textures
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		//set sound path
		SoundFactory.setAssetBasePath("sfx/");
		//set music path
		MusicFactory.setAssetBasePath("sfx/");

		try {
			Rsound = SoundFactory.createSoundFromAsset(getSoundManager(), this,
					"Rsound.mp3");
			music2 = MusicFactory.createMusicFromAsset(getMusicManager(), this,
					"music2.mp3");
		} catch (IOException e) {
			e.printStackTrace();
		}
		mBitmapTextureAtlas = new BuildableBitmapTextureAtlas(
				mEngine.getTextureManager(), 1024, 1024);
		BGTextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(mBitmapTextureAtlas, this, "BG1.png");
		mRDTextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(this.mBitmapTextureAtlas, this,

				"RDButton.png");
		mRUTextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(this.mBitmapTextureAtlas, this,

				"RUButton.png");
		try {
			this.mBitmapTextureAtlas
					.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource,

					BitmapTextureAtlas>(0, 0, 0));
			this.mBitmapTextureAtlas.load();
		} catch (TextureAtlasBuilderException e) {
			Debug.e(e);
		}
		
		// set up the font
				iFont = FontFactory.create(mEngine.getFontManager(),
						mEngine.getTextureManager(), 256, 256,
						Typeface.create(Typeface.DEFAULT, Typeface.NORMAL), 32f, true,
						org.andengine.util.color.Color.WHITE_ABGR_PACKED_INT);
				// load that font
				iFont.load();
				// andengine is a little wonky, this is necessary to prevent anything
				// weird from happening when writing text
				iFont.prepareLetters("abcdefghijklmnopqrstuvwxyz0123456789"
						.toCharArray());

		// and done loading resources
				n = 0;
				weapons();
				armor();
		pOnCreateResourcesCallback.onCreateResourcesFinished();
	}
	
	@Override
	public synchronized void onResumeGame() {
		// if there is a song and it's not playing
		if (music2 != null && !music2.isPlaying()) {// play that music
			music2.play();
		}
		// this is becomes part of the super function
		super.onResumeGame();
	}

	@Override
	public synchronized void onPauseGame() {
		// if there's a song and it's playing
		if (music2 != null && music2.isPlaying()) {// pause it
			music2.pause();
		}
		// this is becomes part of the super function
		super.onPauseGame();
	}
	
	@Override
	public void onCreateScene(OnCreateSceneCallback pOnCreateSceneCallback)
			throws Exception {

		Scene = createInvScene();

		// finished creating scene
		pOnCreateSceneCallback.onCreateSceneFinished(Scene);
	}

	protected Scene createInvScene() {
		invScene = new Scene();
		BGsprite = new Sprite(0, 0, BGTextureRegion,
				getVertexBufferObjectManager());
		RButton = new ButtonSprite(700, 400, mRUTextureRegion,
				mRDTextureRegion, getVertexBufferObjectManager(), this);
		invScene.attachChild(BGsprite);
		invScene.registerTouchArea(RButton);
		invScene.attachChild(RButton);
		wText = new Text[50];
		aText = new Text[50];
		for(int i = 0; i < weapCount; i++)
		{
			wText[i] = new Text(15, (i*30)+50, this.iFont, weapon[i], new TextOptions(HorizontalAlign.LEFT), this.getVertexBufferObjectManager());
			invScene.attachChild(wText[n]);
			n++;
		}
		for(int i = 0; i < armorCount; i++)
		{
			aText[i] = new Text(400, (i*30)+50, this.iFont, armor[i], new TextOptions(HorizontalAlign.LEFT), this.getVertexBufferObjectManager());
			invScene.attachChild(aText[i]);
			n++;
		}
		invScene.setTouchAreaBindingOnActionDownEnabled(true);
		return invScene;
	}

	@Override
	public void onClick(final ButtonSprite pButtonSprite,
			final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {

				if (pButtonSprite == RButton) {
					Rsound.play();
					finish();
				}
			}
		});
	}

	@Override
	public void onPopulateScene(Scene pScene,
			OnPopulateSceneCallback pOnPopulateSceneCallback) throws Exception {

		pOnPopulateSceneCallback.onPopulateSceneFinished();
	}

}
