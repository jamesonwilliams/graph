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

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Random;

/**
 * Tests the Vertex class.
 */
public final class VertexTest {

    /**
     * A random number generator used to construct arbitrary vertex
     * values.
     */
    private Random random;

    /**
     * Sets up dependencies needed for the test.
     */
    @Before
    public void setup() {
        random = new Random();
    }

    /**
     * The {@link Vertex(T)} constructor should result in a vertex
     * that stores the expected value.
     */
    @Test
    public void constructorShouldReturnValidVertex() {
        Float value = random.nextFloat();

        Vertex<Float> vertex = new Vertex<>(value);

        Assert.assertEquals(value, vertex.value(), 0f);
    }

    /**
     * A vertex should be equal to itself.
     */
    @Test
    public void equalsShouldReturnTrueWhenVertexIsSelf() {
        Vertex<Boolean> vertex = new Vertex<>(random.nextBoolean());

        Assert.assertEquals(vertex, vertex);
    }

    /**
     * A vertex must not be equal to another vertex if they have
     * different values.
     */
    @Test
    public void equalsShouldReturnFalseWhenVertexHasDifferentValue() {
        Boolean value = random.nextBoolean();
        Vertex<Boolean> vertex = new Vertex<>(value);
        Vertex<Boolean> different = new Vertex<>(!value);

        Assert.assertNotEquals(vertex, different);
    }

    /**
     * A vertex should be equal to another vertex provided that the
     * store the same value.
     */
    @Test
    public void equalsShouldReturnTrueWhenVertexHasSameValue() {
        Long value = random.nextLong();
        Vertex<Long> vertex = new Vertex<>(value);
        Vertex<Long> other = new Vertex<>(value);

        Assert.assertEquals(vertex, other);
    }

    /**
     * A vertex is never equal to an object that is not a vertex.
     */
    @Test
    public void equalsShouldReturnFalseWhenArgumentIsDifferentObjectClass() {
        double value = random.nextDouble();
        Vertex<Double> vertex = new Vertex<>(value);
        Double other = Double.valueOf(value);

        Assert.assertNotEquals(vertex, other);
    }

    /**
     * A vertex is never equal to a vertex of a different template type.
     */
    @Test
    public void equalsShouldReturnFalseWhenVertexUsesDifferentTemplate() {
        int value = random.nextInt();
        Vertex<Integer> vertex = new Vertex<>(value);
        Vertex<Long> other = new Vertex<>((long) value);

        Assert.assertNotEquals(vertex, other);
    }

    /**
     * An vertex that exists is never equal to a vertex that does not
     * exist.
     */
    @Test
    public void equalsShouldReturnFalseWhenArgumentIsNull() {
        Vertex<Object> vertex = new Vertex<>(new Object());
        Assert.assertNotEquals(vertex, null);
    }

    /**
     * Vertices with different values should have different hash codes.
     */
    @Test
    public void hashCodeShouldReturnDistinctHashesForDistinctVertices() {

        Vertex<Object> one = new Vertex<>(new Object());
        Vertex<Object> two = new Vertex<>(new Object());

        Assert.assertNotEquals(one.hashCode(), two.hashCode());
    }

    /**
     * Vertices that store the same value should have the same hash
     * codes.
     */
    @Test
    public void hashCodeShouldReturnSameHashesForSameVertices() {
        Object sameValue = new Object();
        Vertex<Object> one = new Vertex<>(sameValue);
        Vertex<Object> two = new Vertex<>(sameValue);

        Assert.assertEquals(one.hashCode(), two.hashCode());
    }
}
