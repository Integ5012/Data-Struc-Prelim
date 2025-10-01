package prelim;

public class EmailValidator {
    private static final String[] ALLOWED_DOMAINS = new String[] {
        "gmail.com", "hotmail.com", "slu.edu.ph", "yahoo.com", "outlook.com", "icloud.com"
    };

    public static boolean isValidEmail(String var0) {
        if (var0 != null && !var0.trim().isEmpty()) {
            if (!var0.contains("@")) {
                return false;
            } else {
                String[] var1 = var0.split("@");
                if (var1.length != 2) {
                    return false;
                } else {
                    String var2 = var1[0];
                    String var3 = var1[1];
                    if (var2.trim().isEmpty()) {
                        return false;
                    } else if (!isAllowedDomain(var3)) {
                        return false;
                    } else {
                        return var0.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");
                    }
                }
            }
        } else {
            return false;
        }
    }

    private static boolean isAllowedDomain(String var0) {
        if (var0 == null) return false;
        String d = var0.trim().toLowerCase();
        for (String allowed : ALLOWED_DOMAINS) {
            if (allowed.equalsIgnoreCase(d)) return true;
        }
        return false;
    }

    public static String getAllowedDomains() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < ALLOWED_DOMAINS.length; i++) {
            if (i > 0) sb.append(", ");
            sb.append(ALLOWED_DOMAINS[i]);
        }
        return sb.toString();
    }
}
