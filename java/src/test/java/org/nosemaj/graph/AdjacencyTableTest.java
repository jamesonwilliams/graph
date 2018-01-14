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

import org.junit.Test;

/**
 * Tests the AdjacencyTable graph implementation.
 */
public final class AdjacencyTableTest {

    /**
     * The default access constructor should refust construction if it
     * is not passed a valid table to use.
     */
    @Test(expected = IllegalArgumentException.class)
    public void defaultConstructorThrowsExceptionOnNullArgument() {
        new AdjacencyTable(null);
    }

    /**
     * The public constructor must construct an adjacency table.
     */
    @Test
    public void publicConstructorConstructs() {
        new AdjacencyTable();
    }

    /**
     * Calling neighbors with a null argument should throw an excetpion.
     */
    @Test
    public void neighborsThrowsExceptionOnNullArg() {
    }

    /**
     * Calling neighbors for an unknown vertex should throw an
     * exception.
     */
    @Test
    public void neighborsThrowsExceptionForUnknownVertex() {
    }

    /**
     * Calling neighbors for a vertex with no links should result in an
     * empty set of neighbors.
     */
    @Test
    public void neighborsReturnsEmptySetForUnlinkedVertex() {
    }

    /**
     * Calling neighbors should return populated set of neighboring
     * vertices, consistent with the way the graph was constructed.
     */
    @Test
    public void neighborsReturnsCorrectSetForLinkedVertex() {
    }
}
