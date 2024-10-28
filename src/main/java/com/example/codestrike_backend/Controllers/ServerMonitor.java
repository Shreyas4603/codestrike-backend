package com.example.codestrike_backend.Controllers;

import com.example.codestrike_backend.Classes.ServerMetrics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;
import java.lang.management.OperatingSystemMXBean;

@RestController
@RequestMapping("")
public class ServerMonitor {
    @GetMapping("")
    public ResponseEntity<String> checkStatus() {
        return new ResponseEntity<>("Server is running at port 8080", HttpStatus.OK);
    }
    @Autowired
    private ServerMetrics serverMetrics;

    @GetMapping("/metrics")
    public ResponseEntity<?> getMetrics() {
        ServerMetrics metrics = new ServerMetrics();

        // Get memory usage
        MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();
        MemoryUsage heapMemoryUsage = memoryMXBean.getHeapMemoryUsage();
        MemoryUsage nonHeapMemoryUsage = memoryMXBean.getNonHeapMemoryUsage();

        // Get CPU load
        OperatingSystemMXBean osBean = ManagementFactory.getOperatingSystemMXBean();
        double systemLoad = osBean.getSystemLoadAverage();
        int availableProcessors = osBean.getAvailableProcessors();

        // Calculate CPU usage (average load over available processors)
        double cpuUsage = systemLoad / availableProcessors * 100;

        metrics.setHeapMemoryUsed(heapMemoryUsage.getUsed());
        metrics.setHeapMemoryMax(heapMemoryUsage.getMax());
        metrics.setNonHeapMemoryUsed(nonHeapMemoryUsage.getUsed());
        metrics.setNonHeapMemoryMax(nonHeapMemoryUsage.getMax());
        metrics.setCpuLoad(systemLoad);
        metrics.setCpuUsage(cpuUsage);
        metrics.setAvailableProcessors(availableProcessors);
        System.out.println("metrics : "+metrics);
        return new ResponseEntity<>(metrics, HttpStatus.OK);
    }
}
