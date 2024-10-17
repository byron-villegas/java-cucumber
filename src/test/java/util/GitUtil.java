package util;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class GitUtil {
    public static String getCurrentRepositoryUrl() {
        String currentRepositoryUrl = "indefinido";

        if (!PipelineUtil.isRunning()) {
            try {
                Process process = Runtime.getRuntime().exec("git config --get remote.origin.url");
                process.waitFor();

                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(process.getInputStream()));

                currentRepositoryUrl = reader.readLine().replace(".git", "");
            } catch (Exception ex) {
                System.err.println("Error al obtener la url del repositorio: " + ex.getMessage());
            }
        } else {
            currentRepositoryUrl = System.getenv("CI_PROJECT_URL");
        }

        return currentRepositoryUrl;
    }

    public static String getCurrentBranch() {
        String currentBranch = "no definido";

        if (!PipelineUtil.isRunning()) {
            try {
                Process process = Runtime.getRuntime().exec("git rev-parse --abbrev-ref HEAD");
                process.waitFor();

                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(process.getInputStream()));

                currentBranch = reader.readLine();
            } catch (Exception ex) {
                System.err.println("Error al obtener el branch actual: " + ex.getMessage());
            }
        } else {
            currentBranch = System.getenv("CI_COMMIT_BRANCH");
        }

        return currentBranch;
    }

    public static String getCurrentUsername() {
        String currentUsername = "no definido";

        if (!PipelineUtil.isRunning()) {
            try {
                Process process = Runtime.getRuntime().exec("git config user.name");
                process.waitFor();

                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(process.getInputStream()));

                currentUsername = reader.readLine();
            } catch (Exception ex) {
                System.err.println("Error al obtener el nombre del usuario actual: " + ex.getMessage());
            }
        } else {
            currentUsername = System.getenv("GITLAB_USER_NAME");
        }

        return currentUsername;
    }

    public static String getCurrentUserEmail() {
        String currentUsername = "no definido";

        if (!PipelineUtil.isRunning()) {
            try {
                Process process = Runtime.getRuntime().exec("git config user.email");
                process.waitFor();

                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(process.getInputStream()));

                currentUsername = reader.readLine();
            } catch (Exception ex) {
                System.err.println("Error al obtener el email del usuario actual: " + ex.getMessage());
            }
        } else {
            currentUsername = System.getenv("GITLAB_USER_EMAIL");
        }

        return currentUsername;
    }
}