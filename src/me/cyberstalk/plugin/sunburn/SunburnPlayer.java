package me.cyberstalk.plugin.sunburn;

import me.cyberstalk.plugin.sunburn.util.Config;
import me.cyberstalk.plugin.sunburn.util.Melden;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.getspout.spoutapi.player.SpoutPlayer;

public class SunburnPlayer {
	public Sunburn plugin;
	public Player player;
	public String name;
	public boolean immune;
	public String world;
	public int lightLevel;
	public int burnLevel;
	public int imgLevel;
	public int itemLotionUsed = 0;
	private int itemLastUsed;
	private int itemDuration;
	private int itemStrength;
	
	SunburnPlayer(Sunburn plugin, String name){
		this.plugin = plugin;
		this.name = name;
		player = plugin.getServer().getPlayer(this.name);
		this.immune = Sunburn.perms.has(player, "sunburn.immunity");
		this.world = player.getWorld().getName();
		Sunburn.getWidget().initWidget((SpoutPlayer)player);
		Melden.Debug("SunburnPlayer created");
	}
	
	// private static HashMap<String, SunburnPlayer> sunburnPlayer = new HashMap<String, SunburnPlayer>();
	
	public void onMove(){
		Melden.Debug("onMove");
		calcLightLevel();
		updateWidget();
		updateBurnTicks();
	}
	
	public void updateWidget(){
		int itemLevel = getItemStatus();
		if(itemLevel > -1){
			Sunburn.getWidget().setFrame(1);
		} else if(this.lightLevel < Config.getBurnLevel()){
			Sunburn.getWidget().setFrame(0);
		} else {
			Sunburn.getWidget().setFrame(2);
		}
		if(itemLevel > -1){
			Sunburn.getWidget().setLotion(itemLevel);
		}
		Sunburn.getWidget().setRegular(this.lightLevel);
	}
	
	public void updateBurnTicks(){
		if(creativeOrImmune()) return;
		Melden.Debug("updateBurnTicks");
		int item = getItemStatus();
		Melden.Debug("Item: "+item);
		if(item > -1){
			Melden.chat(player, "Item Immunity: "+item);
		} else {
			Melden.Debug("No Item: "+item);
			if(Config.worldEnabled(player.getWorld().getName())){
				if(lightLevel>=Config.getBurnLevel()){
					burnPlayer(Config.getBurnTicksOn());
				} else if (lightLevel < Config.getBurnLevel() && player.getFireTicks() > 20){
					burnPlayer(Config.getBurnTicksOff());
				}
			}
		}
		
	}
	
	public void burnPlayer(int ticks){
		player.setFireTicks(ticks);
	}
	
	public boolean creativeOrImmune(){
		if(player.getGameMode()==GameMode.CREATIVE)
			return true;
		if(getItemStatus()>-1)
			return true;
//		return this.immune;
		return false;
	}
	
	public void calcLightLevel(){
		Location loc = player.getLocation();
		Block block = loc.getBlock();
		byte lightAll	= block.getLightLevel();
		byte lightSky	= block.getLightFromSky();
		byte lightBlock = block.getLightFromBlocks();
		this.lightLevel	= (int) Math.ceil(((lightAll-lightBlock) * lightSky)/25);
	}
	public int getLightLevel(){
		calcLightLevel();
		return this.lightLevel;
	}
	
	public void itemUsed(ItemType type){
		if(type==ItemType.Lotion1x){
			this.itemStrength = 1;
			long t = System.currentTimeMillis();
			this.itemLastUsed = (int) (t / 1000);
			this.itemDuration = this.itemStrength * 10;
			Sunburn.getWidget().setFrame(1);
			Sunburn.getWidget().setLotion(9);
			Sunburn.asyncTaskId = Bukkit.getServer().getScheduler().scheduleAsyncRepeatingTask(
																		Sunburn.getInstance(), 
																		Sunburn.lotionUpdater, 20, 10);
		}
	}
	
	public int getItemStatus(){
		long t = System.currentTimeMillis();
		int currentTime = (int) (t / 1000);
		int endTime = this.itemLastUsed + this.itemDuration;
//		Melden.Debug("Current Time: "+currentTime);
//		Melden.Debug("End Time: "+endTime);
		if(currentTime > endTime){
//			Melden.Debug("Returning 0");
			return -1;
		} else {
			int numb = (endTime - currentTime) / this.itemStrength;
//			Melden.Debug("Returning "+numb);
			return numb;
		}
	}
	
	public void setName(String name){
		this.name = name;
	}
	public String getName(){
		return this.name;
	}
	
	public void setImmune(boolean i){
		this.immune = i;
	}
	public boolean getImmune(){
		return this.immune;
	}
	
	public void setWorld(String w){
		this.world = w;
	}
	public String getWorld(){
		return this.world;
	}
	
	public void setBurnLevel(int b){
		this.burnLevel = b;
	}
	public int getBurnLevel(){
		return this.burnLevel;
	}
	
	public void setImageLevel(int i){
		this.imgLevel = i;
	}
	public int getImageLevel(){
		return this.imgLevel;
	}
}