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
import java.util.Optional;
import java.util.Set;

/**
 * Line is an undirected edge, defined by a simple set of two endpoints.
 * There is no distinction between the two endpoints, but the endpoints
 * must be unique. A line may have a weight; by default the optional
 * weight is not present.
 */
public final class Line implements Edge {

    /**
     * The set of endpoints that comprise the edge.
     */
    private final Set<Vertex> endpoints;

    /**
     * The weight of the line.
     */
    private final Optional<Comparable> weight;

    /**
     * Constructs a new weighted Line.
     * @param first The first of the two (not necessarily distinct)
     *              endpoints in the line
     * @param second The second of the two (not necessarily distinct)
     *               endpoints in the line
     * @param weight An optional weight for the line
     * @throws IllegalArgumentException
     *         If either vertex is null, or if they are equal
     */
    private Line(final Vertex first, final Vertex second,
            final Optional<Comparable> weight) {

        Preconditions.notNull(first, "first vertex must be non-null.");
        Preconditions.notNull(second, "second vertex must be non-null.");
        Preconditions.notEqual(first, second,
            "first and second must be distinct.");

        this.endpoints = new HashSet<>();
        this.endpoints.add(first);
        this.endpoints.add(second);
        this.weight = weight;
    }

    /**
     * Creates an unweighted line between two endpoints.
     * @param first The first of the two (not necessarily distinct)
     *              endpoints in the line
     * @param second The second of the two (not necessarily distinct)
     *               endpoints in the line
     * @return A new line which connects the given vertices
     * @throws IllegalArgumentException
     *         If either vertex is null, or if they are equal
     */
    public static Line create(final Vertex first, final Vertex second)
            throws IllegalArgumentException {
        return new Line(first, second, Optional.empty());
    }

    /**
     * Creates a weighted line between two endpoints.
     * @param first The first of the two (not necessarily distinct)
     *              endpoints in the line
     * @param second The second of the two (not necessarily distinct)
     *               endpoints in the line
     * @param weight The weight of the line
     * @return A new line which connects the given vertices
     * @throws IllegalArgumentException
     *         If any argument is null, or if vertices are equal
     */
    public static Line create(final Vertex first, final Vertex second,
            final Comparable weight) throws IllegalArgumentException {
        Preconditions.notNull(weight, "weight == null");
        return new Line(first, second, Optional.of(weight));
    }

    @Override
    public List<Vertex> endpoints() {
        return ImmutableList.of(endpoints);
    }

    @Override
    public Optional<Comparable> weight() {
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

