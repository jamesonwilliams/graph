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

import java.math.BigDecimal;
import java.util.Collection;

/**
 * Sum handles summation of numbers.
 */
public final class Sum {

    /**
     * Dis-allows instantiation of this utility class.
     */
    private Sum() {
        String message = "No instances are allowed for this utility class.";
        throw new UnsupportedOperationException(message);
    }

    /**
     * Gets the sum of a collection of numbers.
     * @param numbers Numbers to sum
     * @return The sum of the numbers as a BigDecimal
     */
    public static BigDecimal of(final Collection<? extends Number> numbers) {
        Preconditions.notNull(numbers, "numbers == null.");

        BigDecimal sum = new BigDecimal(0);

        for (Number number : numbers) {
            sum = sum.add(new BigDecimal(number.toString()));
        }

        return sum;
    }
}
