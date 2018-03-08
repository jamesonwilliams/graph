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
     * A random number generator used to obtain arbitrary vertex and
     * weight values (since we don't care what they are).
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
     * Calling create should result in a line that has the requested
     * endpoints appropriately set.
     */
    @Test
    public void createShouldCreateLineWithRequestedEndpoints() {
        Vertex<Float> one = Vertex.create(random.nextFloat());
        Vertex<Object> two = Vertex.create(new Object());

        Edge edge = Line.create(one, two);

        Assert.assertEquals(2, edge.endpoints().size());
        Assert.assertTrue(edge.endpoints().contains(one));
        Assert.assertTrue(edge.endpoints().contains(two));
    }

    /**
     * Supplying a null first vertex to create() should throw an
     * exception.
     * @throws IllegalArgumentException
     *         Expected result of this test
     */
    @Test(expected = IllegalArgumentException.class)
    public void createShouldThrowExceptionWhenFirstArgNull() {
        Line.create(null, Vertex.create(random.nextInt()));
    }

    /**
     * Supplying a null second vertex to create() should throw an
     * exception.
     * @throws IllegalArgumentException
     *         Expected result of this test
     */
    @Test(expected = IllegalArgumentException.class)
    public void createShouldThrowExceptionWhenSecondArgNull() {
        Line.create(Vertex.create(random.nextInt()), null);
    }

    /**
     * Lines may not connect the same vertices, so ensure that creation
     * of such a line is refused with an exception.
     * @throws IllegalArgumentException
     *         Expected result of this test
     */
    @Test(expected = IllegalArgumentException.class)
    public void createShouldThrowExceptionWhenIdenticalVertices() {
        Vertex<Object> vertex = Vertex.create(new Object());
        Line.create(vertex, vertex);
    }

    /**
     * Asking for the weight should return the same value that was
     * passed into create().
     */
    @Test
    public void weightShouldReturnValueFromCreate() {
        Vertex<Object> first = Vertex.create(new Object());
        Vertex<Object> second = Vertex.create(new Object());
        Weight<Long> weight = Weight.of(random.nextLong());

        Line line = Line.create(first, second, weight);

        Assert.assertEquals(weight, line.weight().get());
    }

    /**
     * It must not be possible to modify the collection of vertices
     * returned by accessing endpoints() on the line.
     */
    @Test(expected = UnsupportedOperationException.class)
    public void endpointsShouldNotBeModifiable() {
        Boolean value = random.nextBoolean();
        Vertex<Boolean> vertex = Vertex.create(value);
        Edge edge = Line.create(vertex, Vertex.create(!value));

        edge.endpoints().remove(vertex);
    }

    /**
     * It must not be possible to agument the set of vertices returned
     * by endpoints() by adding a third vertex.
     */
    @SuppressWarnings("unchecked") // add() on Collection
    @Test(expected = UnsupportedOperationException.class)
    public void endpointsShouldNotBeAugmentable() {
        Boolean value = random.nextBoolean();
        Edge edge = Line.create(Vertex.create(value), Vertex.create(!value));

        edge.endpoints().add(Vertex.create(random.nextBoolean()));
    }

    /**
     * A line should be equal to itself.
     */
    @Test
    public void equalsShouldReturnTrueWhenArgIsSelf() {
        Boolean value = random.nextBoolean();
        Edge edge = Line.create(Vertex.create(value), Vertex.create(!value));

        Assert.assertEquals(edge, edge);
    }

    /**
     * A line must not be equal to another line if they represent
     * connections between different endpoints.
     */
    @Test
    public void equalsShouldReturnFalseWhenLinesConnectDifferentEndpoints() {
        Integer value = random.nextInt();
        int offset = 0;
        Vertex<Integer> vertex = Vertex.create(value + offset++);
        Edge edge = Line.create(vertex, Vertex.create(value + offset++));
        Edge different = Line.create(vertex, Vertex.create(value + offset++));

        Assert.assertNotEquals(edge, different);
    }

    /**
     * A line should be equal to another line provided that they
     * represent connections to the same vertices.
     */
    @Test
    public void equalsShouldReturnTrueWhenLinesConnectSameEndpoints() {

        Vertex<Date> one = Vertex.create(new Date());
        Vertex<Object> two = Vertex.create(new Object());

        Assert.assertEquals(Line.create(one, two), Line.create(two, one));
    }

    /**
     * A line is never equal to an object that is not a line.
     */
    @Test
    public void equalsShouldReturnFalseWhenComparingDifferentObjectClass() {
        Vertex<Long> vertex = Vertex.create(random.nextLong());
        Edge edge = Line.create(Vertex.create(random.nextDouble()), vertex);

        Assert.assertNotEquals(edge, vertex);
    }

    /**
     * A line that exists is never equal to a line that does not exist.
     */
    @Test
    public void equalsShouldReturnFalseWhenComparingAgainstNull() {
        Vertex<Object> one = Vertex.create(new Object());
        Vertex<Object> two = Vertex.create(new Object());

        Assert.assertNotEquals(Line.create(one, two), null);
    }

    /**
     * Two lines should NOT be equal when they have the same vertices
     * but different weights.
     */
    @Test
    public void equalsShouldReturnFalseWhenWeightsDiffer() {
        Vertex<Long> firstVertex = Vertex.create(random.nextLong());
        Vertex<Long> secondVertex = Vertex.create(random.nextLong());
        Weight<Float> firstWeight = Weight.of(random.nextFloat());
        Weight<Float> secondWeight = Weight.of(random.nextFloat());

        Line first = Line.create(firstVertex, secondVertex, firstWeight);
        Line second = Line.create(firstVertex, secondVertex, secondWeight);

        Assert.assertNotEquals(first, second);
    }

    /**
     * Lines with different endpoints should have different hash codes.
     */
    @Test
    public void hashCodeShouldReturnDistinctHashesForDistinctEndpointConfigs() {

        Vertex<Object> firstVertex = Vertex.create(new Object());
        Vertex<Object> secondVertex = Vertex.create(new Object());
        Vertex<Object> thirdVertex = Vertex.create(new Object());

        Edge one = Line.create(firstVertex, secondVertex);
        Edge two = Line.create(secondVertex, thirdVertex);

        Assert.assertNotEquals(one.hashCode(), two.hashCode());
    }

    /**
     * Lines that are the same should have the same hash code.
     */
    @Test
    public void hashCodeShouldReturnSameHashesForSameEndpointConfigs() {
        Vertex<Object> firstVertex = Vertex.create(new Object());
        Vertex<Object> secondVertex = Vertex.create(new Object());

        Edge one = Line.create(firstVertex, secondVertex);
        Edge two = Line.create(secondVertex, firstVertex);

        Assert.assertEquals(one.hashCode(), two.hashCode());
    }

    /**
     * Two lines with different weights should have different hash
     * codes.
     */
    @Test
    public void hashCodeShouldReturnDifferentCodeWhenDifferentWeights() {
        Vertex<Long> firstVertex = Vertex.create(random.nextLong());
        Vertex<Long> secondVertex = Vertex.create(random.nextLong());
        Weight<Double> firstWeight = Weight.of(random.nextDouble());
        Weight<Double> secondWeight = Weight.of(random.nextDouble());

        Line first = Line.create(firstVertex, secondVertex, firstWeight);
        Line second = Line.create(firstVertex, secondVertex, secondWeight);

        Assert.assertNotEquals(first.hashCode(), second.hashCode());
    }
}

