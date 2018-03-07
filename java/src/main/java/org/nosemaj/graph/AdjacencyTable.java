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

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 * AdjacencyTable is a Graph which is implemented by means of
 * maintaining a table of the adjacencies that exist between vertices.
 */
public final class AdjacencyTable implements Graph {

    /**
     * For each vertex, a table of its adjacent vertices and the weights
     * to reach them.
     */
    private final Map<Vertex, Map<Vertex, Optional<Comparable>>> neighbors;

    /**
     * Constructs a new AdjacencyTable from a neighbors table.
     * @param neighbors A table of all vertices' neighboring vertices
     */
    AdjacencyTable(
            final Map<Vertex, Map<Vertex, Optional<Comparable>>> neighbors) {

        Preconditions.notNull(neighbors, "neighbors == null");
        this.neighbors = neighbors;
    }

    /**
     * Creates a new AdjacencyTable.
     * @return A new adjacencty table
     */
    public static AdjacencyTable create() {
        return new AdjacencyTable(new HashMap<>());
    }

    @Override
    public Set<Vertex> neighbors(final Vertex vertex) {
        Preconditions.notNull(vertex, "vertex == null");
        Preconditions.isTrue(contains(vertex),
                "vertex does not exist in graph.");

        return Collections.unmodifiableSet(neighbors.get(vertex).keySet());
    }

    @Override
    public Map<Vertex, Optional<Comparable>> weights(final Vertex vertex) {
        return Collections.unmodifiableMap(neighbors.get(vertex));
    }

    @Override
    public void add(final Vertex vertex) throws IllegalArgumentException {
        Preconditions.notNull(vertex, "vertex == null");
        Preconditions.isFalse(contains(vertex), "vertex is already in graph.");

        neighbors.put(vertex, new HashMap<>());
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
        for (Map<Vertex, Optional<Comparable>> values : neighbors.values()) {
            if (values.containsKey(vertex)) {
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
    public boolean containsAll(final Collection<Vertex> vertices) {
        Preconditions.notNull(vertices, "vertices == null");
        return vertices().containsAll(vertices);
    }

    @Override
    public Set<Vertex> vertices() {
        return Collections.unmodifiableSet(neighbors.keySet());
    }

    @Override
    public void add(final Edge edge) throws IllegalArgumentException {
        Preconditions.notNull(edge, "edge == null");
        Preconditions.isTrue(containsAll(edge.endpoints()),
                "Endpoint(s) not in graph.");

        Vertex vertex = edge.endpoints().get(0);
        Vertex adjacent = edge.endpoints().get(1);

        // Don't allow multigraphs; neighbors table wouldn't immediately
        // support this.
        Preconditions.isFalse(neighbors.get(vertex).containsKey(adjacent),
                "Relationship already exists between vertices.");

        neighbors.get(vertex).put(adjacent, edge.weight());
        if (edge instanceof Line) {
            neighbors.get(adjacent).put(vertex, edge.weight());
        }
    }

    @Override
    public void remove(final Edge edge) throws IllegalArgumentException {
        Preconditions.notNull(edge, "edge == null");
        Preconditions.isTrue(contains(edge), "Edge not in graph.");

        Vertex vertex = edge.endpoints().get(0);
        Vertex adjacent = edge.endpoints().get(1);

        neighbors.get(vertex).remove(adjacent);
        if (edge instanceof Line) {
            neighbors.get(adjacent).remove(vertex);
        }
    }

    @Override
    public boolean contains(final Edge edge) {
        Preconditions.notNull(edge, "edge == null");
        Preconditions.isTrue(containsAll(edge.endpoints()),
                "Edge endpoint(s) not in graph.");

        Vertex vertex = edge.endpoints().get(0);
        Vertex adjacent = edge.endpoints().get(1);

        if (edge instanceof Line) {
            if (!hasNeighborEntry(adjacent, vertex, edge.weight())) {
                return false;
            }
        }

        return hasNeighborEntry(vertex, adjacent, edge.weight());
    }

    /**
     * Checks if the neighbors table has an entry from the given vertex,
     * to the given adjacent vertex, with the given weight.
     * @param vertex Key in the neighbors table, known to exist
     * @param adjacent Key into the values map returned for vertex,
     *                 known to exist in the neighbors table
     * @param weight Weight expected for the value associated with
     *               adjacent
     * @return true if the neigbors table has an entry matching the
     *         provided criteria; false, otherwise
     */
    private boolean hasNeighborEntry(final Vertex vertex,
            final Vertex adjacent, final Optional<Comparable> weight) {

        if (!neighbors.get(vertex).containsKey(adjacent)) {
            return false;
        }

        return neighbors.get(vertex).get(adjacent).equals(weight);
    }
}

