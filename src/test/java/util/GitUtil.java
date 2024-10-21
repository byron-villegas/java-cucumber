package util;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class GitUtil {
    public static String getCurrentRepositoryUrl() {
        String currentRepositoryUrl = "no definido";

        if(PipelineUtil.isRunning()) {
            if(PipelineUtil.getPlatform().equals("GITLAB")) {
                currentRepositoryUrl = System.getenv("CI_PROJECT_URL");
            } else {
                currentRepositoryUrl = System.getenv("GITHUB_SERVER_URL") + "/" + System.getenv("GITHUB_REPOSITORY_OWNER") + "/" + System.getenv("GITHUB_REPOSITORY");
            }
        } else {
            try {
                Process process = Runtime.getRuntime().exec("git config --get remote.origin.url");
                process.waitFor();

                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(process.getInputStream()));

                currentRepositoryUrl = reader.readLine().replace(".git", "");
            } catch (Exception ex) {
                System.err.println("Error al obtener la url del repositorio: " + ex.getMessage());
            }
        }

        return currentRepositoryUrl;
    }

    public static String getCurrentBranch() {
        String currentBranch = "no definido";

        if(PipelineUtil.isRunning()) {
            if(PipelineUtil.getPlatform().equals("GITLAB")) {
                currentBranch = System.getenv("CI_COMMIT_BRANCH");
            } else {
                currentBranch = System.getenv("GITHUB_REF_NAME");
            }
        } else {
            try {
                Process process = Runtime.getRuntime().exec("git rev-parse --abbrev-ref HEAD");
                process.waitFor();

                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(process.getInputStream()));

                currentBranch = reader.readLine();
            } catch (Exception ex) {
                System.err.println("Error al obtener el branch actual: " + ex.getMessage());
            }
        }

        return currentBranch;
    }

    public static String getCurrentUsername() {
        String currentUsername = "no definido";

        if(PipelineUtil.isRunning()) {
            if(PipelineUtil.getPlatform().equals("GITLAB")) {
                currentUsername = System.getenv("GITLAB_USER_NAME");
            } else {
                currentUsername = System.getenv("GITHUB_TRIGGERING_ACTOR");
            }
        } else {
            try {
                Process process = Runtime.getRuntime().exec("git config user.name");
                process.waitFor();

                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(process.getInputStream()));

                currentUsername = reader.readLine();
            } catch (Exception ex) {
                System.err.println("Error al obtener el nombre del usuario actual: " + ex.getMessage());
            }
        }

        return currentUsername;
    }

    public static String getCurrentUserEmail() {
        String currentUsername = "no definido";

        if(PipelineUtil.isRunning()) {
            if(PipelineUtil.getPlatform().equals("GITLAB")) {
                currentUsername = System.getenv("GITLAB_USER_EMAIL");
            } else {
                currentUsername = System.getenv("GITHUB_TRIGGERING_ACTOR");
            }
        } else {
            try {
                Process process = Runtime.getRuntime().exec("git config user.email");
                process.waitFor();

                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(process.getInputStream()));

                currentUsername = reader.readLine();
            } catch (Exception ex) {
                System.err.println("Error al obtener el email del usuario actual: " + ex.getMessage());
            }
        }

        return currentUsername;
    }
}