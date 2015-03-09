package com.brennytizer.jumg.entities.ai.pathing;

import com.brennytizer.jumg.utils.Logging;
import com.brennytizer.jumg.utils.Logging.LoggingSpice;
import com.brennytizer.jumg.utils.SortedList;
import com.brennytizer.jumg.utils.Words;
import com.brennytizer.jumg.utils.geom.Collisions;
import com.brennytizer.jumg.utils.geom.Point2D;

// Creates paths and stuff...
public class PathFinding {
	public NodeSortedList open = new NodeSortedList();
	public NodeSortedList closed = new NodeSortedList();
	public int exhaustStart = 80;
	public int exhaustOffset = 7;
	public int exhaustModifier = 1;
	public int heuristicAddition = 0;
	public Point2D start;
	public Point2D end;
	public float endRadius;
	
	// See the next constructor
	public PathFinding(Point2D start, Point2D end) {
		this(start, end, 1.0F);
	}
	
	// Sets the start end and how far away from the end we are allowed to be to stop the search
	public PathFinding(Point2D start, Point2D end, float endRadius) {
		this.start = start;
		this.end = end;
		this.endRadius = endRadius;
	}
	
	// Sets exhaust data - exhaust data is when you start reaching an incredible depth, and
	//   the search starts to become a greedy best first search by A* rules.
	public void setExhaustionData(int exhaustStart, int exhaustOffset, int exhaustModifier) {
		this.exhaustStart = exhaustStart;
		this.exhaustOffset = exhaustOffset;
		this.exhaustModifier = exhaustModifier;
	}
	
	// Creates a path using a web. - Currently unavailable
	public Path pathFromWeb(PathWeb web, Mover mover) {
		boolean quit = true;
		if(quit) return null;
		return null;
	}
	
	// This will force (x,y) rounding - it will use the Manhattan heuristic.
	public Path pathFromTileMap(MapChecker mapChecker, Mover mover, boolean diagonal) {
		Logging.log(LoggingSpice.MILD, "Starting to create a path on a tile map. Diagonal? " + diagonal);
		open.clear();
		closed.clear();
		Node start = new Node(this.start.x, this.start.y, 0, null, 1, false);
		Logging.log(LoggingSpice.MILD, Words.insert("  Start node placed at ({0}, {1})", start.x, start.y));
		start.minCostMod = mapChecker.getCostModifier(mover);
		open.push(start);
		float newCost = 0;
		float nX = 0;
		float nY = 0;
		Node n = null;
		Node nn = null;
		while(open.size() > 0) {
			n = open.pop();
			calculateExhaust(n);
			if(isGoalMet(n)) {
				Logging.log(LoggingSpice.MILD, "Path finished!");
				return new Path().recurse(n);
			} else {
				// Leaving this out for the moment. Checking if we exhaust when there's a maze.
				//if(n.depth > maxDepth) continue; // Oh no! The depth was exhausted!
				for(int y = -1; y < 2; y++) {
					for(int x = -1; x < 2; x++) {
						nX = n.x + x;
						nY = n.y + y;
						if(nX != 0 && nY != 0) continue;
						newCost = n.costFromStart + 1 * mapChecker.getCostModifier(mover, n.x, n.y, x, y);
						nn = new Node(nX, nY, newCost, n, mapChecker.getCostModifier(mover), false);
						if((open.contains(nn) || closed.contains(nn)) && nn.costFromStart <= newCost) continue;
						else {
							closed.remove(nn);
							if(open.contains(nn)) open.override(nn);
							else open.add(nn);
							Logging.log(LoggingSpice.MILD, Words.insert("  Added node: {0}", nn.toString()));
						}
					}
				}
			}
		}
		Logging.log(LoggingSpice.MEDIUM, "Bummer! We didn't manage to create a path to our destination");
		return new Path().recurse(start);
	}
	
	// Free roams the map in search for an A* path to the goal
	// The smaller the angle the more time this will take.
	// Rounding a value is used for tiles, when being half on a tile is illegal. - False if the player can 'Free Roam'
	public Path pathFreeRoam(MapChecker mapChecker, Mover mover, float angleOffset, boolean roundCoords) {
		Logging.log(LoggingSpice.MILD, Words.insert("Starting to create a free roam path. [Angle:{0}], [Rounding Coordinates:{1}]", angleOffset, roundCoords));
		open.clear();
		closed.clear();
		Node start = new Node(this.start.x, this.start.y, 0, null, 1, true);
		Logging.log(LoggingSpice.MILD, Words.insert("  Start node placed at ({0}, {1})", start.x, start.y));
		start.minCostMod = mapChecker.getCostModifier(mover);
		open.push(start);
		float newCost = 0;
		float nX = 0;
		float nY = 0;
		Node nn = null;
		while(open.size() > 0) {
			Node n = open.pop();
			calculateExhaust(n);
			if(isGoalMet(n)) {
				Logging.log(LoggingSpice.MILD, "Path finished!");
				return new Path().recurse(n);
			} else {
				for(float angle = 0; angle < 360; angle += angleOffset) {
					Point2D p = new Point2D(n.x, n.y - 1).rotate(n, angle);
					nX = p.x;
					nY = p.y;
					newCost = n.costFromStart + 1 * mapChecker.getCostModifier(mover, n.x, n.y, p.x, p.y);
					nn = new Node(nX, nY, newCost, n, mapChecker.getCostModifier(mover), true);
					if((open.contains(nn) || closed.contains(nn)) && nn.costFromStart <= newCost) continue;
					else {
						closed.remove(nn);
						if(open.contains(nn)) open.override(nn);
						else open.add(nn);
						Logging.log(LoggingSpice.MILD, Words.insert("  Added node: {0}", nn.toString()));
					}
				}
			}
		}
		return null;
	}
	
	// Checks if the node we are checking is within the end radius.
	public boolean isGoalMet(Node n) {
		return Collisions.collidedCircles(end, endRadius, n, 0.1F);
	}
	
	// Calculates the exhaust we should add.
	public void calculateExhaust(Node n) {
		int depth = n.depth - exhaustStart;
		if(depth < 0) return;
		heuristicAddition = ((depth / exhaustOffset) + 1) * exhaustModifier;
	}
	
	// A node class.
	public class Node extends Point2D implements Comparable<Node> {
		private static final long serialVersionUID = 1L;
		public Node parent;
		public int depth = 0;
		public float costFromStart;
		public float costModifier;
		public float minCostMod;
		public float costToGoal;
		public boolean useEuclid;
		
		public Node(float x, float y, float costFromStart, Node parent, float costModifier, boolean useEuclid) {
			super(x, y);
			this.costFromStart = costFromStart; // g
			if(parent != null) this.depth = parent.depth + 1;
			this.parent = parent;
			this.costModifier = costModifier;
			this.minCostMod = costModifier;
			this.useEuclid = useEuclid;
			this.costToGoal = estimateHeuristic(PathFinding.this.end, useEuclid);
		}
		
		public float estimateHeuristic(Point2D p, boolean euclidean) { // h
			float cost = 0;
			if(euclidean) cost = (float) Math.sqrt(Math.pow(p.x - this.x, 2) + Math.pow(p.y - this.y, 2));
			else cost = Math.abs(p.x - this.x) + Math.abs(p.y - this.y);
			return cost * minCostMod + PathFinding.this.heuristicAddition;
		}
		
		public float total(Point2D p, boolean euclidean) { // f
			return costFromStart + estimateHeuristic(p, euclidean);
		}
		
		public int compareTo(Node n) {
			float myHeu = total(PathFinding.this.end, useEuclid);
			float nHeu = n.total(PathFinding.this.end, n.useEuclid);
			return myHeu > nHeu ? 1 : myHeu < nHeu ? -1 : 0;
		}
		
		public String toString() {
			return Words.insert("Node[loc:({0},{1}), parent:({2},{3}), costMod:{4}, g():{5}, h():{6}, f():{7}", x, y, parent.x, parent.y, minCostMod, costFromStart, costToGoal, estimateHeuristic(PathFinding.this.end, useEuclid));
		}
	}
	
	// A Path class (contains the recursive function).
	public class Path {
		public SortedList<Point2D> nodes;
		
		public Path recurse(Node n) {
			Node p = n.parent;
			if(p != null) {
				nodes.push(new Point2D(n.x, n.y));
				recurse(p);
			}
			return this;
		}
		
		public Path mergePaths(Path start, Path end) {
			this.nodes = start.nodes;
			this.nodes.addAll(end.nodes);
			return this;
		}
		
		public SortedList<Point2D> getCurrentPath() {
			return nodes;
		}
	}
	
	// An extension to the sorted list, with methods specifically for Nodes.
	public class NodeSortedList extends SortedList<Node> {
		private static final long serialVersionUID = 1L;
		
		public boolean contains(Node e) {
			for(Node n : this)
				if(n.x == e.x && n.y == e.y) return true;
			return false;
		}
		
		public int getIndex(Node n) {
			for(int i = 0; i < this.size(); i++) {
				Node e = this.get(i);
				if(n.x == e.x && n.y == e.y) return i;
			}
			return -1;
		}
		
		public void override(Node n) {
			int remIn = getIndex(n);
			if(remIn != -1) remove(remIn);
			add(n);
		}
	}
}