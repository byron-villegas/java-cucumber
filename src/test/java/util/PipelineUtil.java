package util;

public class PipelineUtil {

    public static String getPlatform() {
        if(System.getenv("CI_RUNNER_ID") != null && !System.getenv("CI_RUNNER_ID").isEmpty()) {
            return "GITLAB";
        } else if (System.getenv("GITHUB_RUN_ID") != null && !System.getenv("GITHUB_RUN_ID").isEmpty()) {
            return "GITHUB";
        } else {
            return "NO DEFINIDO";
        }
    }

    public static boolean isRunning() {
        final String platform = getPlatform();

        return !platform.equals("NO DEFINIDO");
    }
}