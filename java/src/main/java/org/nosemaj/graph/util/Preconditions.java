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

/**
 * Preconditions provides checks that may be used to assert state before
 * carrying out further operations.
 */
public final class Preconditions {

    /**
     * Dis-allows construction of this utility class.
     */
    private Preconditions() {
        String message = "No instances of Precoditions are allowed.";
        throw new UnsupportedOperationException(message);
    }

    /**
     * Checks if the provided object is not null; throws an illegal
     * argument exception if it is, including the provided message.
     * @param object The object to ensure is non-null
     * @param message Message to include in exception, it object is null
     * @throws IllegalArgumentException when provided object is null
     */
    public static void notNull(final Object object, final String message)
            throws IllegalArgumentException {

        check(object != null, message);
    }

    /**
     * Checks that two objects are not equal to one another.
     * @param first An object
     * @param second An object
     * @param message Message to include in thrown exception
     * @throws IllegalArgumentException if the objects are the same
     */
    public static void notEqual(final Object first, final Object second,
            final String message) throws IllegalArgumentException {

        if (first != null) {
            check(!first.equals(second), message);
        } else {
            check(second != null, message);
        }
    }

    /**
     * Checks if the provided object is an instance of the named class;
     * if not, throw an exception with provided error message.
     * @param object Object to check for instance type
     * @param clazz The class that the object should be
     * @param message Message to throw if object isn't type clazz
     * @throws IllegalArgumentException if object is not of type clazz
     */
    public static void isInstance(final Object object,
            final Class<?> clazz, final String message)
            throws IllegalArgumentException {

        check(clazz.isInstance(object), message);
    }

    /**
     * Checks a condition and throws an exception if it isn't so.
     * @param condition Condition to check
     * @param message Message to throw in exception
     * @throws IllegalArgumentException if condition isn't true
     */
    private static void check(final boolean condition, final String message)
            throws IllegalArgumentException {

        if (!condition) {
            throw new IllegalArgumentException(message);
        }
    }
}
