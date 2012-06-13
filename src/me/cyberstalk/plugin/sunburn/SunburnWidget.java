package me.cyberstalk.plugin.sunburn;

import org.getspout.spoutapi.gui.GenericTexture;
import org.getspout.spoutapi.gui.RenderPriority;
import org.getspout.spoutapi.player.SpoutPlayer;

import me.cyberstalk.plugin.sunburn.util.Config;
import me.cyberstalk.plugin.sunburn.util.Melden;

public class SunburnWidget {

	Sunburn plugin;
	public GenericTexture imageMeatLotion  = null;
	public GenericTexture imageMeatRegular  = null;
	public GenericTexture imageFrame = null;
	public String[] urlMeatRegular;
	public String[] urlMeatLotion;
	public String[] urlFrame;
	public String urlBase;
	
	SunburnWidget(Sunburn instance){
		this.plugin = instance;
		urlBase = Config.getWidgetUrl();
		urlMeatRegular  = new String[]{urlBase+"00.png",urlBase+"01.png",urlBase+"02.png",
								urlBase+"03.png",urlBase+"04.png",urlBase+"05.png",
								urlBase+"06.png",urlBase+"07.png",urlBase+"08.png",
								urlBase+"fire.png"};
		urlMeatLotion  = new String[]{urlBase+"00.png",urlBase+"gray01.png",urlBase+"gray02.png",urlBase+"gray03.png",
								urlBase+"gray04.png",urlBase+"gray05.png",urlBase+"gray06.png",
								urlBase+"gray07.png",urlBase+"gray08.png",urlBase+"gray09.png"};
		urlFrame = new String[]{urlBase+"frameBlack.png",
								urlBase+"frameWhite.png",
								urlBase+"frameRed.png"};
	}
	
	public void initWidget(SpoutPlayer player){
		Melden.Info("initWidget()");
//    	The inner part of the widget - lotion sun
    	imageMeatLotion = new GenericTexture();
    	imageMeatLotion.setX(Config.getWidgetX())
    			 .setY(Config.getWidgetY())
    			 .setWidth(Config.getWidgetW())
    			 .setHeight(Config.getWidgetH())
    			 .setPriority(RenderPriority.Lowest)
    			 .setAutoDirty(true);
    	imageMeatLotion.setUrl(urlMeatLotion[0]);
// 		The inner part of the widget - regular sun
    	imageMeatRegular = new GenericTexture();
    	imageMeatRegular.setX(Config.getWidgetX())
    			 .setY(Config.getWidgetY())
    			 .setWidth(Config.getWidgetW())
    			 .setHeight(Config.getWidgetH())
    			 .setPriority(RenderPriority.Normal)
    			 .setAutoDirty(true);
    	imageMeatRegular.setUrl(urlMeatRegular[0]);
    	// The outer part of the widget
    	imageFrame = new GenericTexture();
    	imageFrame.setX(Config.getWidgetX())
				  .setY(Config.getWidgetY())
				  .setWidth(Config.getWidgetW())
			 	  .setHeight(Config.getWidgetH())
    			  .setAutoDirty(true);
    	imageFrame.setUrl(urlFrame[0]);
    	// Add them all to the screen
    	player.getMainScreen().attachWidget(plugin, imageMeatLotion);
    	player.getMainScreen().attachWidget(plugin, imageMeatRegular);
    	player.getMainScreen().attachWidget(plugin, imageFrame);
    }
    
    public void updateWidget(SpoutPlayer player, int BurnLevel){
//    	if(imageMeatRegular == null){
//    		initWidget(player);
//    	}
//    	if(BurnLevel >= Config.getBurnLevel()){
//    		setFrame(2);
//    	} else {
//    		setFrame(0);
//    	}
//    	setRegular(BurnLevel);
    }
    
    public void setRegular(int level){
    	imageMeatRegular.setUrl(urlMeatRegular[level]);
    	Melden.Info("Widget setRegular("+level+")");
    }
   
    public void setLotion(int level){
    	imageMeatLotion.setUrl(urlMeatLotion[level]);
    	Melden.Info("Widget setLotion("+level+")");
    }
    
    public void setFrame(int level){
    	imageFrame.setUrl(urlFrame[level]);
    	Melden.Info("Widget setFrame("+level+")");
    }
}
