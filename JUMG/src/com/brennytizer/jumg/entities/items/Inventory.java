package com.brennytizer.jumg.entities.items;

public class Inventory {
	public Item[] inventory;
	
	public Inventory() {
		this(new Item[] {});
	}
	
	public Inventory(Item[] inventory) {
		this.inventory = inventory;
	}
	
	public Item[] getInventory() {
		return this.inventory;
	}
	
	public boolean contains(String name) {
		for(Item a : inventory)
			if(a.getName().equals(name)) return true;
		return false;
	}
	
	public boolean contains(short itemID) {
		for(Item a : inventory)
			if(a.getItemID() == itemID) return true;
		return false;
	}
	
	public boolean contains(Item i, boolean byName) {
		if(byName) return contains(i.getName());
		else return contains(i.getItemID());
	}
	
	public Item getItemAt(int index) {
		return inventory[index];
	}
	
	public Item[] resizeInventory(int size) {
		Item[] newItems = new Item[size];
		if(size > inventory.length) {
			for(int i = 0; i < inventory.length; i++)
				newItems[i] = inventory[i];
		} else if(size < inventory.length) {
			for(int i = 0; i < size; i++)
				newItems[i] = inventory[i];
		} else {
			return this.inventory;
		}
		return newItems;
	}
}