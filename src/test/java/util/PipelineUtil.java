package util;

public class PipelineUtil {

    public static boolean isRunning() {
        final String runnerId = System.getenv("CI_RUNNER_ID");

        return runnerId != null && !runnerId.isEmpty();
    }
}