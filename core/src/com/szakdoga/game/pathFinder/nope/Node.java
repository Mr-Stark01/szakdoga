package com.szakdoga.game.pathFinder.nope;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Node {

    private String name;
    private int X,Y;

    private List<Node> shortestPath = new LinkedList<>();

    private Integer distance = Integer.MAX_VALUE;

    Map<Node, Integer> adjacentNodes = new HashMap<>();



    public void addDestination(Node destination, int distance) {
        if(destination!=null) {
            adjacentNodes.put(destination, distance);
        }
    }

    public Node(int X, int Y) {
        this.X=X;
        this.Y=Y;
    }

    public Integer getDistance() {
        return distance;
    }

    public void setDistance(Integer distance) {
        this.distance = distance;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Node> getShortestPath() {
        return shortestPath;
    }

    public void setShortestPath(List<Node> shortestPath) {
        this.shortestPath = shortestPath;
    }

    public Map<Node, Integer> getAdjacentNodes() {
        return adjacentNodes;
    }

    public int getX() {
        return X;
    }


    public int getY() {
        return Y;
    }


    // getters and setters
}
