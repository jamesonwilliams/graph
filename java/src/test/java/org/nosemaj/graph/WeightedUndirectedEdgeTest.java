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
 * Tests the WeightedUndirectedEdge class.
 *
 * All that is in scope for these tests is to verify the functionality
 * of the additional weight component, which is added above
 * UndirectedEdge. UndirectedEdge functionalities are tested separately.
 */
public final class WeightedUndirectedEdgeTest {

    /**
     * An arbitrary vertex.
     */
    private Vertex<Long> first;

    /**
     * An arbitrary vertex.
     */
    private Vertex<Integer> second;

    /**
     * Specific values are unimportant for test, so fill them out
     * randomly.
     */
    private Random random;

    /**
     * Sets up dependencies needed for the test.
     */
    @Before
    public void setup() {
        random = new Random();

        first = new Vertex<>(random.nextLong());
        second = new Vertex<>(random.nextInt());
    }

    /**
     * The constructor should refuse to construct if it is passed a null
     * value for the weight.
     */
    @Test(expected = IllegalArgumentException.class)
    public void constructorShouldThrowExceptionOnNullWeight() {
        new WeightedUndirectedEdge<Long>(first, second, null);
    }

    /**
     * Asking for the weight should return the same value that was
     * passed into the constructor.
     */
    @Test
    public void weightShouldReturnValueFromConstructor() {
        Boolean weight = random.nextBoolean();

        Weighted<Boolean> weighted =
            new WeightedUndirectedEdge<>(first, second, weight);

        Assert.assertEquals(weight, weighted.weight());
    }

    /**
     * Two edges that have the same weights and the same vertices should
     * have the same ahs code.
     */
    @Test
    public void hashCodeShouldReturnSameCodeWhenSameWeightsAndVertices() {
        Vertex<Long> firstVertex = new Vertex<>(random.nextLong());
        Vertex<Long> secondVertex = new Vertex<>(random.nextLong());
        Boolean weight = random.nextBoolean();

        WeightedUndirectedEdge<Boolean> firstEdge =
            new WeightedUndirectedEdge<>(firstVertex, secondVertex, weight);

        WeightedUndirectedEdge<Boolean> secondEdge =
            new WeightedUndirectedEdge<>(firstVertex, secondVertex, weight);

        Assert.assertEquals(firstEdge.hashCode(), secondEdge.hashCode());
    }

    /**
     * Two edges that describe different vertcies should not have the
     * same hash code.
     */
    @Test
    public void hashCodeShouldReturnDifferentCodeWhenDifferentVertices() {
        Vertex<Long> firstVertex = new Vertex<>(random.nextLong());
        Vertex<Long> secondVertex = new Vertex<>(random.nextLong());
        Vertex<Long> thirdVertex = new Vertex<>(random.nextLong());
        Boolean weight = random.nextBoolean();

        WeightedUndirectedEdge<Boolean> firstEdge =
            new WeightedUndirectedEdge<>(firstVertex, secondVertex, weight);

        WeightedUndirectedEdge<Boolean> secondEdge =
            new WeightedUndirectedEdge<>(firstVertex, thirdVertex, weight);

        Assert.assertNotEquals(firstEdge.hashCode(), secondEdge.hashCode());
    }

    /**
     * Two edges with different weights should have different hash
     * codes.
     */
    @Test
    public void hashCodeShouldReturnDifferentCodeWhenDifferentWeights() {
        Vertex<Long> firstVertex = new Vertex<>(random.nextLong());
        Vertex<Long> secondVertex = new Vertex<>(random.nextLong());
        Boolean weight = random.nextBoolean();

        WeightedUndirectedEdge<Boolean> firstEdge =
            new WeightedUndirectedEdge<>(firstVertex, secondVertex, weight);

        WeightedUndirectedEdge<Boolean> secondEdge =
            new WeightedUndirectedEdge<>(firstVertex, secondVertex, !weight);

        Assert.assertNotEquals(firstEdge.hashCode(), secondEdge.hashCode());
    }

    /**
     * Two edges should be equal when they have the same vertices and
     * weight.
     */
    @Test
    public void equalsShouldReturnTrueWhenWeightsAndVerticesAreEqual() {
        Vertex<Long> firstVertex = new Vertex<>(random.nextLong());
        Vertex<Long> secondVertex = new Vertex<>(random.nextLong());
        Boolean weight = random.nextBoolean();

        WeightedUndirectedEdge<Boolean> firstEdge =
            new WeightedUndirectedEdge<>(firstVertex, secondVertex, weight);

        WeightedUndirectedEdge<Boolean> secondEdge =
            new WeightedUndirectedEdge<>(firstVertex, secondVertex, weight);

        Assert.assertEquals(firstEdge, secondEdge);
    }

    /**
     * Two edges should NOT be equal when they have the same vertices
     * but different weights.
     */
    @Test
    public void equalsShouldReturnFalseWhenWeightsDiffer() {
        Vertex<Long> firstVertex = new Vertex<>(random.nextLong());
        Vertex<Long> secondVertex = new Vertex<>(random.nextLong());
        Boolean weight = random.nextBoolean();

        WeightedUndirectedEdge<Boolean> firstEdge =
            new WeightedUndirectedEdge<>(firstVertex, secondVertex, weight);

        WeightedUndirectedEdge<Boolean> secondEdge =
            new WeightedUndirectedEdge<>(firstVertex, secondVertex, !weight);

        Assert.assertNotEquals(firstEdge, secondEdge);
    }

    /**
     * Two edges should NOT be equals when they have the same weights
     * but different vertices.
     */
    @Test
    public void equalsShouldReturnFalseWhenVerticesDiffer() {
        Vertex<Long> firstVertex = new Vertex<>(random.nextLong());
        Vertex<Long> secondVertex = new Vertex<>(random.nextLong());
        Vertex<Long> thirdVertex = new Vertex<>(random.nextLong());
        Boolean weight = random.nextBoolean();

        WeightedUndirectedEdge<Boolean> firstEdge =
            new WeightedUndirectedEdge<>(firstVertex, secondVertex, weight);

        WeightedUndirectedEdge<Boolean> secondEdge =
            new WeightedUndirectedEdge<>(firstVertex, thirdVertex, weight);

        Assert.assertNotEquals(firstEdge, secondEdge);
    }

    /**
     * An edge is never equal to something that is not an edge.
     */
    @Test
    public void equalsShouldReturnFalseWhenArgIsNotAnEdge() {
        Vertex<Long> firstVertex = new Vertex<>(random.nextLong());
        Vertex<Long> secondVertex = new Vertex<>(random.nextLong());
        Boolean weight = random.nextBoolean();

        WeightedUndirectedEdge<Boolean> firstEdge =
            new WeightedUndirectedEdge<>(firstVertex, secondVertex, weight);

        Assert.assertNotEquals(firstEdge, weight);
    }

    /**
     * An edge is never equal to something that is not an edge.
     */
    @Test
    public void equalsShouldReturnFalseWhenArgIsNull() {
        Vertex<Long> firstVertex = new Vertex<>(random.nextLong());
        Vertex<Long> secondVertex = new Vertex<>(random.nextLong());
        Boolean weight = random.nextBoolean();

        WeightedUndirectedEdge<Boolean> firstEdge =
            new WeightedUndirectedEdge<>(firstVertex, secondVertex, weight);

        Assert.assertNotEquals(firstEdge, null);
    }
}
