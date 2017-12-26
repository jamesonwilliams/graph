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

import java.util.Objects;

/**
 * WeightedDirectedEdge is an edge which is both weighted and
 * directional.
 * @param <W> A comparable type for the weight
 */
public class WeightedDirectedEdge<W extends Comparable<W>>
        extends DirectedEdge implements Weighted<W> {

    /**
     * The weight of the edge.
     */
    private final W weight;

    /**
     * Constructs a new weighted directed edge.
     * @param source The source vertex
     * @param target The target vertex
     * @param weight The weight of the edge
     */
    public WeightedDirectedEdge(final Vertex source, final Vertex target,
            final W weight) {

        super(source, target);
        Preconditions.notNull(weight, "weight must be non-null.");
        this.weight = weight;
    }

    @Override
    public final W weight() {
        return this.weight;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return Objects.hash(weight(), super.hashCode());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(final Object thatObject) {
        if (thatObject == null || !getClass().equals(thatObject.getClass())) {
            return false;
        }

        if (!super.equals(thatObject)) {
            return false;
        }

        return Objects.equals(weight(),
            getClass().cast(thatObject).weight());
    }
}
