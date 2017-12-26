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

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * A test utility which provides for access and instantiation of private
 * constructors.
 */
public final class PrivateConstructor {

    /**
     * Dis-allows construction of this utility class.
     */
    private PrivateConstructor() {
        String message = "Instances of PrivateConstructor are not allowed.";
        throw new IllegalStateException(message);
    }

    /**
     * Gets the declared constructors for a given class.
     * @param clazz Class for which to obtain declared constructors
     * @param <T> The type of the class
     * @return Declared constructors array, potentially empty
     */
    @SuppressWarnings("unchecked") // Explicit cast
    public static <T> Constructor<T>[] getConstructors(final Class<T> clazz) {
        return (Constructor<T>[]) clazz.getDeclaredConstructors();
    }

    /**
     * Creates a new instance of a class by using its private
     * constructor.
     * @param clazz The class for which a new instance is desired
     * @param <T> The type of the class
     * @return A new instance of the class, of null if one cannot be
     *         created
     */
    public static <T> T newInstance(final Class<T> clazz) {
        Constructor<T>[] constructors = getConstructors(clazz);

        Constructor<T> constructor = constructors[0];
        if (!constructor.isAccessible()) {
            constructor.setAccessible(true);
        }

        T instance;

        try {
            instance = constructor.newInstance();
        } catch (InstantiationException instantiationException) {
            instance = null;
        } catch (IllegalAccessException illegalAccessException) {
            instance = null;
        } catch (InvocationTargetException invocationTargetException) {
            instance = null;
            Throwable cause = invocationTargetException.getCause();
            if (cause instanceof RuntimeException) {
                throw (RuntimeException) cause;
            }
        }

        return instance;
    }
}
