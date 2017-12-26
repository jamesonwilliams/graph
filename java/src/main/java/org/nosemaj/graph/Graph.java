/*
 * Copyright 2017 nosemaj.org
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package org.nosemaj.graph;

import java.util.Set;

/**
 * A graph is a set of vertices in conjunction with a set of edges which
 * connect them.
 */
public interface Graph {

    /**
     * Tests whether there is an edge between two vertices.
     * @param thisVertex An arbitrary vertex to check for adjacency
     * @param thatVertex An arbitrary vertex to check for adjacency
     * @return true if there if there is an edge between the two
     *         vertices, in this Graph; false, otherwise
     */
    boolean adjacent(Vertex thisVertex, Vertex thatVertex);

    /**
     * Gets the vertices which are directly connected to a given vertex
     * by an edge.
     * @param vertex An arbitary vertex
     * @return The set of vertexes which are connected to the provided
     *         vertex; possibly an empty set
     */
    Set<Vertex> neighbors(Vertex vertex);

    /**
     * Adds a vertex to the graph, if it's not already there.
     * @param vertex Vertex to add to the graph
     * @throws IllegalArgumentException
     *         if the vertex is already in the graph
     */
    void addVertex(Vertex vertex) throws IllegalArgumentException;

    /**
     * Removes a vertex from the graph, if it is in it.
     * @param vertex Vertex to remove from the graph
     * @throws IllegalArgumentException
     *         If the vertex was not in the graph
     */
    void removeVertex(Vertex vertex) throws IllegalArgumentException;

    /**
     * Adds an edge to the graph, if not already there.
     * @param edge Edge to add to graph
     * @throws IllegalArgumentException
     *         If the edge is already in the graph
     */
    void addEdge(Edge edge) throws IllegalArgumentException;

    /**
     * Removes and edge from the graph, if there.
     * @param edge Edge to remove from graph
     * @throws IllegalArgumentException
     *         If the edge wasn't in the graph originally
     */
    void removeEdge(Edge edge) throws IllegalArgumentException;
}
