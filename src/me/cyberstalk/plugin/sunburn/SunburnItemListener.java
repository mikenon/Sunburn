package me.cyberstalk.plugin.sunburn;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.getspout.spoutapi.inventory.SpoutItemStack;
import org.getspout.spoutapi.player.SpoutPlayer;

public class SunburnItemListener implements Listener{
	
	Sunburn plugin;

	public SunburnItemListener(Sunburn instance) {
		this.plugin = instance;
	}
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		SpoutPlayer splayer = (SpoutPlayer) player;
		if (new SpoutItemStack(splayer.getItemInHand()).isCustomItem()) {
			String name = new SpoutItemStack(splayer.getItemInHand()).getMaterial().getName();
			if(Sunburn.itemLotion.itemName.equalsIgnoreCase(name)){
				if(event.getAction()==Action.RIGHT_CLICK_AIR || event.getAction()==Action.RIGHT_CLICK_BLOCK){
					String playername = player.getName();
					if (player.getItemInHand().getAmount() > 1)
						player.getItemInHand().setAmount(
								player.getItemInHand().getAmount() - 1);
					else {
						player.setItemInHand(new ItemStack(
								org.bukkit.Material.AIR));
					}
					if(player.getFireTicks()>0) player.setFireTicks(0);
					Sunburn.sunburnPlayer.get(playername).itemUsed(Sunburn.itemLotion.itemType);
					player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 15, 1), false);
				}
			}
		}
	}
}