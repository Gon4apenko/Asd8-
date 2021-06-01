package com.company;

public interface Graph {
    int numberOfEdges();
    int size();
    boolean edgeExists(int a, int b);
    boolean hasVertex(int v);
    void removeEdge(int a, int b);
    void addEdge(int a, int b);
}
