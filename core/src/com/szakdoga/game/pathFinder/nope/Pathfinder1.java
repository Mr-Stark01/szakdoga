package com.szakdoga.game.pathFinder.nope;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

public class Pathfinder1 {
    private Graph graph = new Graph();
    private Node player1,player2;
    public Pathfinder1(TiledMap map){
        TiledMapTileLayer tileyLayer = (TiledMapTileLayer) map.getLayers().get(0);
        Node[][] nodes = new Node[tileyLayer.getWidth()][tileyLayer.getHeight()];
        System.out.println("width"+tileyLayer.getWidth());
        for (int i = 0; i < tileyLayer.getWidth(); i++) {
            for (int j = 0; j < tileyLayer.getHeight(); j++) {
                if (!tileyLayer.getCell(i, j).getTile().getProperties().containsKey("grass") || !tileyLayer.getCell(i, j).getTile().getProperties().containsKey("blocked")) {
                    nodes[i][j] = new Node(i, j);
                    if (tileyLayer.getCell(i, j).getTile().getProperties().get("CellPath")!=null && tileyLayer.getCell(i, j).getTile().getProperties().get("CellPath").equals("Client")) {//TODO modify it to player 1
                        player1 = nodes[i][j];
                    } else if (tileyLayer.getCell(i, j).getTile().getProperties().get("CellPath")!=null && tileyLayer.getCell(i, j).getTile().getProperties().get("CellPath").equals("Server")) {//TODO modify it to player 2
                        player2 = nodes[i][j];
                    }
                }
            }
        }
        for (int i = 1; i < tileyLayer.getWidth()-1; i++) {
            for (int j = 1; j < tileyLayer.getHeight()-1; j++) {
                nodes[i][j].addDestination(nodes[i+1][j],1);
                nodes[i][j].addDestination(nodes[i+1][j+1],2);
                nodes[i][j].addDestination(nodes[i][j+1],1);
                nodes[i][j].addDestination(nodes[i-1][j+1],2);
                nodes[i][j].addDestination(nodes[i-1][j],1);
                nodes[i][j].addDestination(nodes[i-1][j-1],2);
                nodes[i][j].addDestination(nodes[i][j-1],1);
                nodes[i][j].addDestination(nodes[i+1][j-1],2);
                graph.addNode(nodes[i][j]);
            }
        }
    }
    public void showPath(){

        Graph best=graph.calculateShortestPathFromSource(graph,player1);
        for(var nd:best.getNodes().toArray()){
            System.out.println(((Node) nd).getX()+"\t"+((Node) nd).getY());
        }/*
        for(Node tmp:best.getNodes()){
            System.out.println(tmp);
            for(Node node:tmp.getShortestPath()){
                System.out.println("asádwásé");
                System.out.println(
                "X:"+node.getX()+"\t"+
                "Y"+node.getY());
            }
        }*/

    }
}
