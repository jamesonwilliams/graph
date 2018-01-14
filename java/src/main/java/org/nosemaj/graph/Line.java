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
import org.nosemaj.graph.util.ImmutableList;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * Line is an undirected edge, defined by a simple set of two endpoints.
 * There is no distinction between the two endpoints, but the endpoints
 * must be unique. A line may have a weight; by default the weight is
 * the Boolean value true, indicating that the line exists.
 */
public final class Line implements Edge {

    /**
     * The set of endpoints that comprise the edge.
     */
    private final Set<Vertex> endpoints;

    /**
     * The weight of the line.
     */
    private final Comparable weight;

    /**
     * Constructs a Line.
     * @param first The first of the two (not necessarily distinct)
     *              endpoints in the line
     * @param second The second of the two (not necessarily distinct)
     *               endpoints in the line
     */
    public Line(final Vertex first, final Vertex second) {
        this(first, second, true);
    }

    /**
     * Constructs a new weighted Line.
     * @param first The first of the two (not necessarily distinct)
     *              endpoints in the line
     * @param second The second of the two (not necessarily distinct)
     *               endpoints in the line
     * @param weight The weight of the line
     */
    public Line(final Vertex first, final Vertex second,
            final Comparable weight) {

        Preconditions.notNull(first, "first vertex must be non-null.");
        Preconditions.notNull(second, "second vertex must be non-null.");
        Preconditions.notEqual(first, second,
            "first and second must be distinct.");

        this.endpoints = new HashSet<>();
        this.endpoints.add(first);
        this.endpoints.add(second);

        Preconditions.notNull(weight, "weight must be non-null.");
        this.weight = weight;
    }

    @Override
    public List<Vertex> endpoints() {
        return ImmutableList.of(endpoints);
    }

    @Override
    public Comparable weight() {
        return weight;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return Objects.hash(endpoints, weight);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(final Object thatObject) {
        if (thatObject == null) {
            return false;
        }

        if (!getClass().equals(thatObject.getClass())) {
            return false;
        }

        Line that = getClass().cast(thatObject);

        if (!this.endpoints().containsAll(that.endpoints())) {
            return false;
        }

        return Objects.equals(this.weight(), that.weight());
    }
}
