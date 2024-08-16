package de.outstare.fortbattleplayer.statistics;

/*
 Copyright (c) 2010 Daniel Raap

 Permission is hereby granted, free of charge, to any person obtaining a copy
 of this software and associated documentation files (the "Software"), to deal
 in the Software without restriction, including without limitation the rights
 to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 copies of the Software, and to permit persons to whom the Software is
 furnished to do so, subject to the following conditions:

 The above copyright notice and this permission notice shall be included in
 all copies or substantial portions of the Software.

 THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 THE SOFTWARE.
 */

import java.util.List;

/**
 * A DataAggregationType determines how a value is calculated for a set of
 * values
 * 
 * @author daniel
 */
enum DataAggregationType {
	/**
	 * the sum of all values
	 */
	SUM {
		@Override
		double aggregate(final List<Integer> values) {
			long sum = 0;
			for (final Integer value : values) {
				sum += value.intValue();
			}
			return sum;
		}
	},
	/**
	 * the sum of all positiv values
	 */
	POSITIVE_SUM {
		@Override
		double aggregate(final List<Integer> values) {
			double sum = 0;
			int intValue;
			for (final Integer value : values) {
				intValue = value.intValue();
				if (intValue > 0) {
					sum += intValue;
				}
			}
			return sum;
		}
	},
	/**
	 * count of values
	 */
	AMOUNT {
		@Override
		double aggregate(final List<Integer> values) {
			return values.size();
		}
	},
	/**
	 * count of positive values
	 */
	POSITIVE_AMOUNT {
		@Override
		double aggregate(final List<Integer> values) {
			double count = 0;
			for (final Integer value : values) {
				if (value.intValue() > 0) {
					count++;
				}
			}
			return count;
		}
	},
	/**
	 * the average of values is the sum divided by the count
	 */
	AVERAGE {
		@Override
		double aggregate(final List<Integer> values) {
			// avoid division by zero
			if (values.isEmpty()) {
				return 0.0;
			}
			return SUM.aggregate(values) / AMOUNT.aggregate(values);
		}
	},
	/**
	 * the average of positive values is the sum divided by the count
	 */
	POSITIVE_AVERAGE {
		@Override
		double aggregate(final List<Integer> values) {
			final double positiveCount = POSITIVE_AMOUNT.aggregate(values);
			// avoid division by zero
			if (positiveCount == 0) {
				return 0.0;
			}
			return POSITIVE_SUM.aggregate(values) / positiveCount;
		}
	};
	/**
	 * @param values
	 * @return
	 */
	abstract double aggregate(List<Integer> values);
}