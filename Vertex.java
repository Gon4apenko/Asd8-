package com.company;

import java.util.ArrayList;
import java.util.Iterator;


class Vertex implements Iterable<Integer>{
    private boolean visited;
    private ArrayList<Integer> neighbours;

    public Vertex(){
        this.visited = false;
        this.neighbours = new ArrayList<>();
    }

    public Vertex(Vertex rhs){
        this.visited = rhs.visited;
        this.neighbours = new ArrayList<>(rhs.neighbours);
    }

    public boolean isVisited(){
        return visited;
    }

    public void removeNeighbour(int v){
        this.neighbours.remove(v);
    }

    public int noOfNeighbours(){
        return this.neighbours.size();
    }

    public boolean isNeighbour(int v){
        return this.neighbours.contains(v);
    }

    public void addNeighbour(int v){
        this.neighbours.add(v);
    }

    public void append(Vertex other){
        for(int i : other){
            this.neighbours.add(i);
        }
    }

    public void removeAll(int v){
        this.neighbours.removeIf(x -> x == v);
    }

    public void replaceAll(int v1, int v2){
        this.neighbours.replaceAll(x -> { if(x == v2) return v1; else return x; });
    }

    public void setVisited(boolean b){
        this.visited = b;
    }

    @Override
    public Iterator<Integer> iterator(){
        return this.neighbours.iterator();
    }
}
