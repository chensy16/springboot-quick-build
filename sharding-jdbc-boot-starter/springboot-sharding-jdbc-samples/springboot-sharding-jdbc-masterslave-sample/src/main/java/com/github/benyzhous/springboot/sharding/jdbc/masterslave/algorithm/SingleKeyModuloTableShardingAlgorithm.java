
package com.github.benyzhous.springboot.sharding.jdbc.masterslave.algorithm;

import com.dangdang.ddframe.rdb.sharding.api.ShardingValue;
import com.dangdang.ddframe.rdb.sharding.api.strategy.table.SingleKeyTableShardingAlgorithm;
import com.google.common.collect.Range;

import java.util.Collection;
import java.util.LinkedHashSet;

public class SingleKeyModuloTableShardingAlgorithm implements SingleKeyTableShardingAlgorithm<Long> {

	@Override
	public String doEqualSharding(final Collection<String> availableTargetNames, final ShardingValue<Long> shardingValue) {
		for (String each : availableTargetNames) {
			if (each.endsWith(shardingValue.getValue() % 4 + "")) {
				return each;
			}
		}
		throw new UnsupportedOperationException();
	}

	@Override
	public Collection<String> doInSharding(final Collection<String> availableTargetNames, final ShardingValue<Long> shardingValue) {
		Collection<String> result = new LinkedHashSet<>(availableTargetNames.size());
		Collection<Long> values = shardingValue.getValues();
		for (Long value : values) {
			for (String each : availableTargetNames) {
				if (each.endsWith(value % 4 + "")) {
					result.add(each);
				}
			}
		}
		return result;
	}

	@Override
	public Collection<String> doBetweenSharding(final Collection<String> availableTargetNames, final ShardingValue<Long> shardingValue) {
		Collection<String> result = new LinkedHashSet<>(availableTargetNames.size());
		Range<Long> range = shardingValue.getValueRange();
		for (Long value = range.lowerEndpoint(); value <= range.upperEndpoint(); value++) {
			for (String each : availableTargetNames) {
				if (each.endsWith(value % 4 + "")) {
					result.add(each);
				}
			}
		}
		return result;
	}
}
