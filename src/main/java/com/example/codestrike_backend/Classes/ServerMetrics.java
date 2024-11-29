package com.example.codestrike_backend.Classes;

import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for capturing and storing server metrics.
 *
 * This class holds essential system performance metrics such as memory usage,
 * CPU load, and processor availability, which can be used for monitoring and debugging.
 */
@Configuration
public class ServerMetrics {

    // Current heap memory usage in bytes
    private long heapMemoryUsed;

    // Maximum heap memory available in bytes
    private long heapMemoryMax;

    // Current non-heap memory usage in bytes
    private long nonHeapMemoryUsed;

    // Maximum non-heap memory available in bytes
    private long nonHeapMemoryMax;

    // System load average (CPU load over a period of time)
    private double cpuLoad;

    // CPU usage percentage
    private double cpuUsage;

    // Number of processors available to the JVM
    private int availableProcessors;

    // Getters and Setters

    /**
     * @return the current heap memory usage in bytes
     */
    public long getHeapMemoryUsed() {
        return heapMemoryUsed;
    }

    /**
     * @param heapMemoryUsed sets the current heap memory usage in bytes
     */
    public void setHeapMemoryUsed(long heapMemoryUsed) {
        this.heapMemoryUsed = heapMemoryUsed;
    }

    /**
     * @return the maximum heap memory available in bytes
     */
    public long getHeapMemoryMax() {
        return heapMemoryMax;
    }

    /**
     * @param heapMemoryMax sets the maximum heap memory available in bytes
     */
    public void setHeapMemoryMax(long heapMemoryMax) {
        this.heapMemoryMax = heapMemoryMax;
    }

    /**
     * @return the current non-heap memory usage in bytes
     */
    public long getNonHeapMemoryUsed() {
        return nonHeapMemoryUsed;
    }

    /**
     * @param nonHeapMemoryUsed sets the current non-heap memory usage in bytes
     */
    public void setNonHeapMemoryUsed(long nonHeapMemoryUsed) {
        this.nonHeapMemoryUsed = nonHeapMemoryUsed;
    }

    /**
     * @return the maximum non-heap memory available in bytes
     */
    public long getNonHeapMemoryMax() {
        return nonHeapMemoryMax;
    }

    /**
     * @param nonHeapMemoryMax sets the maximum non-heap memory available in bytes
     */
    public void setNonHeapMemoryMax(long nonHeapMemoryMax) {
        this.nonHeapMemoryMax = nonHeapMemoryMax;
    }

    /**
     * @return the system's current CPU load average
     */
    public double getCpuLoad() {
        return cpuLoad;
    }

    /**
     * @param cpuLoad sets the system's current CPU load average
     */
    public void setCpuLoad(double cpuLoad) {
        this.cpuLoad = cpuLoad;
    }

    /**
     * @return the CPU usage percentage
     */
    public double getCpuUsage() {
        return cpuUsage;
    }

    /**
     * @param cpuUsage sets the CPU usage percentage
     */
    public void setCpuUsage(double cpuUsage) {
        this.cpuUsage = cpuUsage;
    }

    /**
     * @return the number of processors available to the JVM
     */
    public int getAvailableProcessors() {
        return availableProcessors;
    }

    /**
     * @param availableProcessors sets the number of processors available to the JVM
     */
    public void setAvailableProcessors(int availableProcessors) {
        this.availableProcessors = availableProcessors;
    }
}
