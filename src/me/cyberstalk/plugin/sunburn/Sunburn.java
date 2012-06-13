package me.cyberstalk.plugin.sunburn;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import me.cyberstalk.plugin.sunburn.util.Config;
import me.cyberstalk.plugin.sunburn.util.Melden;
import net.milkbowl.vault.permission.Permission;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.getspout.spoutapi.SpoutManager;
import org.getspout.spoutapi.inventory.SpoutItemStack;
import org.getspout.spoutapi.inventory.SpoutShapedRecipe;
import org.getspout.spoutapi.material.MaterialData;
import org.getspout.spoutapi.player.SpoutPlayer;

public class Sunburn extends JavaPlugin{
	
	public SunburnPlayerListener playerListener;
	
	private static Sunburn instance;
	
	public static Sunburn getInstance() {
		return instance;
	}
	
	private static SunburnWidget widget;
	
	public static SunburnWidget getWidget(){
		return widget;
	}
	
    public static Permission perms = null;
    
    public static List<?> worldList = null;
    
    public static HashMap<String, SunburnPlayer> sunburnPlayer = new HashMap<String, SunburnPlayer>();
    
    public static SunburnItem itemLotion;
    
    public static boolean debug = true;
	
	public void onEnable() {
		instance = this;
		Config.init(this);
		playerListener = new SunburnPlayerListener(this);
		widget = new SunburnWidget(this);
		getServer().getPluginManager().registerEvents(playerListener, this);
		Melden.Info("v" + getDescription().getVersion() + " Enabled");
		worldList = Config.getWorldList();
		sillyHack();
		setupPermissions();
		cacheImages();
		makeItems();
		getServer().getPluginManager().registerEvents(new SunburnItemListener(this),this);
	}

	private void cacheImages() {
		for(String url : widget.urlFrame){
			SpoutManager.getFileManager().addToPreLoginCache(this, url);
			Melden.Info("Caching "+url);
		}
		for(String url : widget.urlMeatLotion){
			SpoutManager.getFileManager().addToPreLoginCache(this, url);
			Melden.Info("Caching "+url);
		}
		for(String url : widget.urlMeatRegular){
			SpoutManager.getFileManager().addToPreLoginCache(this, url);
			Melden.Info("Caching "+url);
		}
		SpoutManager.getFileManager().addToPreLoginCache(this, "http://dl.dropbox.com/u/79326363/Spout/Sunburn/images/items/lotion.png");
	}
	
	private void makeItems(){
		itemLotion = new SunburnItem(this, "Lotion 1x", "http://dl.dropbox.com/u/79326363/Spout/Sunburn/images/items/lotion.png");
		ItemStack result = new SpoutItemStack(itemLotion, 1);
        SpoutShapedRecipe recipe = new SpoutShapedRecipe(result);
        recipe.shape(new String[] { " A ", " B ", " C " });
		recipe.setIngredient('A', MaterialData.getMaterial(377)); // blaze powder
		recipe.setIngredient('B', MaterialData.getMaterial(335)); // milk
		recipe.setIngredient('C', MaterialData.getMaterial(374)); // glass bottle
		SpoutManager.getMaterialManager().registerSpoutRecipe(recipe);
	}

	public void onDisable() {
		Melden.Info("v" + getDescription().getVersion() + " Disabled");
	}
	
	private boolean setupPermissions() {
        RegisteredServiceProvider<Permission> rsp = getServer().getServicesManager().getRegistration(Permission.class);
        perms = rsp.getProvider();
        return perms != null;
    }
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if ((!(sender instanceof Player))) {
			Melden.Info("Console support not complete");
			return true;
		}
		Player player = (Player)sender;
		switch (args.length) {
		case 0:
			Melden.chat((Player)sender, new String[]{"Sunburn by ","Mikenon\n"}, new ChatColor[]{ChatColor.GRAY,ChatColor.DARK_AQUA});
			Melden.chat((Player)sender, new String[]{"Commands:"}, new ChatColor[]{ChatColor.GRAY});
			Melden.chat((Player)sender, new String[]{"  /sunburn", " - ", "This"}, new ChatColor[]{ChatColor.WHITE, ChatColor.DARK_AQUA, ChatColor.GRAY});
			if(perms.has(player, "sunburn.cmd.burnlevel")) {
				Melden.chat((Player)sender, 
						new String[]{" /sunburn level"," [0-9]", " - ", "Set level at which sun-exposed players ignite"}, 
						new ChatColor[]{ChatColor.WHITE, ChatColor.GRAY, ChatColor.DARK_AQUA, ChatColor.GRAY});
			}
			if(perms.has(player, "sunburn.cmd.selfburn")) {
				Melden.chat((Player)sender, 
						new String[]{" /sunburn burn", " [number]", " - ", "Burn yourself, 1 equals rougly 1/20th of a second"}, 
						new ChatColor[]{ChatColor.WHITE, ChatColor.GRAY, ChatColor.DARK_AQUA, ChatColor.GRAY});
			}
			if(perms.has(player, "sunburn.cmd.burnplayer")) {
				Melden.chat((Player)sender, 
						new String[]{" /sunburn burn", " [player] [number]", " - ", "Burn someone else, 1 equals rougly 1/20th of a second"}, 
						new ChatColor[]{ChatColor.WHITE, ChatColor.GRAY, ChatColor.DARK_AQUA, ChatColor.GRAY});
			}
			if(perms.has(player, "sunburn.cmd.reload")) {
				Melden.chat((Player)sender, 
						new String[]{" /sunburn reload", " - ", "Reload the config file"}, 
						new ChatColor[]{ChatColor.WHITE, ChatColor.DARK_AQUA, ChatColor.GRAY});
			}
			if(perms.has(player, "sunburn.cmd.weather")) {
					Melden.chat((Player)sender, 
							new String[]{" /sunburn weather", " - ", "Change the weather"}, 
							new ChatColor[]{ChatColor.WHITE, ChatColor.DARK_AQUA, ChatColor.GRAY});
			}
			return true;
		case 1:
			if (args[0].equals("permissions")) {
				if(perms.has(player, "sunburn.cmd.permissions")) {
					ChatColor[] colors = new ChatColor[]{ChatColor.WHITE, ChatColor.DARK_AQUA, ChatColor.GRAY};
					Melden.chat((Player)sender, new String[]{" Burn Immunity", " - ", "sunburn.immunity"}, colors);
					Melden.chat((Player)sender, new String[]{" /sunburn level", " - ", "sunburn.cmd.burnlevel"}, colors);
					Melden.chat((Player)sender, new String[]{" /sunburn weather", " - ", "sunburn.cmd.weather"}, colors);
					Melden.chat((Player)sender, new String[]{" /sunburn reload", " - ", "sunburn.cmd.reload"}, colors);
					Melden.chat((Player)sender, new String[]{" /sunburn burn [self]", " - ", "sunburn.cmd.selfburn"}, colors);
					Melden.chat((Player)sender, new String[]{" /sunburn burn [others]", " - ", "sunburn.cmd.burnplayer"}, colors);
					Melden.chat((Player)sender, new String[]{" /sunburn permissions", " - ", "sunburn.cmd.permissions"}, colors);
					return true;
				}
			}
			if (args[0].equals("pop")) {
				if(perms.has(player, "sunburn.cmd.internalpoptest")) {
					SpoutPlayer p = SpoutManager.getPlayer((Player) sender);
					if (p == null) {
						Melden.chat((Player)sender, new String[]{"You need SpoutCraft to do that!"}, new ChatColor[]{ChatColor.DARK_RED});
						return true;
					} else {
						Melden.sendNotification(p, "Pop goes the weasel!");
					}
				} else {
					Melden.chat((Player)sender, new String[]{"Permission Denied"}, new ChatColor[]{ChatColor.GRAY});
				}
				return true;
			}
			if (args[0].equals("level")) {
				if(perms.has(player, "sunburn.cmd.burnlevel")) {
					Melden.chat((Player)sender, new String[]{"Usage: ","\\sunburn level [0-9]"}, new ChatColor[]{ChatColor.GRAY,ChatColor.DARK_AQUA});
				} else {
					Melden.chat((Player)sender, new String[]{"Permission Denied"}, new ChatColor[]{ChatColor.GRAY});
				}
				return true;
			}
			if (args[0].equals("burn")) {
				if(perms.has(player, "sunburn.cmd.selfburn")) {
					Melden.chat((Player)sender, new String[]{"Usage: ","\\sunburn burn [number]"}, new ChatColor[]{ChatColor.GRAY,ChatColor.DARK_AQUA});
				} else {
					Melden.chat((Player)sender, new String[]{"Permission Denied"}, new ChatColor[]{ChatColor.GRAY});
				}
				return true;
			}
			if (args[0].equals("reload")) {
				if(perms.has(player, "sunburn.cmd.reload")) {
					Config.reload();
					worldList = Config.getWorldList();
					Melden.chat((Player)sender, new String[]{"Config Reloaded"}, new ChatColor[]{ChatColor.GRAY});
				} else {
					Melden.chat((Player)sender, new String[]{"Permission Denied"}, new ChatColor[]{ChatColor.GRAY});
				}
				return true;
			}
			if (args[0].equals("weather")) {
				Player p = (Player)sender;
				if(perms.has(player, "sunburn.cmd.weather")) {
					p.getWorld().setWeatherDuration(1);
					Melden.chat((Player)sender, new String[]{"Changing weather"}, new ChatColor[]{ChatColor.GRAY});
				} else {
					Melden.chat((Player)sender, new String[]{"Permission Denied"}, new ChatColor[]{ChatColor.GRAY});
				}
				return true;
			}
			return false;
		case 2:
			if (args[0].equals("level")) {
				if(Integer.parseInt(args[1])>-1 && Integer.parseInt(args[1])<10){
					Config.setBurnLevel(Integer.parseInt(args[1]));
					Melden.chat((Player)sender, 
							new String[]{"Burn Level set to ",""+Config.getBurnLevel()}, 
							new ChatColor[]{ChatColor.GRAY,ChatColor.DARK_AQUA});
				} else {
					Melden.chat((Player)sender, 
							new String[]{"Usage: ","\\sunburn level [0-9]"}, 
							new ChatColor[]{ChatColor.GRAY,ChatColor.DARK_AQUA});
				}
				return true;
			}
			if (args[0].equals("burn")) {
				if(perms.has(player, "sunburn.cmd.selfburn")) {
					if(Integer.parseInt(args[1])>-1){
						int ticks = Integer.parseInt(args[1]);
						Player p = (Player)sender;
						p.setFireTicks(ticks);
						Melden.chat((Player)sender, 
								new String[]{"Burning ","you"," for ",""+ticks," ticks"}, 
								new ChatColor[]{ChatColor.GRAY,ChatColor.DARK_AQUA,ChatColor.GRAY,ChatColor.DARK_AQUA,ChatColor.GRAY});
					} else {
						Melden.chat((Player)sender, new String[]{"Usage: ","\\sunburn burn [number]"}, new ChatColor[]{ChatColor.GRAY,ChatColor.DARK_AQUA});
					}
				} else {
					Melden.chat((Player)sender, new String[]{"Permission Denied"}, new ChatColor[]{ChatColor.GRAY});
				}
				return true;
			}
			return false;
		case 3:
			if (args[0].equals("burn")) {
				if(perms.has(player, "sunburn.cmd.burnplayer")) {
					String pName = args[1];
					if(Integer.parseInt(args[2])>-1){
						int ticks = Integer.parseInt(args[2]);
						if(this.getServer().getPlayer(pName) != null){
							Player p = this.getServer().getPlayer(pName);
							p.setFireTicks(ticks);
							Melden.chat((Player)sender, 
									new String[]{"Burning ",pName," for ",""+ticks," ticks"}, 
									new ChatColor[]{ChatColor.GRAY,ChatColor.DARK_AQUA,ChatColor.GRAY,ChatColor.DARK_AQUA,ChatColor.GRAY});
						} else {
							Melden.chat((Player)sender, 
									new String[]{pName+" is not a valid player"}, 
									new ChatColor[]{ChatColor.RED});
							Melden.chat((Player)sender, 
									new String[]{"Usage: ","\\sunburn burn [player] [number]"}, 
									new ChatColor[]{ChatColor.GRAY,ChatColor.DARK_AQUA});
						}
					} else {
						Melden.chat((Player)sender, 
								new String[]{"Usage: ","\\sunburn burn [player] [number]"}, 
								new ChatColor[]{ChatColor.GRAY,ChatColor.DARK_AQUA});
					}
				} else {
					Melden.chat((Player)sender, new String[]{"Permission Denied"}, new ChatColor[]{ChatColor.GRAY});
				}
				return true;
			}
		default:
			Melden.chat((Player)sender, new String[]{"Invalid Syntax"}, new ChatColor[]{ChatColor.DARK_RED});
		}
		return false;
	}

	private void sillyHack(){
		Config.setBurnLevel(Config.getBurnLevel());
	}

	static public int asyncTaskId = -1;
	
	static Runnable lotionUpdater = new Runnable(){
		@Override
		public void run() {
			Iterator<String> names = Sunburn.sunburnPlayer.keySet().iterator();
			boolean shouldCancel = true;
		    while (names.hasNext()) {
		    	String name = names.next();
		    	int itemLevel = Sunburn.sunburnPlayer.get(name).getItemStatus();
		    	if(itemLevel > -1){
		    		Sunburn.sunburnPlayer.get(name).updateWidget();
		    		shouldCancel = false;
		    	}
		    }
		    if(shouldCancel){
		    	Sunburn.cancelLotionUpdater();
		    }
	    }
	};

	static void cancelLotionUpdater() {
		System.out.println("[Sunburn] cancelLotionUpdater() taskId: "+Sunburn.asyncTaskId);
		if(Sunburn.asyncTaskId > -1){
			Bukkit.getServer().getScheduler().cancelTask(Sunburn.asyncTaskId);
			Sunburn.asyncTaskId = -1;
		}
	}	
}
