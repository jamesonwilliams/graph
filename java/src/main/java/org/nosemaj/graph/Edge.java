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
 * Edges are connections between two vertices.
 *
 * @param <C> The type of collection which is used to store the two
 *            edges; in general, an edge does not prescribe properties
 *            of this collection (ordered, not) however implementations
 *            may.
 */
interface Edge<C extends Collection<Vertex>> {

    /**
     * Gets the vertices that comprise the edge.
     * @return The vertices that comprise the edge
     */
    C vertices();
}
