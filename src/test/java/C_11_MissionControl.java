/**
 * MissionControl is the new tool for monitoring Java applications.
 */
public class C_11_MissionControl {
    /*
        See http://www.oracle.com/technetwork/java/javaseproducts/mission-control/java-mission-control-1998576.html
        and https://www.youtube.com/watch?v=ORHVOmxnxbo

        MissionControl "JMC" = JMX Console + FlightRecorder "JFR"

        JMX CONSOLE
        ============

        1. Start MissionControl: [JDK]/bin/jmc.exe

        2. Create launch config with JVM startup flags for flight recorder:
        -XX:+UnlockCommercialFeatures
        -XX:+FlightRecorder

        3. Right-Click in JVM Browser -> Start JMX Console

        4. Overview,
            Triggers,
            Memory -> Heap Histogram (Click on "Refresh Heap Histogram" two times)
            Threads -> Deadlock-Detection + "Lock Owner Name"

        FLIGHT RECORDING
        =================

        1. (within MissionControl) Right-Click in JVM-Browser -> Start Flight Recording
        2. Range Slider
        3. Code -> Hot Methods
     */
}
