package me.cyberstalk.plugin.sunburn;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class SunburnPlayerListener implements Listener{
	Sunburn plugin;

	public SunburnPlayerListener(Sunburn instance) {
		this.plugin = instance;
	}

	@EventHandler
	public void onJoinLogin(PlayerJoinEvent event) {
		Sunburn.sunburnPlayer.put(event.getPlayer().getName(), new SunburnPlayer(plugin, event.getPlayer().getName()));
	}
	
	@EventHandler
	public void OnPlayerQuit(PlayerQuitEvent e) {
		if(Sunburn.sunburnPlayer.containsKey(e.getPlayer().getName())){
			Sunburn.sunburnPlayer.remove(e.getPlayer().getName());
		}
	}
	
	@EventHandler
	public void onPlayerMove(PlayerMoveEvent event){
		Sunburn.sunburnPlayer.get(event.getPlayer().getName()).onMove();
	}
}