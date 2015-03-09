package com.brennytizer.jumg.entities.ai.pathing;

import java.util.Random;

import com.brennytizer.jumg.utils.geom.PolygonalObject;

/**
 * This class can generate a 'Node Connection Web' using the width and height of
 * the map and all the {@link PolygonalObject} which act as obstacles that can
 * be found on the map.<br>
 * <br>
 * This will be created after all the other paths. This is similar to the free
 * roam but instead calculates connections between vertices of map bounds and
 * polygon boundaries based off what that vertex can 'see'. What a vertex can
 * 'see' is based on whether or not there is an obstruction between this vertex
 * and the other vertex in question, ie, if vertex A can make a straight line to
 * vertex B (based on if the Mover supplied can cross the land on the way), a
 * line will be made between vertices A and B. An extra calculation will be
 * performed to determine how many Nodes should be placed on this line.<br>
 * <br>
 * An extra check will be made to make sure that there are no other nodes within
 * a proximity specified. If the nodes are within close proximity they will be
 * sent to a NodeChooser to select which node is to be kept. This would be
 * repeated if there were multiple nodes within the same proximity of a single
 * node.<br>
 * <br>
 * Each node will be given a unique ID (most likely a hex ID of unstoppable
 * length if required) and each node will be placed in another node's
 * "Connection Table". A 'connection' is only made when a node can directly
 * reach another node, and doesn't need to pass through an extra node to get
 * there. This will be based simply on angle checking. Consider node
 * <code>0xA</code>, <code>0xB</code> and <code>0xC</code>. The angle between
 * <code>0xA</code> and <code>0xB</code> is 45 degrees. If the angle between
 * <code>0xA</code> and <code>0xC</code> is within 22 degrees either side of
 * <code>angle(0xA, 0xB)</code> then <code>connection(0xA, 0xC)</code> will not
 * exist. However <code>connection(0xC, 0xA)</code> can exist if node
 * <code>0xC</code> was not removed by the previous check (to prevent nodes in
 * close proximity to be available).
 */
public class PathWeb {
	public RandomNode randNodeChooser = new RandomNode();
	public int mapWidth;
	public int mapHeight;
	public Mover mover;
	public PolygonalObject[] objectsOnMap;
	
	public PathWeb(int mapWidth, int mapHeight, Mover mover, PolygonalObject ... objectsOnMap) {
		this.mapWidth = mapWidth;
		this.mapHeight = mapHeight;
		this.mover = mover;
		this.objectsOnMap = objectsOnMap;
	}
	
	public class Node {}
	
	public class ConnectionTable {}
	
	public interface NodeChooser {
		public Node nodeToRemove(Node a, Node b);
	}
	
	public class RandomNode implements NodeChooser {
		public Node nodeToRemove(Node a, Node b) {
			if(new Random().nextBoolean()) return a;
			else return b;
		}
	}
}