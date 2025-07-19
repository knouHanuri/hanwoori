package knou.seoul.hanwoori.common.security;

public class InputValidator {
    private static final String[] DANGEROUS_STRINGS = {
            "<script", "</script", "javascript:", "onerror=", "onload=", "onclick=", "onmouseover=",
            "<iframe", "<img", "<svg", "<object", "<embed", "<style", "<link", "<meta",
            "<body", "<html", "<head",
            "' or '1'='1", "--", ";--", ";", "'", "\"", "/*", "*/", "xp_", "exec",
            "union", "select", "insert", "update", "delete",
            "eval(", "alert(", "prompt(", "confirm(", "document.cookie", "window.location"
    };

    public static boolean containsDangerousContent(String value) {
        if (value == null) return false;

        String lower = value.toLowerCase();

        for (String dangerous : DANGEROUS_STRINGS) {
            if (lower.contains(dangerous)) {
                return true;
            }
        }

        return false;
    }
}
