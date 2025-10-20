package org.grind;

import org.junit.platform.launcher.*;
import org.junit.platform.launcher.core.*;
import org.junit.platform.engine.discovery.*;
import org.junit.platform.reporting.legacy.xml.*;
import org.junit.platform.launcher.listeners.SummaryGeneratingListener;
import org.junit.platform.launcher.listeners.TestExecutionSummary;

import java.io.File;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Path;
import java.util.*;


public class TestTube {

    public static void main(String[] args) throws Exception {
        List<String> testClassNames = Arrays.asList(args).subList(0, args.length);
        
        ClassLoader classLoader = ClassLoader.getSystemClassLoader();

        // Build test discovery request
        LauncherDiscoveryRequestBuilder requestBuilder = LauncherDiscoveryRequestBuilder.request();

        // System.out.println("Effective classpath:");
        // System.out.println(System.getProperty("java.class.path"));

        if (!testClassNames.isEmpty()) {
            for (String className : testClassNames) {
                System.out.println("Adding Class -> " + className);
                requestBuilder.selectors(DiscoverySelectors.selectClass(className));
            }
        } else {
            List<String> allClassNames = findAllClassNames(new File("target/test/"), new File("target/test/"));
            for (String className : allClassNames) {
                System.out.println("Adding Dynamic Class -> " + className);
                requestBuilder.selectors(DiscoverySelectors.selectClass(className));
            }
        }

        LauncherDiscoveryRequest request = requestBuilder.build();

        // Create test launcher
        Launcher launcher = LauncherFactory.create();

        // Set up listeners
        SummaryGeneratingListener summaryListener = new SummaryGeneratingListener();
        Path reportsDir = Path.of(System.getProperty("test.report.output", "reports/"));

        PrintWriter out = new PrintWriter(System.out);
        LegacyXmlReportGeneratingListener xmlListener = new LegacyXmlReportGeneratingListener(reportsDir, out);

        // Register both listeners
        launcher.registerTestExecutionListeners(summaryListener, xmlListener);

        // Execute tests
        launcher.execute(request);

        // Print summary to console
        TestExecutionSummary summary = summaryListener.getSummary();
    
        long total = summary.getTestsFoundCount();
        long succeeded = summary.getTestsSucceededCount();
        long failed = summary.getTestsFailedCount();
        long skipped = summary.getTestsSkippedCount();
        long aborted = summary.getTestsAbortedCount(); // Optional, for more granularity
        long started = summary.getTestsStartedCount(); // May help for more accuracy

        System.out.println("========== Test Summary ==========");
        System.out.println("📄 Total tests:   " + total);
        System.out.println("✅ Passed:        " + succeeded);
        System.out.println("❌ Failed:        " + failed);
        System.out.println("⏩ Skipped:       " + skipped);
        System.out.println("🚫 Aborted:       " + aborted);
        System.out.println("==================================");

        // Optional: print errors or failures
        if (summary.getTotalFailureCount() > 0) {
            System.out.println("\n❌ Some tests failed:");
            for (TestExecutionSummary.Failure failure : summary.getFailures()) {
                System.out.println("- " + failure.getTestIdentifier().getDisplayName());
                System.out.println("  " + failure.getException().getMessage());
            }
        } else {
            System.out.println("\n✅ All tests passed.");
        }

        System.out.println("📄 XML reports written to: " + reportsDir.toAbsolutePath());
    }

    /**
     * Recursively finds all .class files and returns fully qualified class names.
     */
    private static List<String> findAllClassNames(File root, File current) {
        List<String> classNames = new ArrayList<>();
        for (File file : Objects.requireNonNull(current.listFiles())) {
            if (file.isDirectory()) {
                classNames.addAll(findAllClassNames(root, file));
            } else if (file.getName().endsWith(".class") && !file.getName().contains("$")) {
                String relPath = root.toURI().relativize(file.toURI()).getPath();
                String className = relPath.replace("/", ".").replace(".class", "");
                classNames.add(className);
            }
        }
        return classNames;
    }
}
