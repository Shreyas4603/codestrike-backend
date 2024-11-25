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

@Service
public class ServerMetricsService {

    @Autowired
    private  NodeClient nodeClient;


    public ServerMetrics getMetrics() {
        ServerMetrics metrics = new ServerMetrics();
        HashMap<String,String> data=new HashMap<>();
        data=( HashMap<String,String>)nodeClient.greet().getBody();
        System.out.println("hhejkhj : "+data);

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

        // Set metrics data
        metrics.setHeapMemoryUsed(heapMemoryUsage.getUsed());
        metrics.setHeapMemoryMax(heapMemoryUsage.getMax());
        metrics.setNonHeapMemoryUsed(nonHeapMemoryUsage.getUsed());
        metrics.setNonHeapMemoryMax(nonHeapMemoryUsage.getMax());
        metrics.setCpuLoad(systemLoad);
        metrics.setCpuUsage(cpuUsage);
        metrics.setAvailableProcessors(availableProcessors);

        return metrics;
    }
}
