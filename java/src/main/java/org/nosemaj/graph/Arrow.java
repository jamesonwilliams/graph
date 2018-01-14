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

import java.util.Objects;
import java.util.List;

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
    private final Comparable weight;

    /**
     * Constructs a new Arrow.
     * @param source The source vertex
     * @param target The target vertex
     */
    public Arrow(final Vertex source, final Vertex target) {
        this(source, target, true);
    }

    /**
     * Constructs a new weighted Arrow.
     * @param source The source vertex
     * @param target The target vertex
     * @param weight The weight of the arrow
     */
    public Arrow(final Vertex source, final Vertex target,
            final Comparable weight) {

        Preconditions.notNull(source, "source must be non-null.");
        Preconditions.notNull(target, "target must be non-null.");
        Preconditions.notNull(weight, "weight must be non-null.");

        this.source = source;
        this.target = target;
        this.weight = weight;
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
    public Comparable weight() {
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
