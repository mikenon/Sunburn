package me.cyberstalk.plugin.sunburn;

import org.bukkit.plugin.Plugin;
import org.getspout.spoutapi.material.item.GenericCustomItem;

public class SunburnItem extends GenericCustomItem{
  String itemSound;
  String itemName;
  ItemType itemType;

  public SunburnItem(Plugin plugin, String name, String texture, ItemType type){
    super(plugin, name, texture);
    itemName = name;
    itemType = type;
  }

}