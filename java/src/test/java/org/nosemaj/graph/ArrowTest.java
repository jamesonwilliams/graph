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
     * A random number generator used to construct arbitrary arrow
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
     * The {@link Arrow()} constructor should result in an arrow that
     * has the requested endpoints appropriately set.
     */
    @Test
    public void constructorShouldConstructArrowWithRequestedEndpoints() {
        Vertex<Float> source = new Vertex<>(random.nextFloat());
        Vertex<Object> target = new Vertex<>(new Object());

        Arrow arrow = new Arrow(source, target);

        Assert.assertEquals(2, arrow.endpoints().size());
        Assert.assertTrue(arrow.endpoints().contains(source));
        Assert.assertTrue(arrow.endpoints().contains(target));
        Assert.assertEquals(source, arrow.source());
        Assert.assertEquals(target, arrow.target());
    }

    /**
     * Supplying a null first endpoint to the constructor should throw
     * an exception.
     * @throws IllegalArgumentException
     *         Expected result of this test
     */
    @Test(expected = IllegalArgumentException.class)
    public void constructorShouldThrowExceptionWhenFirstArgNull() {
        new Arrow(null, new Vertex<>(random.nextInt()));
    }

    /**
     * Supplying a null second endpoint to the constructor should throw
     * an exception.
     * @throws IllegalArgumentException
     *         Expected result of this test
     */
    @Test(expected = IllegalArgumentException.class)
    public void constructorShouldThrowExceptionWhenSecondArgNull() {
        new Arrow(new Vertex<>(random.nextInt()), null);
    }

    /**
     * Arrows allow self loops (where source == target).
     */
    @Test
    public void constructorShouldAllowSelfLoop() {
        Vertex<Object> vertex = new Vertex<>(new Object());

        Arrow arrow = new Arrow(vertex, vertex);

        Assert.assertEquals(2, arrow.endpoints().size());
        Assert.assertTrue(arrow.endpoints().contains(vertex));
        Assert.assertEquals(vertex, arrow.source());
        Assert.assertEquals(vertex, arrow.target());
    }

    /**
     * The constructor should refuse to construct when passed a null
     * weight.
     */
    @Test(expected = IllegalArgumentException.class)
    public void constructorShouldThrowExceptionOnNullWeight() {
        Vertex<Long> source = new Vertex<>(random.nextLong());
        Vertex<Long> target = new Vertex<>(random.nextLong());

        new Arrow(source, target, null);
    }

    /**
     * Asking for the weight should return the same value that was
     * passed into the constructor.
     */
    @Test
    public void weightShouldReturnValueFromConstructor() {
        Vertex<Long> source = new Vertex<>(random.nextLong());
        Vertex<Long> target = new Vertex<>(random.nextLong());
        Boolean weight = random.nextBoolean();

        Arrow arrow = new Arrow(source, target, weight);

        Assert.assertEquals(weight, arrow.weight());
    }

    /**
     * It should not be possible to change a value of one of the
     * endpoints returned by the endpoints() method.
     */
    @Test(expected = UnsupportedOperationException.class)
    public void endpointsShouldNotBeChangeable() {
        Vertex<Boolean> vertex = new Vertex<>(random.nextBoolean());

        Edge edge = new Arrow(vertex, vertex);

        edge.endpoints().remove(vertex);
    }

    /**
     * It should not be possible to add endpoints to the collection
     * return by the endpoints() method.
     */
    @SuppressWarnings("unchecked") // add() on Collection
    @Test(expected = UnsupportedOperationException.class)
    public void endpointsShouldNotBeAugmentable() {
        Vertex<Boolean> vertex = new Vertex<>(random.nextBoolean());

        Edge edge = new Arrow(vertex, vertex);

        edge.endpoints().add(vertex);
    }

    /**
     * An arrow should be equal to itself.
     */
    @Test
    public void equalsShouldReturnTrueWhenArgIsSelf() {
        Boolean value = random.nextBoolean();
        Vertex<Boolean> source = new Vertex<>(value);
        Vertex<Boolean> target = new Vertex<>(!value);

        Edge edge = new Arrow(source, target);

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
        Vertex<Integer> source = new Vertex<>(value + offset++);
        Vertex<Integer> edgeTarget = new Vertex<>(value + offset++);
        Vertex<Integer> differentTarget = new Vertex<>(value + offset++);

        Edge edge = new Arrow(source, edgeTarget);
        Edge different = new Arrow(source, differentTarget);

        Assert.assertNotEquals(edge, different);
    }

    /**
     * Reversing the source and target of an arrow means it isn't the
     * same arrow.
     */
    @Test
    public void equalsShouldReturnFalseWhenSourceAndTargetReversed() {

        Vertex<Date> source = new Vertex<>(new Date());
        Vertex<Object> target = new Vertex<>(new Object());

        Edge forward = new Arrow(source, target);
        Edge reverse = new Arrow(target, source);

        Assert.assertNotEquals(forward, reverse);
    }

    /**
     * An arrow is never equal to an object that is not an arrow.
     */
    @Test
    public void equalsShouldReturnFalseWhenComparingDifferentObjectClass() {
        Vertex<Double> source = new Vertex<>(random.nextDouble());
        Vertex<Long> target = new Vertex<>(random.nextLong());

        Edge edge = new Arrow(source, target);

        // edge is of type arrow, source is of type vertex
        Assert.assertNotEquals(edge, source);
    }

    /**
     * An arrow that exists is never equal to an arrow that does not
     * exist.
     */
    @Test
    public void equalsShouldReturnFalseWhenComparingAgainstNull() {
        Vertex<Object> source = new Vertex<>(new Object());
        Vertex<Object> target = new Vertex<>(new Object());
        Edge edge = new Arrow(source, target);

        Assert.assertNotEquals(edge, null);
    }

    /**
     * Two arrows should NOT be equal when they have the same vertices
     * but different weights.
     */
    @Test
    public void equalsShouldReturnFalseWhenWeightsDiffer() {
        Vertex<Long> firstVertex = new Vertex<>(random.nextLong());
        Vertex<Long> secondVertex = new Vertex<>(random.nextLong());
        Boolean weight = random.nextBoolean();

        Arrow first = new Arrow(firstVertex, secondVertex, weight);
        Arrow second = new Arrow(firstVertex, secondVertex, !weight);

        Assert.assertNotEquals(first, second);
    }

    /**
     * Arrows with different endpoints should have different
     * hash codes.
     */
    @Test
    public void hashCodeShouldReturnDistinctHashesForDistinctEndpointConfigs() {

        Vertex<Object> firstVertex = new Vertex<>(new Object());
        Vertex<Object> secondVertex = new Vertex<>(new Object());
        Vertex<Object> thirdVertex = new Vertex<>(new Object());

        Edge one = new Arrow(firstVertex, secondVertex);
        Edge two = new Arrow(secondVertex, thirdVertex);

        Assert.assertNotEquals(one.hashCode(), two.hashCode());
    }

    /**
     * Arrows that are the same should have the same hash code.
     */
    @Test
    public void hashCodeShouldReturnSameHashesForIdenticalEndpointConfigs() {
        Object object = new Object();
        Vertex<Object> source = new Vertex<>(object);
        Vertex<Object> target = new Vertex<>(object);

        Edge one = new Arrow(source, target);
        Edge two = new Arrow(source, target);

        Assert.assertEquals(one.hashCode(), two.hashCode());
    }

    /**
     * The hash code of an arrow from A to B should not be the same as
     * the hash code of an arrow from B to A.
     */
    @Test
    public void hashCodeShouldReturnDifferentHashForReversedDirection() {
        Vertex<Object> source = new Vertex<>(new Object());
        Vertex<Object> target = new Vertex<>(new Object());

        Edge forward = new Arrow(source, target);
        Edge reverse = new Arrow(target, source);

        Assert.assertNotEquals(forward.hashCode(), reverse.hashCode());
    }

    /**
     * Two arrows with different weights should have different hash
     * codes.
     */
    @Test
    public void hashCodeShouldReturnDifferentCodeWhenDifferentWeights() {
        Vertex<Long> source = new Vertex<>(random.nextLong());
        Vertex<Long> target = new Vertex<>(random.nextLong());
        Boolean weight = random.nextBoolean();

        Arrow first = new Arrow(source, target, weight);
        Arrow second = new Arrow(source, target, !weight);

        Assert.assertNotEquals(first.hashCode(), second.hashCode());
    }
}
