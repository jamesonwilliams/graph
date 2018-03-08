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

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Random;

/**
 * Tests the Weight class.
 */
public final class WeightTest {

    /**
     * A random number generator used to obtain arbitrary weight values.
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
     * Creating a weight from value should result in a weight that
     * stores the expected value.
     */
    @Test
    public void ofShouldReturnValidWeight() {
        Float value = random.nextFloat();
        Assert.assertEquals(value, Weight.of(value).value(), 0f);
    }

    /**
     * It should be alright to create weight with null value.
     */
    @Test
    public void createFromNullShouldReturnWeight() {
        Assert.assertEquals(null, Weight.of(null).value());
    }

    /**
     * A weight should be equal to itself.
     */
    @Test
    public void equalsShouldReturnTrueWhenWeightIsSelf() {
        Weight<Float> weight = Weight.of(random.nextFloat());
        Assert.assertEquals(weight, weight);
    }

    /**
     * A weight must not be equal to another weight if they have
     * different values.
     */
    @Test
    public void equalsShouldReturnFalseWhenWeightHasDifferentValue() {
        int value = random.nextInt();
        Integer one = new Integer(value);
        Integer two = new Integer(++value);
        Assert.assertNotEquals(Weight.of(one), Weight.of(two));
    }

    /**
     * A weight should be equal to another weight provided that the
     * store the same value.
     */
    @Test
    public void equalsShouldReturnTrueWhenWeightHasSameValue() {
        Long value = random.nextLong();
        Assert.assertEquals(Weight.of(value), Weight.of(value));
    }

    /**
     * A weight is never equal to an object that is not a weight.
     */
    @Test
    public void equalsShouldReturnFalseWhenArgumentIsDifferentObjectClass() {
        Double value = random.nextDouble();
        Assert.assertNotEquals(Weight.of(value), Double.valueOf(value));
    }

    /**
     * A weight is never equal to a weight of a different template type.
     */
    @Test
    public void equalsShouldReturnFalseWhenWeightUsesDifferentTemplate() {
        int value = random.nextInt();
        Weight<Integer> weight = Weight.of(value);
        Weight<Long> other = Weight.of((long) value);

        Assert.assertNotEquals(weight, other);
    }

    /**
     * An weight that exists is never equal to a weight that does not
     * exist.
     */
    @Test
    public void equalsShouldReturnFalseWhenArgumentIsNull() {
        Assert.assertNotEquals(Weight.of(BigInteger.valueOf(0)), null);
    }

    /**
     * Weights with different values should have different hash codes.
     */
    @Test
    public void hashCodeShouldReturnDistinctHashesForDistinctWeights() {
        Weight<BigDecimal> one =
            Weight.of(BigDecimal.valueOf(random.nextFloat()));
        Weight<BigDecimal> two =
            Weight.of(BigDecimal.valueOf(random.nextFloat()));
        Assert.assertNotEquals(one.hashCode(), two.hashCode());
    }

    /**
     * Weights that store the same value should have the same hash
     * codes.
     */
    @Test
    public void hashCodeShouldReturnSameHashesForSameWeights() {
        Byte sameValue = Byte.valueOf((byte) 0);
        Weight<Byte> one = Weight.of(sameValue);
        Weight<Byte> two = Weight.of(sameValue);

        Assert.assertEquals(one.hashCode(), two.hashCode());
    }
}
