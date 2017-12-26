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
 * Tests the UndirectedEdge class.
 */
public final class UndirectedEdgeTest {

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
     * The {@link UndirectedEdge()} constructor should result in an
     * edge that has the requested vertices appropriately set.
     */
    @Test
    public void constructorShouldConstructEdgeWithRequestedVertices() {
        Vertex<Float> one = new Vertex<>(random.nextFloat());
        Vertex<Object> two = new Vertex<>(new Object());

        Edge edge = new UndirectedEdge(one, two);

        Assert.assertEquals(2, edge.vertices().size());
        Assert.assertTrue(edge.vertices().contains(one));
        Assert.assertTrue(edge.vertices().contains(two));
    }

    /**
     * Supplying a null first vertex to the constructor should throw an
     * exception.
     * @throws IllegalArgumentException
     *         Expected result of this test
     */
    @Test(expected = IllegalArgumentException.class)
    public void constructorShouldThrowExceptionWhenFirstArgNull() {
        new UndirectedEdge(null, new Vertex<>(random.nextInt()));
    }

    /**
     * Supplying a null second vertex to the constructor should throw an
     * exception.
     * @throws IllegalArgumentException
     *         Expected result of this test
     */
    @Test(expected = IllegalArgumentException.class)
    public void constructorShouldThrowExceptionWhenSecondArgNull() {
        new UndirectedEdge(new Vertex<>(random.nextInt()), null);
    }

    /**
     * Undirected edges may not connect the same vertices, so enusre
     * that construction of such an edge is refused with an exception.
     * @throws IllegalArgumentException
     *         Expected result of this test
     */
    @Test(expected = IllegalArgumentException.class)
    public void constructorShouldThrowExceptionWhenVerticesEqual() {
        Vertex<Object> vertex = new Vertex<>(new Object());
        new UndirectedEdge(vertex, vertex);
    }

    /**
     * It must not be possible to modify the collection of vertices
     * returning by accessing vertices() on the edge.
     */
    @Test(expected = UnsupportedOperationException.class)
    public void verticesShouldNotBeModifiable() {
        Boolean value = random.nextBoolean();
        Vertex<Boolean> one = new Vertex<>(value);
        Vertex<Boolean> two = new Vertex<>(!value);

        Edge edge = new UndirectedEdge(one, two);
        edge.vertices().remove(one);
    }

    /**
     * It must not be possible to agument the set of vertices returned
     * by vertices() by adding a third vertex.
     */
    @SuppressWarnings("unchecked") // add() on Collection
    @Test(expected = UnsupportedOperationException.class)
    public void verticesShouldNotBeAugmentable() {
        Boolean value = random.nextBoolean();
        Vertex<Boolean> one = new Vertex<>(value);
        Vertex<Boolean> two = new Vertex<>(!value);

        Edge edge = new UndirectedEdge(one, two);

        Vertex<Boolean> newVertex = new Vertex<>(random.nextBoolean());
        edge.vertices().add(newVertex);
    }

    /**
     * An edge should be equal to itself.
     */
    @Test
    public void equalsShouldReturnTrueWhenArgIsSelf() {
        Boolean value = random.nextBoolean();
        Vertex<Boolean> one = new Vertex<>(value);
        Vertex<Boolean> two = new Vertex<>(!value);

        Edge edge = new UndirectedEdge(one, two);

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
        Vertex<Integer> one = new Vertex<>(value + offset++);
        Vertex<Integer> two = new Vertex<>(value + offset++);
        Vertex<Integer> three = new Vertex<>(value + offset++);

        Edge edge = new UndirectedEdge(one, two);
        Edge different = new UndirectedEdge(one, three);

        Assert.assertNotEquals(edge, different);
    }

    /**
     * An edge should be equal to another edge provided that they
     * represent connections to the same vertices.
     */
    @Test
    public void equalsShouldReturnTrueWhenEdgesConnectSameVertices() {

        Vertex<Date> one = new Vertex<>(new Date());
        Vertex<Object> two = new Vertex<>(new Object());

        Edge edge = new UndirectedEdge(one, two);
        Edge other = new UndirectedEdge(two, one);

        Assert.assertEquals(edge, other);
    }

    /**
     * An edge is never equal to an object that is not an edge.
     */
    @Test
    public void equalsShouldReturnFalseWhenComparingDifferentObjectClass() {
        Vertex<Double> one = new Vertex<>(random.nextDouble());
        Vertex<Long> two = new Vertex<>(random.nextLong());

        Edge edge = new UndirectedEdge(one, two);

        Assert.assertNotEquals(edge, two);
    }

    /**
     * An edge that exists is never equal to an edge that does not
     * exist.
     */
    @Test
    public void equalsShouldReturnFalseWhenComparingAgainstNull() {
        Vertex<Object> one = new Vertex<>(new Object());
        Vertex<Object> two = new Vertex<>(new Object());
        Edge edge = new UndirectedEdge(one, two);

        Assert.assertNotEquals(edge, null);
    }

    /**
     * UndirectedEdges with different vertices should have different
     * hash codes.
     */
    @Test
    public void hashCodeshouldReturnDistinctHashesForDistinctVertexSets() {

        Vertex<Object> firstVertex = new Vertex<>(new Object());
        Vertex<Object> secondVertex = new Vertex<>(new Object());
        Vertex<Object> thirdVertex = new Vertex<>(new Object());

        Edge one = new UndirectedEdge(firstVertex, secondVertex);
        Edge two = new UndirectedEdge(secondVertex, thirdVertex);

        Assert.assertNotEquals(one.hashCode(), two.hashCode());
    }

    /**
     * UndirectedEdges that are the same should have the same hash code.
     */
    @Test
    public void hashCodeShouldReturnSameHashesForIdenticalVertexSets() {
        Vertex<Object> firstVertex = new Vertex<>(new Object());
        Vertex<Object> secondVertex = new Vertex<>(new Object());

        Edge one = new UndirectedEdge(firstVertex, secondVertex);
        Edge two = new UndirectedEdge(secondVertex, firstVertex);

        Assert.assertEquals(one.hashCode(), two.hashCode());
    }
}
