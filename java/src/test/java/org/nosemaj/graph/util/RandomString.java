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

import java.util.UUID;

/**
 * As a utility for testing, provide a mechanism to obtain a random
 * string (since java.util.Random does not provide one).
 */
public final class RandomString {

    /**
     * Dis-allows construction of this utility class.
     */
    private RandomString() {
        String message = "No instances are allowed.";
        throw new IllegalStateException(message);
    }

    /**
     * Gets the next random string.
     * @return A random string
     */
    public static String nextString() {
        return UUID.randomUUID().toString();
    }
}
