package com.brennytizer.jumg.entities.items;

public abstract class Item {
	public static final Item[] allItems = new Item[256];
	public short itemID;
	public String name;
	public int stackCount;
	public int maxStackCount;
	
	public Item(String name, short itemID, int startStackCount, int maxStackCount) {
		if(Item.allItems[itemID] != null) {
			this.name = allItems[itemID].name;
			this.itemID = allItems[itemID].itemID;
			this.stackCount = allItems[itemID].stackCount;
			this.maxStackCount = allItems[itemID].maxStackCount;
		} else {
			this.name = name;
			this.itemID = itemID;
			this.stackCount = startStackCount;
			this.maxStackCount = maxStackCount;
		}
	}
	
	public short getItemID() {
		return itemID;
	}
	
	public String getName() {
		return name;
	}
	
	public int getStackCount() {
		return stackCount;
	}
	
	public int getMaxStackCount() {
		return maxStackCount;
	}
	
	public Item getItem(short id) {
		if(id < 0 || id > 255) return null;
		return allItems[id];
	}
	
	public Item getItem(String name) {
		for(Item i : allItems)
			if(i.getName().equals(name)) return i;
		return null;
	}
}