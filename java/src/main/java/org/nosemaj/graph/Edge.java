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

import java.util.List;
import java.util.Optional;

/**
 * Edges are connections between two vertices.
 *
 * Note: the collection of endpoints in an edge is not strictly speaking
 * a list, however, this is the only sane way to access individual
 * vertices without imposing methods with semantic baggage ("first",
 * "source", etc.)
 */
interface Edge {

    /**
     * Gets the vertices on either end of the edge.
     * @return The vertices that comprise the edge
     */
    List<Vertex> endpoints();

    /**
     * Gets the weight of the edge; may not be present.
     * @return An optional weight value for the edge
     */
    Optional<Comparable> weight();
}

