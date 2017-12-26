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

/**
 * AdjacencyMap is a mapping for each vertex v in a graph, to the
 * collection of vertices to which v is adjacent.
 *
 * This is an externalized Adjacency List. Instead of storing a list of
 * adjacent vertices directly in the Vertex class, we externalized this,
 * to better decouple potential implementations. For example, you may
 * like to host your adjacency map in a different system than that which
 * hosts your vertices.
 *
 * @param <C> The type of collection used to store adjacent vertices
 */
public interface AdjacencyMap<C extends Collection<Vertex>> {

    /**
     * Gets a collection of vertices which are adjacent to a given
     * vertiex.
     * @param vertex The vertex for which adjacent vertices are sought
     * @return The collection of vertices that are adjacent to vertex
     */
    C adjacent(Vertex vertex);
}
