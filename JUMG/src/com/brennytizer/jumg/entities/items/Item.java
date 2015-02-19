package com.brennytizer.jumg.entities.items;

public abstract class Item {
	public static final Item[] allItems = new Item[256];
	public short itemID;
	public String name;
	public int stackCount;
	public int maxStackCount;
	
	public Item(String name, short itemID, int startStackCount, int maxStackCount) throws DuplicateItemException {
		this.name = name;
		this.itemID = itemID;
		this.stackCount = startStackCount;
		this.maxStackCount = maxStackCount;
		if(Item.allItems[itemID] != null) {
			throw new DuplicateItemException(itemID, allItems[itemID], this);
		}
	}
	
	public class DuplicateItemException extends Exception {
		private static final long serialVersionUID = 1L;
		
		public int id;
		public Item first;
		public Item second;
		
		public DuplicateItemException(int id, Item first, Item second) {
			this.id = id;
			this.first = first;
			this.second = second;
		}
	}
}