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
import java.util.Random;

/**
 * Tests the Preconditions utility.
 */
public final class PreconditionsTest {

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
     * As a utility class, Preconditions must not expose a public
     * constructor.
     */
    @Test
    public void constructorShouldHavePrivateAcccess() {
        Constructor<Preconditions>[] constructors =
            PrivateConstructor.getConstructors(Preconditions.class);

        Assert.assertEquals(1, constructors.length);
        Assert.assertFalse(constructors[0].isAccessible());
    }

    /**
     * As a utility class, Preconditions should refuse to construct even
     * if someone manages to call its (private) constructor.
     * @throws UnsupportedOperationException
     *         This is the expected test pass behavior
     */
    @Test(expected = UnsupportedOperationException.class)
    public void constructorShouldThrowExceptionWhenInvoked() {
        PrivateConstructor.newInstance(Preconditions.class);
    }

    /**
     * The notNull precondition must throw an illegal argument exception
     * if it is passed a null argument.
     * @throws IllegalArgumentException
     *         This is the expected test pass behavior
     */
    @Test(expected = IllegalArgumentException.class)
    public void notNullShouldThrowExceptionWhenArgumentIsNull() {
        Preconditions.notNull(null, RandomString.nextString());
    }

    /**
     * The notNull precondition should be a no-op when the argument
     * passed to it is not null.
     */
    @Test
    public void notNullShouldDoNothingWhenArgumentNotNull() {
        Preconditions.notNull(new Object(), RandomString.nextString());
    }

    /**
     * The notEqual precondition should throw an exception if the
     * arguments are not distinct.
     */
    @Test(expected = IllegalArgumentException.class)
    public void notEqualShouldThrowExceptionWhenArgumentsAreEqual() {
        Object object = new Object();
        Preconditions.notEqual(object, object, "Must be unique.");
    }

    /**
     * The notEqual precondition should do nothing is the arguments are
     * distinct.
     */
    @Test
    public void notEqualShouldDoNothingWhenArgumentsDistinct() {
        Preconditions.notEqual(new Object(), new Object(), "must be unique.");
    }

    /**
     * The notEqual precondition should do nothing if only the first
     * argument is null and the second isnt.
     */
    @Test
    public void notEqualShouldDoNothingWhenOnlyFirstArgIsNull() {
        Preconditions.notEqual(null, new Object(), "must not be the same.");
    }

    /**
     * The notEqual precondition should do nothing if only the second
     * argument is null and the first isnt.
     */
    @Test
    public void notEqualShouldDoNothingWhenOnlySecondArgIsNull() {
        Preconditions.notEqual(new Object(), null, "must not be the same.");
    }

    /**
     * The notEqual precondition should throw an exception if both
     * arguments are null, since that is a type of equality, in way.
     */
    @Test(expected = IllegalArgumentException.class)
    public void notEqualShouldThrowExceptionWhenBothArgsNull() {
        Preconditions.notEqual(null, null, "must be unique values.");
    }

    /**
     * The isInstance utility must throw an exception when the argument
     * class types differ.
     */
    @Test(expected = IllegalArgumentException.class)
    public void isInstanceShouldThrowExceptionWhenClassTypesDiffer() {
        Integer integer = random.nextInt();
        Preconditions.isInstance(integer, Boolean.class, "not an integer.");
    }

    /**
     * The isInstance utility must do nothing when the argument class
     * types agree.
     */
    @Test
    public void isInstanceShouldDoNothingWhenClassTypesSame() {
        Integer integer = random.nextInt();
        Preconditions.isInstance(integer, Integer.class, "must be Integer.");
    }

    /**
     * The isInstance utility should do nothing when the object is being
     * checked against a class type which the object inherits.
     */
    @Test
    public void isInstanceShouldDoNothingWhenExtendsSuperClass() {
        Integer integer = random.nextInt();
        Preconditions.isInstance(integer, Object.class, "integer not object?");
    }
}
