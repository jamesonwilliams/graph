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
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Random;

/**
 * Tests the Sum class.
 */
public final class SumTest {

    /**
     * 1, as an int.
     */
    private static final Integer ONE_INTEGER = Integer.valueOf(1);

    /**
     * 2, as a long.
     */
    private static final Long TWO_LONG = Long.valueOf(2L);

    /**
     * 3, as a float.
     */
    private static final Float THREE_FLOAT = Float.valueOf(3.0f);

    /**
     * 4, as a double.
     */
    private static final Double FOUR_DOUBLE = Double.valueOf(4.0);

    /**
     * The sume of the above, as a double.
     */
    private static final BigDecimal MIXED_SUM = BigDecimal.valueOf(10.0);

    /**
     * Generate random numbers for test data.
     */
    private Random random;

    /**
     * Sets up the random number generator.
     */
    @Before
    public void setup() {
        this.random = new Random();
    }

    /**
     * As a utility class, Sum must not expose a public constructor.
     */
    @Test
    public void constructorShouldHavePrivateAcccess() {
        Constructor<Sum>[] constructors =
            PrivateConstructor.getConstructors(Sum.class);

        Assert.assertEquals(1, constructors.length);
        Assert.assertFalse(constructors[0].isAccessible());
    }

    /**
     * As a utility class, Sum should refuse to construct even if
     * someone manages to call its (private) constructor.
     * @throws UnsupportedOperationException
     *         This is the expected test pass behavior
     */
    @Test(expected = UnsupportedOperationException.class)
    public void constructorShouldThrowExceptionWhenInvoked() {
        PrivateConstructor.newInstance(Sum.class);
    }

    /**
     * Passing null into of() should result in an
     * IllegalArgumentException.
     */
    @Test(expected = IllegalArgumentException.class)
    public void ofNullCollectionThrowsIllegalArgument() {
        Sum.of(null);
    }

    /**
     * The sum of an empty collection should be 0.
     */
    @Test
    public void ofEmptyCollectionReturnsZero() {
        BigDecimal sum = Sum.of(Arrays.asList());
        Assert.assertEquals(BigDecimal.valueOf(0L), sum);
    }

    /**
     * The sum of some mixed-type numbers should be produced correctly.
     */
    @Test
    public void ofMixedCollectionYieldsValidResult() {
        BigDecimal sum = Sum.of(Arrays.asList(
            ONE_INTEGER, TWO_LONG, THREE_FLOAT, FOUR_DOUBLE
        ));

        Assert.assertEquals(MIXED_SUM, sum);
    }
}

