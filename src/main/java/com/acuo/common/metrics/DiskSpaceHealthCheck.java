package com.acuo.common.metrics;

import com.codahale.metrics.health.HealthCheck;
import com.sun.management.OperatingSystemMXBean;

import javax.inject.Inject;
import javax.inject.Named;
import java.lang.management.ManagementFactory;

public class DiskSpaceHealthCheck extends HealthCheck {

	private static final OperatingSystemMXBean OS_BEAN = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
	private final long defaultThreshold = 10 * 1024 * 1024;//10 GB default threshold
	private final long threshold;

	@Inject
	public DiskSpaceHealthCheck(@Named("acuo.healthchecks.disk.threshold") long threshold) {
		this.threshold = threshold;
	}

	@Override
	protected Result check() throws Exception {
		long freeSpace = OS_BEAN.getFreePhysicalMemorySize();
		if (freeSpace >= threshold) {
			return Result.healthy();
		}
		return Result.unhealthy("Free disk space below threshold. Available: %d bytes [threshold: %d bytes]", freeSpace, threshold);
	}
}