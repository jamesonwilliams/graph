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

package org.nosemaj.graph.util;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

/**
 * Tests the ImmutableList utility.
 */
public final class ImmutableListTest {

    /**
     * Specific values are meaningless in the scope of this test, so we
     * fill them out at random.
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
     * As a utility class, ImmutableList must not expose a public
     * constructor.
     */
    @Test
    public void constructorShouldHavePrivateAcccess() {
        Constructor<ImmutableList>[] constructors =
            PrivateConstructor.getConstructors(ImmutableList.class);

        Assert.assertEquals(1, constructors.length);
        Assert.assertFalse(constructors[0].isAccessible());
    }

    /**
     * As a utility class, ImmutableList should refuse to construct even
     * if someone manages to call its (private) constructor.
     * @throws UnsupportedOperationException
     *         This is the expected test pass behavior
     */
    @Test(expected = UnsupportedOperationException.class)
    public void constructorShouldThrowExceptionWhenInvoked() {
        PrivateConstructor.newInstance(ImmutableList.class);
    }

    /**
     * Creating an immutable list via a variable argument list should
     * result in a list of the expected size and element ordering.
     */
    @Test
    public void ofVarArgsShouldPopulateList() {
        String first = RandomString.nextString();
        String second = RandomString.nextString();

        List<String> list = ImmutableList.of(first, second);

        // Validate the list construction
        Assert.assertNotNull(list);
        Assert.assertEquals(2, list.size());
        Assert.assertEquals(first, list.get(0));
        Assert.assertEquals(second, list.get(1));
    }

    /**
     * Creating a list from a variable argument list should result in a
     * list which cannot be modified.
     * @throws UnsupportedOperationException expected outcome
     */
    @Test(expected = UnsupportedOperationException.class)
    public void ofVarArgsReturnsUnmodifiableList() {
        ImmutableList.of(RandomString.nextString())
            .set(0, RandomString.nextString());
    }

    /**
     * Creating a list from a varaible argument lsit should result ina
     * list which cannot be grown.
     * @throws UnsupportedOperationException expected outcome
     */
    @Test(expected = UnsupportedOperationException.class)
    public void ofArrayReturnsUngrowableList() {
        ImmutableList.of(RandomString.nextString())
            .add(RandomString.nextString());
    }

    /**
     * Creating an immuable list of via a collection of items should
     * result in a list which contains all the items from the
     * collection, but no additional item.
     */
    @Test
    public void ofCollectionShouldPopulateList() {
        String first = RandomString.nextString();
        String second = RandomString.nextString();
        Collection<String> collection = new HashSet<>();
        collection.add(first);
        collection.add(second);

        List<String> list = ImmutableList.of(collection);

        // Validate the list construction
        Assert.assertNotNull(list);
        Assert.assertEquals(2, list.size());
        Assert.assertTrue(list.contains(first));
        Assert.assertTrue(list.contains(second));
    }

    /**
     * Creating an immutable list from a collection of items should
     * result in a list which cannot be modified.
     * @throws UnsupportedOperationException expected outcome
     */
    @Test(expected = UnsupportedOperationException.class)
    public void ofCollectionReturnsUnmodifiableList() {
        ImmutableList.of(Arrays.asList(RandomString.nextString()))
            .add(RandomString.nextString());
    }

    /**
     * Creating an immutable list from a collection of items should
     * result in a list which cannot be grown.
     * @throws UnsupportedOperationException expected outcome
     */
    @Test(expected = UnsupportedOperationException.class)
    public void ofCollectionReturnsUngrowableList() {
        ImmutableList.of(Arrays.asList(RandomString.nextString()))
            .set(0, RandomString.nextString());
    }
}

