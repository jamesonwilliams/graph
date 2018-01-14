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

import java.util.List;
import java.util.Random;
import java.util.Set;

/**
 * Tests the AdjacencyTable graph implementation.
 */
public final class AdjacencyTableTest {

    /**
     * Random number generator used to populate vertex values and edge
     * weights.
     */
    private Random random;

    /**
     * Sets up test dependencies.
     */
    @Before
    public void setup() {
        random = new Random();
    }

    /**
     * The default access constructor should refust construction if it
     * is not passed a valid table to use.
     * @throws IllegalArgumentException expected behavior
     */
    @Test(expected = IllegalArgumentException.class)
    public void defaultConstructorThrowsExceptionOnNullArgument() {
        new AdjacencyTable(null);
    }

    /**
     * Calling the create method returns a valid adjacency table.
     */
    @Test
    public void createReturnsValidAdjacencyTable() {
        AdjacencyTable.create();
    }

    /**
     * Calling neighbors with a null argument should throw an excetpion.
     * @throws IllegalArgumentException expected behaviour
     */
    @Test(expected = IllegalArgumentException.class)
    public void neighborsThrowsExceptionOnNullArg() {
        AdjacencyTable.create().neighbors(null);
    }

    /**
     * Calling neighbors for an unknown vertex should throw an
     * exception.
     * @throws IllegalArgumentException expected beahviour
     */
    @Test(expected = IllegalArgumentException.class)
    public void neighborsThrowsExceptionForUnknownVertex() {
        AdjacencyTable.create().neighbors(Vertex.create(null));
    }

    /**
     * Calling neighbors for a vertex with no links should result in an
     * empty set of neighbors.
     */
    @Test
    public void neighborsReturnsEmptySetForUnlinkedVertex() {
        Vertex<Boolean> vertex = Vertex.create(random.nextBoolean());
        Graph graph = AdjacencyTable.create();
        graph.add(vertex);

        Assert.assertTrue(graph.neighbors(vertex).isEmpty());
    }

    /**
     * Calling neighbors should return populated set of neighboring
     * vertices, consistent with the way the graph was constructed.
     */
    @Test
    public void neighborsReturnsCorrectSetForLinkedVertex() {
        Graph graph = AdjacencyTable.create();
        Vertex<Boolean> truth = Vertex.create(random.nextBoolean());
        Vertex<Long> numeric = Vertex.create(random.nextLong());
        graph.add(truth);
        graph.add(numeric);
        graph.add(Arrow.create(truth, numeric));

        Set<Vertex> neighbors = graph.neighbors(truth);

        Assert.assertEquals(1, neighbors.size());
        Assert.assertTrue(neighbors.contains(numeric));
    }

    /**
     * Adding a null vertex should throw an exception.
     * @throws IllegalArgumentException expected behavior
     */
    @Test(expected = IllegalArgumentException.class)
    public void addVertexThrowsExceptionOnNullArg() {
        AdjacencyTable.create().add((Vertex<Object>) null);
    }

    /**
     * Adding the same vertex twice should throw an exception.
     * @throws IllegalArgumentException expected behavior
     */
    @Test(expected = IllegalArgumentException.class)
    public void addVertexThrowsExceptionWhenVertexAlreadyInGraph() {
        Graph graph = AdjacencyTable.create();
        graph.add(Vertex.create(null));

        // Because of equals(), this new vertex should look the same
        graph.add(Vertex.create(null));
    }

    /**
     * The graph should contain a valid vertex, after one is added.
     */
    @Test
    public void addVertexSuccessfullyAddsVertexToGraph() {
        Graph graph = AdjacencyTable.create();
        Vertex<Object> vertex = Vertex.create(null);

        graph.add(vertex);

        Assert.assertTrue(graph.contains(vertex));
    }

    /**
     * Removing a null vertex should throw an exception.
     * @throws IllegalArgumentException expected behavior
     */
    @Test(expected = IllegalArgumentException.class)
    public void removeVertexThrowsExceptionOnNullArg() {
        AdjacencyTable.create().remove((Vertex<Object>) null);
    }

    /**
     * An attempt to remove a vertex which isn't in the graph should
     * result in an exception.
     * @throws IllegalArgumentException expected behavior
     */
    @Test(expected = IllegalArgumentException.class)
    public void removeVertexThrowsExceptionWhenVertexNotInGraph() {
        // Empty graphs have nothing in them, so ya.
        AdjacencyTable.create().remove(Vertex.create(null));
    }

    /**
     * Removing a valid vertex that exists in the graph should result in
     * the vertex disappearing from the graph.
     */
    @Test
    public void removeVertexSuccessfullyRemovesValidVertex() {
        Graph graph = AdjacencyTable.create();
        Arrow arrow = Arrow.create(Vertex.create(random.nextBoolean()),
                Vertex.create(random.nextLong()));

        graph.add(arrow.source());
        graph.add(arrow.target());
        graph.add(arrow);

        graph.remove(arrow.target());

        Assert.assertTrue(graph.contains(arrow.source()));
        Assert.assertFalse(graph.contains(arrow.target()));
    }

    /**
     * Removing a vertex which is unrelated to an existing edge
     * succeeds, and does not perterb the existing edge.
     */
    @Test
    public void removeVertexSucceedsWhenUnrelatedToEdges() {
        Graph graph = AdjacencyTable.create();

        Vertex<Integer> vertex = Vertex.create(random.nextInt());
        Arrow arrow = Arrow.create(vertex, vertex);
        graph.add(vertex);
        graph.add(arrow);

        Vertex<Boolean> unrelated = Vertex.create(random.nextBoolean());
        graph.add(unrelated);
        // Validate the state prior to removing the unrelated vertex
        Assert.assertTrue(graph.contains(vertex));
        Assert.assertTrue(graph.contains(arrow));
        Assert.assertTrue(graph.contains(unrelated));
        Assert.assertFalse(graph.neighbors(vertex).isEmpty());
        Assert.assertFalse(graph.neighbors(vertex).contains(unrelated));
        Assert.assertTrue(graph.neighbors(unrelated).isEmpty());

        graph.remove(unrelated);
        // This is the main thing of interest ...
        Assert.assertFalse(graph.contains(unrelated));

        // But also make sure we didn't mess up other vertices...
        Assert.assertTrue(graph.contains(vertex));
        Assert.assertTrue(graph.contains(arrow));
        Assert.assertFalse(graph.neighbors(vertex).isEmpty());
    }

    /**
     * Calling contains with a null vertex should throw an exception.
     * @throws IllegalArgumentException expected behavior
     */
    @Test(expected = IllegalArgumentException.class)
    public void containsVertexThrowsExceptionWhenArgIsNull() {
        AdjacencyTable.create().contains((Vertex<Object>) null);
    }

    /**
     * contains must return false for a vertex which is not contained in
     * the graph.
     */
    @Test
    public void containsVertexReturnsFalseWhenVertexNotInGraph() {
        AdjacencyTable.create().contains(Vertex.create(null));
    }

    /**
     * contains must return true for a vertex which has been
     * successfully added to the graph.
     */
    @Test
    public void containsVertexReturnsTrueWhenVertexIsInGraph() {
        Vertex<Integer> vertex = Vertex.create(random.nextInt());
        Graph graph = AdjacencyTable.create();
        graph.add(vertex);

        Assert.assertTrue(graph.contains(vertex));
    }

    /**
     * The vertex set should be empty when a graph is constructed.
     */
    @Test
    public void verticesReturnsEmptySetByDefault() {
        Assert.assertTrue(AdjacencyTable.create().vertices().isEmpty());
    }

    /**
     * The vertex set should describe all added vertices.
     */
    @Test
    public void verticesReturnsAllVerticesInGraph() {
        Graph graph = AdjacencyTable.create();
        Vertex vertex = Vertex.create(null);
        graph.add(vertex);
        Assert.assertFalse(graph.vertices().isEmpty());
        Assert.assertEquals(1, graph.vertices().size());
        Assert.assertTrue(graph.vertices().contains(vertex));
    }

    /**
     * Passing a null argument when attempting to add an edge to the
     * graph should result in an exception.
     * @throws IllegalArgumentException expected behavior
     */
    @Test(expected = IllegalArgumentException.class)
    public void addEdgeThrowsExceptionOnNullArg() {
        Vertex<Boolean> vertex = Vertex.create(null);
        AdjacencyTable.create().add(Arrow.create(vertex, vertex));
    }

    /**
     * An attempt to add an edge whose first vertex is not in the graph
     * should result in an exception.
     * @throws IllegalArgumentException expected behavior
     */
    @Test(expected = IllegalArgumentException.class)
    public void addEdgeThrowsExceptionWhenFirstEndpointNotInGraph() {
        Graph graph = AdjacencyTable.create();
        Vertex<Object> object = Vertex.create(new Object());
        Vertex<Long> numeric = Vertex.create(random.nextLong());
        graph.add(object);
        graph.add(Arrow.create(object, numeric));
    }

    /**
     * An attempt to add an edge whose second vertex is not in the graph
     * should result in an exception.
     * @throws IllegalArgumentException expected behavior
     */
    @Test(expected = IllegalArgumentException.class)
    public void addEdgeThrowsExceptionWhenSecondEndpointNotInGraph() {
        Graph graph = AdjacencyTable.create();
        Vertex<Object> object = Vertex.create(new Object());
        Vertex<Long> numeric = Vertex.create(random.nextLong());
        graph.add(numeric);
        graph.add(Arrow.create(object, numeric));
    }

    /**
     * An attempt to add an edge twice should result in an exception.
     * @throws IllegalArgumentException expected behavior
     */
    @Test(expected = IllegalArgumentException.class)
    public void addEdgeThrowsExceptionOnDuplicateEdge() {
        Graph graph = AdjacencyTable.create();
        Vertex<Object> vertex = Vertex.create(null);
        graph.add(vertex);
        graph.add(Arrow.create(vertex, vertex));

        // Because of equals(), this is a duplicate
        graph.add(Arrow.create(vertex, vertex));
    }

    /**
     * Weighted edges are currently not supported by the implementation.
     * @throws IllegalArgumentException expected behavior
     */
    @Test(expected = IllegalArgumentException.class)
    public void addWeightedEdgeThrowsException() {
        Vertex<Boolean> vertex = Vertex.create(null);
        Comparable weight = random.nextLong();
        Edge edge = Arrow.create(vertex, vertex, weight);
        AdjacencyTable.create().add(edge);
    }

    /**
     * Adding an arrow adds one adjacency record (from source to target).
     */
    @Test
    public void addArrowAddsSingleAdjacency() {
        Graph graph = AdjacencyTable.create();
        Vertex<Boolean> foo = Vertex.create(random.nextBoolean());
        Vertex<Boolean> bar = Vertex.create(!foo.value());
        graph.add(foo);
        graph.add(bar);
        Arrow arrow = Arrow.create(foo, bar);
        graph.add(arrow);

        Assert.assertTrue(graph.contains(arrow));

        // Line version of the above arrow
        Line line = Line.create(arrow.target(), arrow.source());
        Assert.assertFalse(graph.contains(line));
    }

    /**
     * Adding a line should add two adjacency records (forwars and
     * backwards between the endpoints).
     */
    @Test
    public void addLineAddsTwoAdjacencies() {
        Graph graph = AdjacencyTable.create();
        Vertex<Boolean> foo = Vertex.create(random.nextBoolean());
        Vertex<Boolean> bar = Vertex.create(!foo.value());
        graph.add(foo);
        graph.add(bar);
        Line line = Line.create(foo, bar);

        graph.add(line);

        Assert.assertTrue(graph.contains(line));

        List<Vertex> endpoints = line.endpoints();

        Set<Vertex> firstEndpointNeighbors = graph.neighbors(endpoints.get(0));
        Assert.assertTrue(firstEndpointNeighbors.contains(endpoints.get(1)));

        Set<Vertex> secondEndpointNeighbors =
            graph.neighbors(endpoints.get(1));
        Assert.assertTrue(secondEndpointNeighbors.contains(endpoints.get(0)));
    }

    /**
     * An attempt to remove a null edge should throw an exception.
     * @throws IllegalArgumentException expected behavior
     */
    @Test(expected = IllegalArgumentException.class)
    public void removeEdgeThrowsExceptionWhenArgIsNull() {
        AdjacencyTable.create().remove((Edge) null);
    }

    /**
     * An attempt to remove an edge whose first endpoint is not in the
     * graph should throw an exception.
     * @throws IllegalArgumentException expected behavior
     */
    @Test(expected = IllegalArgumentException.class)
    public void removeEdgeThrowsExceptionWhenFirstEndpointNotInGraph() {
        Vertex<Boolean> first = Vertex.create(random.nextBoolean());
        Vertex<Boolean> second = Vertex.create(!first.value());
        Line line = Line.create(first, second);
        Graph graph = AdjacencyTable.create();
        graph.add(second);

        graph.add(line);
    }

    /**
     * An attempt to remove an edge whose second endpoint is not in the
     * graph should throw an exception.
     * @throws IllegalArgumentException expected behavior
     */
    @Test(expected = IllegalArgumentException.class)
    public void removeEdgeThrowsExceptionWhenSecondEndpointNotInGraph() {
        Vertex<Boolean> first = Vertex.create(random.nextBoolean());
        Vertex<Boolean> second = Vertex.create(!first.value());
        Line line = Line.create(first, second);
        Graph graph = AdjacencyTable.create();
        graph.add(first);

        graph.add(line);
    }

    /**
     * An attempt to remove an edge which is not in the graph should
     * result in an exception.
     * @throws IllegalArgumentException expected behavior
     */
    @Test(expected = IllegalArgumentException.class)
    public void removeEdgeThrowsExceptionWhenEdgeNotInGraph() {
        Vertex<Long> vertex = Vertex.create(null);
        Edge edge = Arrow.create(vertex, vertex);
        Graph graph = AdjacencyTable.create();
        graph.add(vertex);

        // Throws
        graph.remove(edge);
    }

    /**
     * Removing a line which exists in the graph should succesed in
     * making it not exist in the graph.
     */
    @Test
    public void removeLineRemovesLineFromGraph() {
        Long offset = 0L;
        Long value = random.nextLong();
        Vertex<Long> first = Vertex.create(value + offset++);
        Vertex<Long> second = Vertex.create(value + offset++);
        Line line = Line.create(first, second);
        Graph graph = AdjacencyTable.create();
        graph.add(first);
        graph.add(second);
        graph.add(line);
        Assert.assertTrue(graph.contains(line));

        graph.remove(line);
        Assert.assertFalse(graph.contains(line));
    }

    /**
     * Removing an arrow which exists in the graph should succesed in
     * making it not exist in the graph.
     */
    @Test
    public void removeArrowRemovesArrowFromGraph() {
        Long offset = 0L;
        Long value = random.nextLong();
        Vertex<Long> first = Vertex.create(value + offset++);
        Vertex<Long> second = Vertex.create(value + offset++);
        Arrow arrow = Arrow.create(first, second);
        Graph graph = AdjacencyTable.create();
        graph.add(first);
        graph.add(second);
        graph.add(arrow);
        Assert.assertTrue(graph.contains(arrow));

        graph.remove(arrow);
        Assert.assertFalse(graph.contains(arrow));
    }

    /**
     * contains() should throw when provided a null arg.
     * @throws IllegalArgumentException expected behavior
     */
    @Test(expected = IllegalArgumentException.class)
    public void containsEdgeThrowsExceptionWhenArgIsNull() {
        AdjacencyTable.create().contains((Edge) null);
    }

    /**
     * contains() should throw when the first endpoint in the passed
     * edge is not in the graph.
     * @throws IllegalArgumentException expected behavior
     */
    @Test(expected = IllegalArgumentException.class)
    public void containsEdgeThrowsExceptionWhenFirstEndpointNotInGraph() {
        Graph graph = AdjacencyTable.create();
        Vertex<Boolean> first = Vertex.create(random.nextBoolean());
        Vertex<Boolean> second = Vertex.create(!first.value());
        graph.add(second);
        graph.contains(Line.create(first, second)); // throws
    }

    /**
     * contains() should throw when the second endpointin the passed
     * edge is not in the graph.
     * @throws IllegalArgumentException expected behavior
     */
    @Test(expected = IllegalArgumentException.class)
    public void containsEdgeThrowsExceptionWhenSecondEndpointNotInGraph() {
        Graph graph = AdjacencyTable.create();
        Vertex<Boolean> first = Vertex.create(random.nextBoolean());
        Vertex<Boolean> second = Vertex.create(!first.value());
        graph.add(first);
        graph.contains(Line.create(first, second)); // throws
    }

    /**
     * contains() should return true when checking an arrow which is
     * known to be in the graph.
     */
    @Test
    public void containsArrowReturnsTrueWhenArrowInGraph() {
        Graph graph = AdjacencyTable.create();
        Vertex<Boolean> vertex = Vertex.create(random.nextBoolean());
        graph.add(vertex);
        Arrow arrow = Arrow.create(vertex, vertex);
        graph.add(arrow);

        Assert.assertTrue(graph.contains(arrow));
    }

    /**
     * contains() should return false when checking an arrow which is
     * known not to be in the graph.
     */
    @Test
    public void containsArrowReturnsFalseWhenArrowNotInGraph() {
        Graph graph = AdjacencyTable.create();
        Vertex<Boolean> vertex = Vertex.create(null);
        graph.add(vertex);
        Assert.assertFalse(graph.contains(Arrow.create(vertex, vertex)));
    }

    /**
     * contains() should return true when checking a line which is known
     * to be in the graph.
     */
    @Test
    public void containsLineReturnsTrueWhenLineInGraph() {
        Graph graph = AdjacencyTable.create();
        Vertex<Boolean> first = Vertex.create(random.nextBoolean());
        Vertex<Boolean> second = Vertex.create(!first.value());
        graph.add(first);
        graph.add(second);
        Line line = Line.create(first, second);
        graph.add(line);

        Assert.assertTrue(graph.contains(line));
    }

    /**
     * contains() should return false when checking a line which is
     * known not to be in the graph.
     */
    @Test
    public void containsLineReturnsFalseWhenLineNotInGraph() {
        Graph graph = AdjacencyTable.create();
        Vertex<Boolean> first = Vertex.create(random.nextBoolean());
        Vertex<Boolean> second = Vertex.create(!first.value());
        graph.add(first);
        graph.add(second);

        Assert.assertFalse(graph.contains(Line.create(first, second)));
    }

    /**
     * After a line is added to a graph, arrow may be constructed which
     * look like half of the adjacency relationship of the line. In
     * these cases, the forward arrow and backward arrow will cause
     * contains() to return true. This is behaviour is not immediately
     * intuitive, and this test is left in place to document it.
     *
     * This is not great, but is acceptable so long as the edges do not
     * have weights (in this case, the sum of a forward and reverse
     * arrow are definitely not the same as a line.)
     */
    @Test
    public void containsArrowReturnsFalseWhenOriginallyAddedAsLine() {
        Graph graph = AdjacencyTable.create();
        Vertex<Boolean> first = Vertex.create(random.nextBoolean());
        Vertex<Boolean> second = Vertex.create(!first.value());
        graph.add(first);
        graph.add(second);
        Line line = Line.create(first, second);
        graph.add(line);
        Assert.assertTrue(graph.contains(line));

        Arrow forwardArrow = Arrow.create(first, second);
        Assert.assertTrue(graph.contains(forwardArrow));

        Arrow backwardArrow = Arrow.create(second, first);
        Assert.assertTrue(graph.contains(backwardArrow));
    }

    /**
     * After an arrow is added to a graph, a line may be constructed
     * from the same endpoints, thus looking similar. In this case,
     * contains() must return false when asked about the similar-looking
     * line.
     */
    @Test
    public void containsLineReturnsFalseWhenOriginallyAddedAsArrow() {
        Graph graph = AdjacencyTable.create();
        Vertex<Boolean> first = Vertex.create(random.nextBoolean());
        Vertex<Boolean> second = Vertex.create(!first.value());
        graph.add(first);
        graph.add(second);
        Arrow arrow = Arrow.create(first, second);
        graph.add(arrow);
        Assert.assertTrue(graph.contains(arrow));

        Assert.assertFalse(graph.contains(Line.create(first, second)));
        Assert.assertFalse(graph.contains(Line.create(second, first)));
    }
}
