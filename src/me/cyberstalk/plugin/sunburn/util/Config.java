package me.cyberstalk.plugin.sunburn.util;

import java.util.Arrays;
import java.util.List;

import me.cyberstalk.plugin.sunburn.Sunburn;

public class Config {
	private static Sunburn plugin;

	static boolean cEnabled;
	static List<?> cWorlds;
	static int cLevel;
	static int cBurnOn;
	static int cBurnOff;
	static int cWidgetX;
	static int cWidgetY;
	static int cWidgetW;
	static int cWidgetH;
	static String cUrl;
	static boolean cDebug;
	static boolean cStats;
	
	public static void init(Sunburn instance) {
		plugin = instance;
		plugin.getConfig().options().header(getHeader());
		plugin.getConfig().options().copyDefaults(true);
		save();
		plugin.reloadConfig();
		setVars();
		if(isDebug()){
			printVars();
		}
	}

	public static void save() {
		plugin.saveConfig();
	}

	public static void reload() {
		plugin.reloadConfig();
		setVars();
	}

	public static boolean worldEnabled(String name){
		if(cWorlds.contains(name)){
			return true;
		}
		return false;
	}
	
	public static List<?> getWorldList(){
		return cWorlds;
	}
	
	public static boolean isEnabled() {
		return cEnabled;
	}

	public static int getBurnLevel() {
		return cLevel;
	}

	public static void setBurnLevel(int value) {
		plugin.getConfig().set("burnLevel", value);
		save();
		reload();
	}

	public static int getBurnTicksOn() {
		return cBurnOn;
	}

	public static int getBurnTicksOff() {
		return cBurnOff;
	}

	public static int getWidgetX() {
		return cWidgetX;
	}

	public static int getWidgetY() {
		return cWidgetY;
	}

	public static int getWidgetW() {
		return cWidgetW;
	}

	public static int getWidgetH() {
		return cWidgetH;
	}

	public static String getWidgetUrl() {
		return cUrl;
	}
	
	public static boolean isDebug(){
		return cDebug;
	}
	
	public static boolean allowStats(){
		return cStats;
	}
	
	public static void setVars(){
		cEnabled = plugin.getConfig().getBoolean("enabled", true);
		cWorlds = plugin.getConfig().getList("worlds",Arrays.asList(new String[] {"world","newworld","another_world"}));
		cLevel = plugin.getConfig().getInt("burnLevel", 9);
		cBurnOn = plugin.getConfig().getInt("burnTicksOn", 320);
		cBurnOff = plugin.getConfig().getInt("burnTicksOff", 20);
		cWidgetX = plugin.getConfig().getInt("widget.x", 99);
		cWidgetY = plugin.getConfig().getInt("widget.y", 218);
		cWidgetW = plugin.getConfig().getInt("widget.w", 21);
		cWidgetH = plugin.getConfig().getInt("widget.h", 21);
		cUrl = plugin.getConfig().getString("widget.textureUrl");
		cDebug = plugin.getConfig().getBoolean("debuglog",false);
		cStats = plugin.getConfig().getBoolean("enablestats",true);
	}
	
	public static void printVars(){
		Melden.Debug("[config] enabled "+cEnabled);
		Melden.Debug("[config] world list "+cWorlds);
		Melden.Debug("[config] burn level "+cLevel);
		Melden.Debug("[config] burn ticks on "+cBurnOn);
		Melden.Debug("[config] burn ticks off "+cBurnOff);
		Melden.Debug("[config] widget x "+cWidgetX);
		Melden.Debug("[config] widget y "+cWidgetY);
		Melden.Debug("[config] widget width "+cWidgetW);
		Melden.Debug("[config] widget height "+cWidgetH);
		Melden.Debug("[config] widget url "+cUrl);
		Melden.Debug("[config] debuglog "+cDebug);
	}

	private static String getHeader() {
		String header = "Author: Mikenon\n"
				+ "Created: May 25th, 2012"
				+ "Updated: June 15th, 2012\n"
				+ "This plugin is a Work in Progress.\n"
				+ "---------------------------------- \n"
				+ "enabled [true|false]\n"
				+ "worlds [list of worlds to burn people in]"
				+ "burnLevel [0-9] - The amount of sunlight it takes to ignite a player.\n"
				+ "-- Calculation: Ceil( ( (total light - light from blocks) * light from sky) / 25 )\n"
				+ "burnTicksOn [number] - The number of ticks to burn a player when exposed to burnLevel light\n"
				+ "burnTicksOff [number] - The number of ticks to burn a player after exposed to burnLevel light\n"
				+ "-- One second is roughly 20 ticks.\n"
				+ "widget: - The visible graphic added to the HUD\n"
				+ "  x: [0-427] - Placement of the widget, number of pixels from the left side.\n"
				+ "  y: [0-240] - Placement of the widget, number of pixels from the top.\n"
				+ "  w: [number] - The width of the widget, default is 21\n"
				+ "  h: [number] - The height of the widget, default is 21\n"
				+ "  textureUrl: [http://something] - Change this only if you want to override the default art.\n"
				+ "  -- textureUrl+\"00.png\" through textureUrl+\"09.png\", as well as \"fire.png\", \"frameBlack.png\" and \"frameRed.png\"\n"
				+ "  -- must be available.\n"
				+ "debuglog: [true|false] - renamed in version 0.5 to set default on previous installs.\n"
				+ "enablestats: [true|false] - enable the plugin to report stats to http://mcstats.org/plugin/sunburn";
		return header;
	}
}