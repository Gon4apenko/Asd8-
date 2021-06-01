package com.company;

import java.util.*;

public abstract class UndirectedGraph implements Graph {
    private boolean visited[];
    private int v;
    private int e;
    private Map<Integer, Vertex> adjMap;

    public UndirectedGraph(int n) {
        this.v = n;
        this.adjMap = new HashMap<>();
        for (int i = 1; i <= v; ++i) {
            adjMap.put(i, new Vertex());
        }
    }

    public UndirectedGraph(UndirectedGraph rhs) {
        this.v = rhs.v;
        this.e = rhs.e;
        this.adjMap = new HashMap<>();
        for (Map.Entry<Integer, Vertex> entry : rhs.adjMap.entrySet()) {
            this.adjMap.put(entry.getKey(), new Vertex(entry.getValue()));
        }
    }

    public UndirectedGraph(java.util.Scanner sc) {
        this.v = sc.nextInt();
        this.adjMap = new HashMap<>();
        System.out.print(sc.nextLine());

        while (sc.hasNext()) {
            String input = sc.nextLine();
            StringTokenizer strToken = new StringTokenizer(input);
            int count = strToken.countTokens();

            int[] arr = new int[count];
            for (int i = 0; i < count; ++i) {
                arr[i] = Integer.parseInt(strToken.nextToken());
            }

            adjMap.put(arr[0], new Vertex());
            for (int i = 1; i < count; ++i) {
                adjMap.get(arr[0]).addNeighbour(arr[i]);
            }
        }
        e = edges();
    }

    class NoSuchVertexException extends RuntimeException {
        public NoSuchVertexException(String no_such_vertex) {
            super(no_such_vertex);
        }
    }

    class BadEdgeException extends RuntimeException {
        public BadEdgeException(String bad_edge) {
            super(bad_edge);
        }
    }

    public void addEdge(int v1, int v2) {
        if (!hasVertex(v1) || !hasVertex(v2))
            throw new NoSuchVertexException("No such vertex!");
        adjMap.get(v1).addNeighbour(v2);
        adjMap.get(v2).addNeighbour(v1);
    }

    @Override
    public void removeEdge(int v1, int v2) {
        if (!edgeExists(v1, v2))
            throw new BadEdgeException("Such an edge does not exist!");
        else {
            adjMap.get(v1).removeNeighbour(v2);
            adjMap.get(v2).removeNeighbour(v1);
        }
    }

    private int edges() {
        int total = 0;
        for (Vertex v : adjMap.values()) {
            total += v.noOfNeighbours();
        }
        return total / 2;
    }

    @Override
    public int numberOfEdges() {
        return e;
    }

    @Override
    public int size() {
        return v;
    }

    @Override
    public boolean edgeExists(int v1, int v2) {
        return adjMap.containsKey(v1) && adjMap.containsKey(v2) &&
                adjMap.get(v1).isNeighbour(v2) &&
                adjMap.get(v2).isNeighbour(v1);
    }

    @Override
    public boolean hasVertex(int v) {
        return false;
    }

    public boolean hasVertex(String v) {
        return adjMap.containsKey(v);
    }

    private void contractEdge(int v1, int v2) {
        if (!hasVertex(v1) || !hasVertex(v2))
            throw new NoSuchVertexException("Invalid vertex!");
        else if (!edgeExists(v1, v2))
            throw new BadEdgeException("Such an edge does not exist!");
        else {
            adjMap.get(v1).removeAll(v2);
            adjMap.get(v2).removeAll(v1);

            adjMap.get(v1).append(adjMap.get(v2));

            adjMap.remove(v2);

            for (Vertex vr : adjMap.values()) {
                vr.replaceAll(v1, v2);
            }

            //update v and e
            v = adjMap.size();
            e = edges();
        }
    }

    public int Cut() {
        UndirectedGraph copy = new UndirectedGraph(this) {
            @Override
            public void print() {

            }
        };
        while (copy.size() > 2) {
            int v1, v2;
            do {
                ArrayList<Integer> keys = new ArrayList<>(copy.adjMap.keySet());
                Random r = new Random();
                v1 = keys.get(r.nextInt(copy.size()));
                v2 = keys.get(r.nextInt(copy.size()));
            }
            while (!copy.edgeExists(v1, v2));

            copy.contractEdge(v1, v2);
        }
        return copy.numberOfEdges();
    }

    public int minCut() {
        int answer = this.numberOfEdges();
        System.out.println(this.size());
        for (int i = 0; i < this.size() * this.size() * 2; ++i)
            answer = Math.min(answer, this.Cut());
        return answer;
    }
}
