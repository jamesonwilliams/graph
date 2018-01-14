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

import org.nosemaj.graph.util.Preconditions;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * AdjacencyTable is a Graph which is implemented by means of
 * maintaining an table of adjencies that exist between vertices.
 */
public final class AdjacencyTable implements Graph {

    /**
     * Map of neighbors for all vertices.
     */
    private final Map<Vertex, Set<Vertex>> neighbors;

    /**
     * Constructs a new AdjacencyTableGraph from an neighbors table.
     * @param neighbors A table of all vertices' neighboring vertices
     */
    AdjacencyTable(final Map<Vertex, Set<Vertex>> neighbors) {
        Preconditions.notNull(neighbors, "neighbors == null");
        this.neighbors = neighbors;
    }

    /**
     * Creates a new AdjacencyTable.
     * @return A new adjacencty table
     */
    public static AdjacencyTable create() {
        return new AdjacencyTable(new HashMap<Vertex, Set<Vertex>>());
    }

    @Override
    public Set<Vertex> neighbors(final Vertex vertex) {
        Preconditions.notNull(vertex, "vertex == null");
        Preconditions.isTrue(contains(vertex),
                "vertex does not exist in graph.");

        return neighbors.get(vertex);
    }

    @Override
    public void add(final Vertex vertex) throws IllegalArgumentException {
        Preconditions.notNull(vertex, "vertex == null");
        Preconditions.isFalse(contains(vertex), "vertex is already in graph.");

        neighbors.put(vertex, new HashSet<>());
    }

    @Override
    public void remove(final Vertex vertex) throws IllegalArgumentException {
        Preconditions.notNull(vertex, "vertex == null.");
        Preconditions.isTrue(contains(vertex), "vertex not in graph.");

        // Cheap operation to remove the forward mapping for the vertex
        neighbors.remove(vertex);

        /*
         * An O(V) operation is needed to remove all references to the
         * vertex in the value set of the adjacency table. Lookup in the
         * neighbors list is O(1), but we may perform up that up to as
         * many times as there are vertices in the graph.
         */
        for (Set<Vertex> values : neighbors.values()) {
            if (values.contains(vertex)) {
                values.remove(vertex);
            }
        }
    }

    @Override
    public boolean contains(final Vertex vertex) {
        Preconditions.notNull(vertex, "vertex == null");
        return vertices().contains(vertex);
    }

    @Override
    public Set<Vertex> vertices() {
        return neighbors.keySet();
    }

    @Override
    public void add(final Edge edge) throws IllegalArgumentException {
        Preconditions.notNull(edge, "edge == null");
        Preconditions.isNull(edge.weight(), "weighted edges not supported.");

        List<Vertex> vertices = edge.endpoints();
        addAdjacency(vertices.get(0), vertices.get(1));
        if (edge instanceof Line) {
            addAdjacency(vertices.get(1), vertices.get(0));
        }
    }

    /**
     * Adds an adjacency entry to the neighbors table.
     * @param vertex A vertex
     * @param adjacent A vertex which is adjacent to vertex
     * @throws IllegalArgumentException
     *         If the relationship already exists, or if one of the
     *         provided vertices is not known to the graph
     */
    private void addAdjacency(final Vertex vertex, final Vertex adjacent)
            throws IllegalArgumentException {

        Preconditions.isTrue(contains(vertex), "Endpoint(s) not in graph.");
        Preconditions.isTrue(contains(adjacent), "Endpoint(s) not in graph.");
        Preconditions.isFalse(neighbors(vertex).contains(adjacent),
                "Edge exists in graph.");

        neighbors(vertex).add(adjacent);
    }

    @Override
    public void remove(final Edge edge) throws IllegalArgumentException {
        Preconditions.notNull(edge, "edge == null");

        List<Vertex> vertices = edge.endpoints();
        removeAdjacency(vertices.get(0), vertices.get(1));
        if (edge instanceof Line) {
            removeAdjacency(vertices.get(1), vertices.get(0));
        }
    }

    /**
     * Removes an adjacency from the neighbors table.
     * @param vertex The vertex from which an adjacency exists
     * @param adjacent The node adjacent to the vertex
     * @throws IllegalArgumentException
     *         If there was no such adjacency in the table, or if either
     *         vertex is not known to the graph
     */
    private void removeAdjacency(final Vertex vertex, final Vertex adjacent)
            throws IllegalArgumentException {

        Preconditions.isTrue(contains(vertex), "Endpoints not in graph.");
        Preconditions.isTrue(contains(adjacent), "Endpoints not in graph.");
        Preconditions.isTrue(neighbors(vertex).contains(adjacent),
                "Edge not in graph.");

        neighbors(vertex).remove(adjacent);
    }

    @Override
    public boolean contains(final Edge edge) {
        Preconditions.notNull(edge, "edge == null");

        List<Vertex> vertices = edge.endpoints();
        Preconditions.isTrue(contains(vertices.get(0)),
                "Edge endpoint(s) not in graph.");
        Preconditions.isTrue(contains(vertices.get(1)),
                "Edge endpoint(s) not in graph.");

        if (edge instanceof Line) {
            if (!neighbors(vertices.get(1)).contains(vertices.get(0))) {
                return false;
            }
        }

        return neighbors(vertices.get(0)).contains(vertices.get(1));
    }
}
