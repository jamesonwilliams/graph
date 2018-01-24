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

/**
 * Directed objects identify a source and a target.
 * @param <S> The type of the source object
 * @param <T> The type of the target object
 */
public interface Directed<S, T> {

    /**
     * Gets the source object.
     * @return The source object
     */
    S source();

    /**
     * Gets the target object.
     * @return The target object
     */
    T target();
}
