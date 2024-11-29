package com.example.codestrike_backend.Services;

import com.example.codestrike_backend.Classes.ServerMetrics;
import com.example.codestrike_backend.Clients.NodeClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;
import java.lang.management.OperatingSystemMXBean;
import java.util.HashMap;

/**
 * Service to retrieve server metrics including memory usage and CPU load.
 * This service encapsulates logic for fetching and processing system-level information.
 */
@Service
public class ServerMetricsService {

    @Autowired
    private NodeClient nodeClient;

    /**
     * Retrieves server metrics including memory usage, CPU load, and available processors.
     * Additionally, this method fetches data from the external NodeClient (e.g., greeting data).
     *
     * @return ServerMetrics containing memory and CPU metrics.
     */
    public ServerMetrics getMetrics() {
        // Create a new ServerMetrics object to hold the server data.
        ServerMetrics metrics = new ServerMetrics();



        // Fetch memory usage details using Java's ManagementFactory.
        MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();
        MemoryUsage heapMemoryUsage = memoryMXBean.getHeapMemoryUsage();
        MemoryUsage nonHeapMemoryUsage = memoryMXBean.getNonHeapMemoryUsage();

        // Fetch system load and available processor count from the operating system.
        OperatingSystemMXBean osBean = ManagementFactory.getOperatingSystemMXBean();
        double systemLoad = osBean.getSystemLoadAverage();
        int availableProcessors = osBean.getAvailableProcessors();

        // Calculate CPU usage percentage.
        // CPU usage is computed based on system load divided by the number of available processors.
        double cpuUsage = systemLoad / availableProcessors * 100;

        // Set the gathered data into the ServerMetrics object.
        // These fields represent memory usage and CPU statistics.
        metrics.setHeapMemoryUsed(heapMemoryUsage.getUsed()); // Used heap memory
        metrics.setHeapMemoryMax(heapMemoryUsage.getMax()); // Max heap memory
        metrics.setNonHeapMemoryUsed(nonHeapMemoryUsage.getUsed()); // Used non-heap memory
        metrics.setNonHeapMemoryMax(nonHeapMemoryUsage.getMax()); // Max non-heap memory
        metrics.setCpuLoad(systemLoad); // Current system load
        metrics.setCpuUsage(cpuUsage); // Calculated CPU usage as a percentage
        metrics.setAvailableProcessors(availableProcessors); // Total number of processors available

        // Return the populated metrics object containing the server's resource usage details.
        return metrics;
    }
}
