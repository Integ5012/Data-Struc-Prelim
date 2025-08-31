package prelim;

public class EmailValidator {
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
        // Allow any valid domain instead of restricting to specific ones
        // The domain should not be empty and should contain at least one dot
        return var0 != null && !var0.trim().isEmpty() && var0.contains(".");
    }

    public static String getAllowedDomains() {
        return "Any valid email domain (e.g., gmail.com, hotmail.com, slu.edu.ph, etc.)";
    }
}
