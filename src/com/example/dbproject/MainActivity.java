package com.example.dbproject;

import java.io.IOException;
import java.util.Random;

import org.andengine.audio.music.Music;
import org.andengine.audio.music.MusicFactory;
import org.andengine.audio.sound.Sound;
import org.andengine.audio.sound.SoundFactory;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.WakeLockOptions;
import org.andengine.engine.options.resolutionpolicy.FillResolutionPolicy;
import org.andengine.entity.modifier.AlphaModifier;
import org.andengine.entity.modifier.ParallelEntityModifier;
import org.andengine.entity.modifier.ScaleModifier;
import org.andengine.entity.modifier.SequenceEntityModifier;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.ButtonSprite;
import org.andengine.entity.sprite.ButtonSprite.OnClickListener;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TickerText;
import org.andengine.entity.text.TickerText.TickerTextOptions;
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

import android.content.Intent;
import android.graphics.Typeface;
import android.widget.Toast;

public class MainActivity extends BaseGameActivity implements OnClickListener {
	// target width
	private static final int WIDTH = 800;
	// target height
	private static final int HEIGHT = 480;
	// the camera
	private Camera mCamera;
	// the main scene
	private Scene mScene;
	private Scene Scene;
	// a sound
	private Sound VIsound;
	private Sound AIsound;
	private Sound EIsound;
	// the bg music
	private Music mMusic;
	// bg image for the initial view
	private ITextureRegion mBGTextureRegion;
	// add item buttons
	private ITextureRegion mAIUTextureRegion;
	private ITextureRegion mAIDTextureRegion;
	// view inventory buttons
	private ITextureRegion mVIUTextureRegion;
	private ITextureRegion mVIDTextureRegion;
	// empty inventory buttons
	private ITextureRegion mEIUTextureRegion;
	private ITextureRegion mEIDTextureRegion;
	//exit buttonz
	private ITextureRegion EUTextureRegion;
	private ITextureRegion EDTextureRegion;	
	// the font
	private Font mFont;
	// the welcome text
	private Text mText;
	// needed for our textures
	private BuildableBitmapTextureAtlas mBitmapTextureAtlas;
	// declare a background image sprite
	private Sprite oBGsprite;
	private Sprite VIButton;
	private Sprite AIButton;
	private Sprite EIButton;
	private Sprite EButton;

	private String test;
	private int wNumber;
	private int aNumber;
	private String wName;
	private String wMat;
	public myDatabase myDatabase;
	
	private Random ranGen = new Random();
	private int randomInt;
	
	private String aMat;
	private String aName;

	@Override
	public EngineOptions onCreateEngineOptions() {
		// set up the camera
		mCamera = new Camera(0, 0, WIDTH, HEIGHT);
		// set the engine options
		EngineOptions engineOptions = new EngineOptions(true,
				ScreenOrientation.LANDSCAPE_SENSOR, new FillResolutionPolicy(),
				mCamera);
		// keep the screen on
		engineOptions.setWakeLockOptions(WakeLockOptions.SCREEN_ON);
		// allow music
		engineOptions.getAudioOptions().setNeedsMusic(true);
		// allow sound
		engineOptions.getAudioOptions().setNeedsSound(true);
		// done setting the engine options
		return engineOptions;
	}

	@Override
	public void onCreateResources(
			OnCreateResourcesCallback pOnCreateResourcesCallback)
			throws Exception {
		// create the DB & the weapons table of the database (if it does not
		// already exist)
		//weapons();
		wNumber = 0;
		aNumber = 0;
		// set path to look for textures
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		// set sound path
		SoundFactory.setAssetBasePath("sfx/");
		// set music path
		MusicFactory.setAssetBasePath("sfx/");
		// load sound file into sound variable
		try {
			AIsound = SoundFactory.createSoundFromAsset(getSoundManager(),
					this, "AIsound.mp3");
			VIsound = SoundFactory.createSoundFromAsset(getSoundManager(),
					this, "VIsound.mp3");
			EIsound = SoundFactory.createSoundFromAsset(getSoundManager(),
					this, "EIsound.mp3");
		} catch (IOException e) {
			e.printStackTrace();
		}
		// load music file into music variable
		try {
			mMusic = MusicFactory.createMusicFromAsset(getMusicManager(), this,
					"music.mp3");
		} catch (IOException e) {
			e.printStackTrace();
		}
		mBitmapTextureAtlas = new BuildableBitmapTextureAtlas(
				mEngine.getTextureManager(), 1024, 1024);
		mBGTextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(mBitmapTextureAtlas, this, "BG0.png");
		mAIDTextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(this.mBitmapTextureAtlas, this,
						"AIDButton.png");
		mVIDTextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(this.mBitmapTextureAtlas, this,
						"VIDButton.png");
		mEIDTextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(this.mBitmapTextureAtlas, this,
						"EIDButton.png");
		EDTextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(this.mBitmapTextureAtlas, this,
						"EDButton.png");
		EUTextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(this.mBitmapTextureAtlas, this,
						"EUButton.png");
		mAIUTextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(this.mBitmapTextureAtlas, this,
						"AIUButton.png");
		mVIUTextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(this.mBitmapTextureAtlas, this,
						"VIUButton.png");
		mEIUTextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(this.mBitmapTextureAtlas, this,
						"EIUButton.png");
		try {
			this.mBitmapTextureAtlas
					.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(
							0, 0, 0));
			this.mBitmapTextureAtlas.load();
		} catch (TextureAtlasBuilderException e) {
			Debug.e(e);
		}

		// set up the font
		mFont = FontFactory.create(mEngine.getFontManager(),
				mEngine.getTextureManager(), 256, 256,
				Typeface.create(Typeface.DEFAULT, Typeface.NORMAL), 32f, true,
				org.andengine.util.color.Color.WHITE_ABGR_PACKED_INT);
		// load that font
		mFont.load();
		// andengine is a little wonky, this is necessary to prevent anything
		// weird from happening when writing text
		mFont.prepareLetters("abcdefghijklmnopqrstuvwxyz0123456789"
				.toCharArray());
		// and done loading resources

		if (myDatabase == null) {
			myDatabase = new myDatabase(this);
		}
		
		pOnCreateResourcesCallback.onCreateResourcesFinished();
	}

	@Override
	public synchronized void onResumeGame() {
		// if there is a song and it's not playing
		if (mMusic != null && !mMusic.isPlaying()) {// play that music
			mMusic.play();
		}
		// this is becomes part of the super function
		super.onResumeGame();
	}

	@Override
	public synchronized void onPauseGame() {
		// if there's a song and it's playing
		if (mMusic != null && mMusic.isPlaying()) {// pause it
			mMusic.pause();
		}
		// this is becomes part of the super function
		super.onPauseGame();
	}

	@Override
	public void onCreateScene(OnCreateSceneCallback pOnCreateSceneCallback)
			throws Exception {

		Scene = createMScene();

		// finished creating scene
		pOnCreateSceneCallback.onCreateSceneFinished(Scene);
	}

	protected Scene createMScene() {
		// create a new scene from function
		mScene = new Scene();
		// create the bg sprite
		oBGsprite = new Sprite(0, 0, mBGTextureRegion,
				getVertexBufferObjectManager());
		AIButton = new ButtonSprite(50, 400, mAIUTextureRegion,
				mAIDTextureRegion, getVertexBufferObjectManager(), this);
		VIButton = new ButtonSprite(350, 400, mVIUTextureRegion,
				mVIDTextureRegion, getVertexBufferObjectManager(), this);
		EIButton = new ButtonSprite(200, 400, mEIUTextureRegion,mEIDTextureRegion, getVertexBufferObjectManager(), this);
		EButton = new ButtonSprite(500,400, EUTextureRegion, EDTextureRegion,getVertexBufferObjectManager(),this);
		// attach the sprite to the scene (makes it actually display)
		mScene.attachChild(oBGsprite);
		mScene.registerTouchArea(AIButton);
		mScene.attachChild(AIButton);
		// mScene.setTouchAreaBindingOnActionDownEnabled(true);
		mScene.registerTouchArea(VIButton);
		mScene.attachChild(VIButton);
		mScene.registerTouchArea(EIButton);
		mScene.attachChild(EIButton);
		mScene.registerTouchArea(EButton);
		mScene.attachChild(EButton);
		// set the position, string, and option for the text
		mText = new TickerText(25, 25, this.mFont,
				"This is an Android Database App\n created by David Willhoite",
				new TickerTextOptions(HorizontalAlign.CENTER, 10),
				this.getVertexBufferObjectManager());
		// set some modifiers since it's a ticker text (animated text
		mText.registerEntityModifier(new SequenceEntityModifier(
				new ParallelEntityModifier(new AlphaModifier(5, 0.0f, 1.0f),
						new ScaleModifier(5, 0.5f, 1.0f))// a rotation modifier
															// could be added
															// here to make the
															// text
															// rotate
		));
		// use the graphics library blend functions for alpha (commented out, so
		// alpha is full by end of ticker run)
		// mText.setBlendFunction(GLES20.GL_SRC_ALPHA,
		// GLES20.GL_ONE_MINUS_SRC_ALPHA);
		// add the text to the scene (since it's drawn after the BG sprite it is
		// on top of the sprite)
		mScene.attachChild(mText);
		mScene.setTouchAreaBindingOnActionDownEnabled(true);
		return mScene;
	}

	private String randomWeapName(){
		randomInt = ranGen.nextInt(10);
		if(randomInt == 0)
			wName = "Sword";
		else if(randomInt == 1)
			wName = "Dagger";
		else if(randomInt == 2)
			wName = "Mace";
		else if(randomInt == 3)
			wName = "Staff";
		else if(randomInt == 4)
			wName = "Axe";
		else if(randomInt == 5)
			wName = "Halberd";
		else if(randomInt == 6)
			wName = "Scimitar";
		else if(randomInt == 7)
			wName = "Spear";
		else if(randomInt == 8)
			wName = "Katana";
		else
			wName = "Wakizashi";
		return wName;
	}
	
	private String randomWeapMat(){
		randomInt = ranGen.nextInt(10);
		if(randomInt == 0)
			wMat = "Wood";
		else if(randomInt == 1)
			wMat = "Copper";
		else if(randomInt == 2)
			wMat = "Iron";
		else if(randomInt == 3)
			wMat = "Steel";
		else if(randomInt == 4)
			wMat = "Silver";
		else if(randomInt == 5)
			wMat = "Gold";
		else if(randomInt == 6)
			wMat = "Platinum";
		else if(randomInt == 7)
			wMat = "Titanium";
		else if(randomInt == 8)
			wMat = "Adamantium";
		else
			wMat = "Mithril";
		return wMat;
	}
	
	private String randomArmorName(){
		randomInt = ranGen.nextInt(10);
		if(randomInt == 0)
			aName = "Bracer";
		else if(randomInt == 1)
			aName = "Leggings";
		else if(randomInt == 2)
			aName = "Boots";
		else if(randomInt == 3)
			aName = "Shield";
		else if(randomInt == 4)
			aName = "Helm";
		else if(randomInt == 5)
			aName = "Chainmail";
		else if(randomInt == 6)
			aName = "Breastplate";
		else if(randomInt == 7)
			aName = "Half-plate";
		else if(randomInt == 8)
			aName = "Pauldrons";
		else
			aName = "Gloves";
		return aName;
	}
	
	private String randomArmorMat(){
		randomInt = ranGen.nextInt(10);
		if(randomInt == 0)
			aMat = "Wood";
		else if(randomInt == 1)
			aMat = "Copper";
		else if(randomInt == 2)
			aMat = "Iron";
		else if(randomInt == 3)
			aMat = "Steel";
		else if(randomInt == 4)
			aMat = "Silver";
		else if(randomInt == 5)
			aMat = "Gold";
		else if(randomInt == 6)
			aMat = "Platinum";
		else if(randomInt == 7)
			aMat = "Cloth";
		else if(randomInt == 8)
			aMat = "Leather";
		else
			aMat = "Mithril";
		return aMat;
	}
	
	@Override
	public void onClick(final ButtonSprite pButtonSprite,
			final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				if (pButtonSprite == AIButton) {
					Toast.makeText(MainActivity.this,
							"Item Added to inventory", Toast.LENGTH_SHORT)
							.show();
					AIsound.play();
					ranGen = new Random();
					randomInt = ranGen.nextInt(100);
					if(randomInt%2 ==1 )
					{
						myDatabase.addWeap(randomWeapName(), randomWeapMat(),wNumber);
						wNumber++;
					}
					else
					{
						myDatabase.addArmor(randomArmorName(), randomArmorMat(),aNumber);
						aNumber++;
					}
				} else if (pButtonSprite == VIButton) {
					/*Toast.makeText(MainActivity.this,
							"So you'd like to view the inventory?",
							Toast.LENGTH_SHORT).show();*/
					VIsound.play();
					startInv();
				} else if (pButtonSprite == EIButton) {
					Toast.makeText(MainActivity.this, "Inventory Emptied",
							Toast.LENGTH_SHORT).show();
					EIsound.play();
					wNumber = 0;
					aNumber = 0;
					myDatabase.emptyInventory();
				}
				else if(pButtonSprite == EButton){
					finish();
				}
			}
		});
	}

	protected void startInv() {
		this.startActivity(new Intent(this, DBViewActivity.class));
	}

	@Override
	public void onPopulateScene(Scene pScene,
			OnPopulateSceneCallback pOnPopulateSceneCallback) throws Exception {

		pOnPopulateSceneCallback.onPopulateSceneFinished();
	}

}
