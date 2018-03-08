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

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 * A graph is a set of vertices in conjunction with a set of edges which
 * connect them.
 */
public interface Graph {

    /**
     * Gets the vertices which are directly connected to a given vertex
     * by an edge.
     * @param vertex An arbitary vertex
     * @return The set of vertexes which are connected to the provided
     *         vertex; possibly an empty set
     * @throws IllegalArgumentException
     *         If vertex is not known to the graph
     */
    Set<Vertex> neighbors(Vertex vertex) throws IllegalArgumentException;

    /**
     * Gets the weights associated with each of the neighbors of a
     * vertex.
     * @param vertex An arbitrary vertex
     * @return A map from each neighbor to the weight between the vertex
     *         and that neighbor
     * @throws IllegalArgumentException
     *         If vertex is not known to the graph
     */
    Map<Vertex, Optional<Weight>> weights(Vertex vertex)
            throws IllegalArgumentException;

    /**
     * Adds a vertex to the graph, if it's not already there.
     * @param vertex Vertex to add to the graph
     * @throws IllegalArgumentException
     *         if the vertex is already in the graph
     */
    void add(Vertex vertex) throws IllegalArgumentException;

    /**
     * Removes a vertex from the graph, if it is in it.
     * @param vertex Vertex to remove from the graph
     * @throws IllegalArgumentException
     *         If the vertex was not in the graph
     */
    void remove(Vertex vertex) throws IllegalArgumentException;

    /**
     * Checks if a vertex is in the graph.
     * @param vertex A vertex that may be in the graph
     * @return true if the vertex is in the graph; false, otherwise
     * @throws IllegalArgumentException if vertex is null
     */
    boolean contains(Vertex vertex) throws IllegalArgumentException;

    /**
     * Checks if a collection of vertices are all in the graph.
     * @param vertices Vertices which may or may not be in the graph.
     * @return true if all of the vertices are in the graph; false,
     *         otherwise
     * @throws IllegalArgumentException if vertices is null
     */
    boolean containsAll(Collection<Vertex> vertices)
            throws IllegalArgumentException;

    /**
     * Gets the vertex set.
     * @return The vertex set
     */
    Set<Vertex> vertices();

    /**
     * Adds an edge to the graph, if not already there.
     * @param edge Edge to add to graph
     * @throws IllegalArgumentException
     *         If the edge is already in the graph
     */
    void add(Edge edge) throws IllegalArgumentException;

    /**
     * Removes and edge from the graph, if there.
     * @param edge Edge to remove from graph
     * @throws IllegalArgumentException
     *         If the edge wasn't in the graph originally
     */
    void remove(Edge edge) throws IllegalArgumentException;

    /**
     * Checks whether an edge exists in the graph.
     * @param edge An edge
     * @return true if edge exists in graph; false, otherwise
     * @throws IllegalArgumentException
     *         If edge is null, or if either endpoints of edge is not
     *         known to the graph
     */
    boolean contains(Edge edge) throws IllegalArgumentException;
}

