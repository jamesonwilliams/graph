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

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Arrow is an edge that distinguishes endpoints into source and
 * target, assuming a directional relationship between them.
 * Arrow does not require its endpoints to be unique.
 */
public final class Arrow implements Edge, Directed<Vertex, Vertex> {

    /**
     * The vertex from which the arrow originates.
     */
    private final Vertex source;

    /**
     * The vertex to which the arrow points.
     */
    private final Vertex target;

    /**
     * The weight of the arrow.
     */
    private final Optional<Comparable> weight;

    /**
     * Constructs a new weighted Arrow.
     * @param source The source vertex
     * @param target The target vertex
     * @param weight An optional weight of the arrow
     * @throws IllegalArgumentException If any argument is null
     */
    private Arrow(final Vertex source, final Vertex target,
            final Optional<Comparable> weight)
            throws IllegalArgumentException {

        Preconditions.notNull(source, "source must be non-null.");
        Preconditions.notNull(target, "target must be non-null.");

        this.source = source;
        this.target = target;
        this.weight = weight;
    }

    /**
     * Creates a new weighted Arrow.
     * @param source The source vertex
     * @param target The target vertex
     * @param weight The weight of the arrow
     * @return An arrow from source to target, and the given weight
     * @throws IllegalArgumentException If any argument is null
     */
    public static Arrow create(final Vertex source, final Vertex target,
            final Comparable weight) throws IllegalArgumentException {
        Preconditions.notNull(weight, "weight == null.");
        return new Arrow(source, target, Optional.of(weight));
    }

    /**
     * Creates a new unweighted Arrow.
     * @param source The source vertex
     * @param target The target vertex
     * @return An arrow from source to target
     * @throws IllegalArgumentException If any argument is null
     */
    public static Arrow create(final Vertex source, final Vertex target)
            throws IllegalArgumentException {
        return new Arrow(source, target, Optional.empty());
    }

    @Override
    public List<Vertex> endpoints() {
        return ImmutableList.of(source, target);
    }

    @Override
    public Vertex source() {
        return this.source;
    }

    @Override
    public Vertex target() {
        return this.target;
    }

    @Override
    public Optional<Comparable> weight() {
        return this.weight;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return Objects.hash(source, target, weight);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(final Object thatObject) {
        if (thatObject == null) {
            return false;
        }

        if (!this.getClass().equals(thatObject.getClass())) {
            return false;
        }

        final Arrow that = getClass().cast(thatObject);

        if (!Objects.equals(this.source(), that.source())) {
            return false;
        }

        if (!Objects.equals(this.target(), that.target())) {
            return false;
        }

        return Objects.equals(this.weight(), that.weight());
    }
}

