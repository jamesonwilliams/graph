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

import java.util.Date;
import java.util.Random;

/**
 * Tests the Line class.
 */
public final class LineTest {

    /**
     * A random number generator used to construct arbitrary vertex
     * values (since we don't care what they are).
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
     * The {@link Line()} constructor should result in a line that has
     * the requested endpoints appropriately set.
     */
    @Test
    public void constructorShouldConstructEdgeWithRequestedEndpoints() {
        Vertex<Float> one = new Vertex<>(random.nextFloat());
        Vertex<Object> two = new Vertex<>(new Object());

        Edge edge = new Line(one, two);

        Assert.assertEquals(2, edge.endpoints().size());
        Assert.assertTrue(edge.endpoints().contains(one));
        Assert.assertTrue(edge.endpoints().contains(two));
    }

    /**
     * Supplying a null first vertex to the constructor should throw an
     * exception.
     * @throws IllegalArgumentException
     *         Expected result of this test
     */
    @Test(expected = IllegalArgumentException.class)
    public void constructorShouldThrowExceptionWhenFirstArgNull() {
        new Line(null, new Vertex<>(random.nextInt()));
    }

    /**
     * Supplying a null second vertex to the constructor should throw an
     * exception.
     * @throws IllegalArgumentException
     *         Expected result of this test
     */
    @Test(expected = IllegalArgumentException.class)
    public void constructorShouldThrowExceptionWhenSecondArgNull() {
        new Line(new Vertex<>(random.nextInt()), null);
    }

    /**
     * Lines may not connect the same vertices, so ensure that
     * construction of such a line is refused with an exception.
     * @throws IllegalArgumentException
     *         Expected result of this test
     */
    @Test(expected = IllegalArgumentException.class)
    public void constructorShouldThrowExceptionWhenIdenticalVertices() {
        Vertex<Object> vertex = new Vertex<>(new Object());
        new Line(vertex, vertex);
    }

    /**
     * The constructor should refuse to construct if it is passed a null
     * value for the weight.
     */
    @Test(expected = IllegalArgumentException.class)
    public void constructorShouldThrowExceptionOnNullWeight() {
        Vertex<Object> first = new Vertex<>(new Object());
        Vertex<Object> second = new Vertex<>(new Object());
        new Line(first, second, null);
    }

    /**
     * Asking for the weight should return the same value that was
     * passed into the constructor.
     */
    @Test
    public void weightShouldReturnValueFromConstructor() {
        Vertex<Object> first = new Vertex<>(new Object());
        Vertex<Object> second = new Vertex<>(new Object());
        Boolean weight = random.nextBoolean();

        Line line = new Line(first, second, weight);

        Assert.assertEquals(weight, line.weight());
    }

    /**
     * It must not be possible to modify the collection of vertices
     * returned by accessing endpoints() on the line.
     */
    @Test(expected = UnsupportedOperationException.class)
    public void endpointsShouldNotBeModifiable() {
        Boolean value = random.nextBoolean();
        Vertex<Boolean> one = new Vertex<>(value);
        Vertex<Boolean> two = new Vertex<>(!value);

        Edge edge = new Line(one, two);
        edge.endpoints().remove(one);
    }

    /**
     * It must not be possible to agument the set of vertices returned
     * by endpoints() by adding a third vertex.
     */
    @SuppressWarnings("unchecked") // add() on Collection
    @Test(expected = UnsupportedOperationException.class)
    public void endpointsShouldNotBeAugmentable() {
        Boolean value = random.nextBoolean();
        Vertex<Boolean> one = new Vertex<>(value);
        Vertex<Boolean> two = new Vertex<>(!value);

        Edge edge = new Line(one, two);

        Vertex<Boolean> newVertex = new Vertex<>(random.nextBoolean());
        edge.endpoints().add(newVertex);
    }

    /**
     * A line should be equal to itself.
     */
    @Test
    public void equalsShouldReturnTrueWhenArgIsSelf() {
        Boolean value = random.nextBoolean();
        Vertex<Boolean> one = new Vertex<>(value);
        Vertex<Boolean> two = new Vertex<>(!value);

        Edge edge = new Line(one, two);

        Assert.assertEquals(edge, edge);
    }

    /**
     * A line must not be equal to another line if they represent
     * connections between different endpoints.
     */
    @Test
    public void equalsShouldReturnFalseWhenEdgesConnectDifferentEndpoints() {
        Integer value = random.nextInt();
        int offset = 0;
        Vertex<Integer> one = new Vertex<>(value + offset++);
        Vertex<Integer> two = new Vertex<>(value + offset++);
        Vertex<Integer> three = new Vertex<>(value + offset++);

        Edge edge = new Line(one, two);
        Edge different = new Line(one, three);

        Assert.assertNotEquals(edge, different);
    }

    /**
     * A line should be equal to another line provided that they
     * represent connections to the same vertices.
     */
    @Test
    public void equalsShouldReturnTrueWhenEdgesConnectSameEndpoints() {

        Vertex<Date> one = new Vertex<>(new Date());
        Vertex<Object> two = new Vertex<>(new Object());

        Edge edge = new Line(one, two);
        Edge other = new Line(two, one);

        Assert.assertEquals(edge, other);
    }

    /**
     * A line is never equal to an object that is not a line.
     */
    @Test
    public void equalsShouldReturnFalseWhenComparingDifferentObjectClass() {
        Vertex<Double> one = new Vertex<>(random.nextDouble());
        Vertex<Long> two = new Vertex<>(random.nextLong());

        Edge edge = new Line(one, two);

        Assert.assertNotEquals(edge, two);
    }

    /**
     * A line that exists is never equal to a line that does not exist.
     */
    @Test
    public void equalsShouldReturnFalseWhenComparingAgainstNull() {
        Vertex<Object> one = new Vertex<>(new Object());
        Vertex<Object> two = new Vertex<>(new Object());
        Edge edge = new Line(one, two);

        Assert.assertNotEquals(edge, null);
    }

    /**
     * Two lines should NOT be equal when they have the same vertices
     * but different weights.
     */
    @Test
    public void equalsShouldReturnFalseWhenWeightsDiffer() {
        Vertex<Long> firstVertex = new Vertex<>(random.nextLong());
        Vertex<Long> secondVertex = new Vertex<>(random.nextLong());
        Boolean weight = random.nextBoolean();

        Line first = new Line(firstVertex, secondVertex, weight);
        Line second = new Line(firstVertex, secondVertex, !weight);

        Assert.assertNotEquals(first, second);
    }

    /**
     * Lines with different endpoints should have different hash codes.
     */
    @Test
    public void hashCodeShouldReturnDistinctHashesForDistinctEndpointConfigs() {

        Vertex<Object> firstVertex = new Vertex<>(new Object());
        Vertex<Object> secondVertex = new Vertex<>(new Object());
        Vertex<Object> thirdVertex = new Vertex<>(new Object());

        Edge one = new Line(firstVertex, secondVertex);
        Edge two = new Line(secondVertex, thirdVertex);

        Assert.assertNotEquals(one.hashCode(), two.hashCode());
    }

    /**
     * Lines that are the same should have the same hash code.
     */
    @Test
    public void hashCodeShouldReturnSameHashesForSameEndpointConfigs() {
        Vertex<Object> firstVertex = new Vertex<>(new Object());
        Vertex<Object> secondVertex = new Vertex<>(new Object());

        Edge one = new Line(firstVertex, secondVertex);
        Edge two = new Line(secondVertex, firstVertex);

        Assert.assertEquals(one.hashCode(), two.hashCode());
    }

    /**
     * Two lines with different weights should have different hash
     * codes.
     */
    @Test
    public void hashCodeShouldReturnDifferentCodeWhenDifferentWeights() {
        Vertex<Long> firstVertex = new Vertex<>(random.nextLong());
        Vertex<Long> secondVertex = new Vertex<>(random.nextLong());
        Boolean weight = random.nextBoolean();

        Line first = new Line(firstVertex, secondVertex, weight);
        Line second = new Line(firstVertex, secondVertex, !weight);

        Assert.assertNotEquals(first.hashCode(), second.hashCode());
    }
}

