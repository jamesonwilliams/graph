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
 * Tests the DirectedEdge class.
 */
public final class DirectedEdgeTest {

    /**
     * A random number generator used to construct arbitrary edge
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
     * The {@link DirectedEdge()} constructor should result in an
     * edge that has the requested vertices appropriately set.
     */
    @Test
    public void constructorShouldConstructEdgeWithRequestedVertices() {
        Vertex<Float> source = new Vertex<>(random.nextFloat());
        Vertex<Object> target = new Vertex<>(new Object());

        DirectedEdge edge = new DirectedEdge(source, target);

        Assert.assertEquals(2, edge.vertices().size());
        Assert.assertTrue(edge.vertices().contains(source));
        Assert.assertTrue(edge.vertices().contains(target));
        Assert.assertEquals(source, edge.source());
        Assert.assertEquals(target, edge.target());
    }

    /**
     * Supplying a null first vertex to the constructor should throw an
     * exception.
     * @throws IllegalArgumentException
     *         Expected result of this test
     */
    @Test(expected = IllegalArgumentException.class)
    public void constructorShouldThrowExceptionWhenFirstArgNull() {
        new DirectedEdge(null, new Vertex<>(random.nextInt()));
    }

    /**
     * Supplying a null second vertex to the constructor should throw an
     * exception.
     * @throws IllegalArgumentException
     *         Expected result of this test
     */
    @Test(expected = IllegalArgumentException.class)
    public void constructorShouldThrowExceptionWhenSecondArgNull() {
        new DirectedEdge(new Vertex<>(random.nextInt()), null);
    }

    /**
     * Directed edges allow self loops (where source == target).
     */
    @Test
    public void constructorShouldAllowSelfLoop() {
        Vertex<Object> vertex = new Vertex<>(new Object());

        DirectedEdge edge = new DirectedEdge(vertex, vertex);

        Assert.assertEquals(2, edge.vertices().size());
        Assert.assertTrue(edge.vertices().contains(vertex));
        Assert.assertEquals(vertex, edge.source());
        Assert.assertEquals(vertex, edge.target());
    }

    /**
     * It should not be possible to change a value of one of the
     * vertices returned by the vertices() method.
     */
    @Test(expected = UnsupportedOperationException.class)
    public void verticesShouldNotBeChangeable() {
        Vertex<Boolean> vertex = new Vertex<>(random.nextBoolean());

        Edge edge = new DirectedEdge(vertex, vertex);

        edge.vertices().remove(vertex);
    }

    /**
     * It should not be possible to add vertices to the collection
     * return by the vertices() method.
     */
    @SuppressWarnings("unchecked") // add() on Collection
    @Test(expected = UnsupportedOperationException.class)
    public void verticesShouldNotBeAugmentable() {
        Vertex<Boolean> vertex = new Vertex<>(random.nextBoolean());

        Edge edge = new DirectedEdge(vertex, vertex);

        edge.vertices().add(new Vertex<>(random.nextBoolean()));
    }

    /**
     * An edge should be equal to itself.
     */
    @Test
    public void equalsShouldReturnTrueWhenArgIsSelf() {
        Boolean value = random.nextBoolean();
        Vertex<Boolean> source = new Vertex<>(value);
        Vertex<Boolean> target = new Vertex<>(!value);

        Edge edge = new DirectedEdge(source, target);

        Assert.assertEquals(edge, edge);
    }

    /**
     * An edge must not be equal to another edge if they represent
     * connections between different vertices.
     */
    @Test
    public void equalsShouldReturnFalseWhenEdgesConnectDifferentVertices() {
        Integer value = random.nextInt();
        int offset = 0;
        Vertex<Integer> source = new Vertex<>(value + offset++);
        Vertex<Integer> targetOne = new Vertex<>(value + offset++);
        Vertex<Integer> targetTwo = new Vertex<>(value + offset++);

        Edge edge = new DirectedEdge(source, targetOne);
        Edge different = new DirectedEdge(source, targetTwo);

        Assert.assertNotEquals(edge, different);
    }

    /**
     * Reversing the source and target of an edge means it isn't the
     * same directed edge.
     */
    @Test
    public void equalsShouldReturnFalseWhenSourceAndTargetReversed() {

        Vertex<Date> source = new Vertex<>(new Date());
        Vertex<Object> target = new Vertex<>(new Object());

        Edge forward = new DirectedEdge(source, target);
        Edge reverse = new DirectedEdge(target, source);

        Assert.assertNotEquals(forward, reverse);
    }

    /**
     * An edge is never equal to an object that is not an edge.
     */
    @Test
    public void equalsShouldReturnFalseWhenComparingDifferentObjectClass() {
        Vertex<Double> source = new Vertex<>(random.nextDouble());
        Vertex<Long> target = new Vertex<>(random.nextLong());

        Edge edge = new DirectedEdge(source, target);

        // source is vertex, not edge
        Assert.assertNotEquals(edge, source);
    }

    /**
     * An edge that exists is never equal to an edge that does not
     * exist.
     */
    @Test
    public void equalsShouldReturnFalseWhenComparingAgainstNull() {
        Vertex<Object> source = new Vertex<>(new Object());
        Vertex<Object> target = new Vertex<>(new Object());
        Edge edge = new DirectedEdge(source, target);

        Assert.assertNotEquals(edge, null);
    }

    /**
     * DirectedEdges with different vertices should have different
     * hash codes.
     */
    @Test
    public void hashCodeShouldReturnDistinctHashesForDistinctVertexSets() {

        Vertex<Object> firstVertex = new Vertex<>(new Object());
        Vertex<Object> secondVertex = new Vertex<>(new Object());
        Vertex<Object> thirdVertex = new Vertex<>(new Object());

        Edge one = new DirectedEdge(firstVertex, secondVertex);
        Edge two = new DirectedEdge(secondVertex, thirdVertex);

        Assert.assertNotEquals(one.hashCode(), two.hashCode());
    }

    /**
     * DirectedEdges that are the same should have the same hash code.
     */
    @Test
    public void hashCodeShouldReturnSameHashesForIdenticalVertexSets() {
        Object object = new Object();
        Vertex<Object> source = new Vertex<>(object);
        Vertex<Object> target = new Vertex<>(object);

        Edge one = new DirectedEdge(source, target);
        Edge two = new DirectedEdge(source, target);

        Assert.assertEquals(one.hashCode(), two.hashCode());
    }

    /**
     * The hash code of a directed edge from A to B should not be the
     * same as the hash code of a directed edge from B to A.
     */
    @Test
    public void hashCodeShouldReturnDifferentHashForReversedDirection() {
        Vertex<Object> source = new Vertex<>(new Object());
        Vertex<Object> target = new Vertex<>(new Object());

        Edge forward = new DirectedEdge(source, target);
        Edge reverse = new DirectedEdge(target, source);

        Assert.assertNotEquals(forward.hashCode(), reverse.hashCode());
    }
}
