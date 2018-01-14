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
 * Tests the Arrow class.
 */
public final class ArrowTest {

    /**
     * A random number generator used to obtain arbitrary vertex and
     * weight values (since we don't care what they are, just possibly
     * how they relate to one another).
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
     * Calling create should result in an arrow that has the requested
     * endpoints appropriately set.
     */
    @Test
    public void createShouldCreateArrowWithRequestedEndpoints() {
        Vertex<Float> source = Vertex.create(random.nextFloat());
        Vertex<Object> target = Vertex.create(new Object());

        Arrow arrow = Arrow.create(source, target);

        Assert.assertEquals(2, arrow.endpoints().size());
        Assert.assertTrue(arrow.endpoints().contains(source));
        Assert.assertTrue(arrow.endpoints().contains(target));
        Assert.assertEquals(source, arrow.source());
        Assert.assertEquals(target, arrow.target());
    }

    /**
     * Supplying null as first argument to create should throw an
     * exception.
     * @throws IllegalArgumentException
     *         Expected result of this test
     */
    @Test(expected = IllegalArgumentException.class)
    public void createShouldThrowExceptionWhenFirstArgNull() {
        Arrow.create(null, Vertex.create(random.nextInt()));
    }

    /**
     * Supplying null as second endpoint to create should throw an
     * exception.
     * @throws IllegalArgumentException
     *         Expected result of this test
     */
    @Test(expected = IllegalArgumentException.class)
    public void createShouldThrowExceptionWhenSecondArgNull() {
        Arrow.create(Vertex.create(random.nextInt()), null);
    }

    /**
     * Arrows allow self loops (where source == target).
     */
    @Test
    public void createShouldAllowSelfLoop() {
        Vertex<Object> vertex = Vertex.create(new Object());

        Arrow arrow = Arrow.create(vertex, vertex);

        Assert.assertEquals(2, arrow.endpoints().size());
        Assert.assertTrue(arrow.endpoints().contains(vertex));
        Assert.assertEquals(vertex, arrow.source());
        Assert.assertEquals(vertex, arrow.target());
    }

    /**
     * Asking for the weight should return the same value that was
     * passed into create().
     */
    @Test
    public void weightShouldReturnValueFromCreate() {
        Vertex<Long> vertex = Vertex.create(random.nextLong());
        Boolean weight = random.nextBoolean();

        Arrow arrow = Arrow.create(vertex, vertex, weight);

        Assert.assertEquals(weight, arrow.weight());
    }

    /**
     * It should not be possible to change a value of one of the
     * endpoints returned by the endpoints() method.
     */
    @Test(expected = UnsupportedOperationException.class)
    public void endpointsShouldNotBeChangeable() {
        Vertex<Boolean> vertex = Vertex.create(random.nextBoolean());

        Arrow.create(vertex, vertex).endpoints().remove(vertex);
    }

    /**
     * It should not be possible to add endpoints to the collection
     * return by the endpoints() method.
     */
    @SuppressWarnings("unchecked") // add() on Collection
    @Test(expected = UnsupportedOperationException.class)
    public void endpointsShouldNotBeAugmentable() {
        Vertex<Boolean> vertex = Vertex.create(random.nextBoolean());

        Arrow.create(vertex, vertex).endpoints().add(vertex);
    }

    /**
     * An arrow should be equal to itself.
     */
    @Test
    public void equalsShouldReturnTrueWhenArgIsSelf() {
        Boolean value = random.nextBoolean();
        Edge edge = Arrow.create(Vertex.create(value), Vertex.create(!value));

        Assert.assertEquals(edge, edge);
    }

    /**
     * An arrow must not be equal to another arrow if they represent
     * connections between different endpoints.
     */
    @Test
    public void equalsShouldReturnFalseWhenArrowsConnectDifferentEndpoints() {
        Integer value = random.nextInt();
        int offset = 0;
        Vertex<Integer> source = Vertex.create(value + offset++);
        Edge edge = Arrow.create(source, Vertex.create(value + offset++));
        Edge different = Arrow.create(source, Vertex.create(value + offset++));

        Assert.assertNotEquals(edge, different);
    }

    /**
     * Reversing the source and target of an arrow means it isn't the
     * same arrow.
     */
    @Test
    public void equalsShouldReturnFalseWhenSourceAndTargetReversed() {

        Vertex<Date> source = Vertex.create(new Date());
        Vertex<Object> target = Vertex.create(new Object());

        Edge forward = Arrow.create(source, target);
        Edge reverse = Arrow.create(target, source);

        Assert.assertNotEquals(forward, reverse);
    }

    /**
     * An arrow is never equal to an object that is not an arrow.
     */
    @Test
    public void equalsShouldReturnFalseWhenComparingDifferentObjectClass() {
        Vertex<Double> vertex = Vertex.create(random.nextDouble());
        Edge edge = Arrow.create(vertex, Vertex.create(random.nextLong()));

        // edge is of type arrow, vertex is of type vertex
        Assert.assertNotEquals(edge, vertex);
    }

    /**
     * An arrow that exists is never equal to an arrow that does not
     * exist.
     */
    @Test
    public void equalsShouldReturnFalseWhenComparingAgainstNull() {
        Vertex<Object> source = Vertex.create(new Object());
        Vertex<Object> target = Vertex.create(new Object());

        Assert.assertNotEquals(Arrow.create(source, target), null);
    }

    /**
     * Two arrows should NOT be equal when they have the same vertices
     * but different weights.
     */
    @Test
    public void equalsShouldReturnFalseWhenWeightsDiffer() {
        Vertex<Long> firstVertex = Vertex.create(random.nextLong());
        Vertex<Long> secondVertex = Vertex.create(random.nextLong());
        Boolean weight = random.nextBoolean();

        Arrow first = Arrow.create(firstVertex, secondVertex, weight);
        Arrow second = Arrow.create(firstVertex, secondVertex, !weight);

        Assert.assertNotEquals(first, second);
    }

    /**
     * Arrows with different endpoints should have different
     * hash codes.
     */
    @Test
    public void hashCodeShouldReturnDistinctHashesForDistinctEndpointConfigs() {

        Vertex<Object> firstVertex = Vertex.create(new Object());
        Vertex<Object> secondVertex = Vertex.create(new Object());
        Vertex<Object> thirdVertex = Vertex.create(new Object());

        Edge one = Arrow.create(firstVertex, secondVertex);
        Edge two = Arrow.create(secondVertex, thirdVertex);

        Assert.assertNotEquals(one.hashCode(), two.hashCode());
    }

    /**
     * Arrows that are the same should have the same hash code.
     */
    @Test
    public void hashCodeShouldReturnSameHashesForIdenticalEndpointConfigs() {
        Object object = new Object();
        Vertex<Object> source = Vertex.create(object);
        Vertex<Object> target = Vertex.create(object);

        Edge one = Arrow.create(source, target);
        Edge two = Arrow.create(source, target);

        Assert.assertEquals(one.hashCode(), two.hashCode());
    }

    /**
     * The hash code of an arrow from A to B should not be the same as
     * the hash code of an arrow from B to A.
     */
    @Test
    public void hashCodeShouldReturnDifferentHashForReversedDirection() {
        Vertex<Object> source = Vertex.create(new Object());
        Vertex<Object> target = Vertex.create(new Object());

        Edge forward = Arrow.create(source, target);
        Edge reverse = Arrow.create(target, source);

        Assert.assertNotEquals(forward.hashCode(), reverse.hashCode());
    }

    /**
     * Two arrows with different weights should have different hash
     * codes.
     */
    @Test
    public void hashCodeShouldReturnDifferentCodeWhenDifferentWeights() {
        Vertex<Long> source = Vertex.create(random.nextLong());
        Vertex<Long> target = Vertex.create(random.nextLong());
        Boolean weight = random.nextBoolean();

        Arrow first = Arrow.create(source, target, weight);
        Arrow second = Arrow.create(source, target, !weight);

        Assert.assertNotEquals(first.hashCode(), second.hashCode());
    }
}
