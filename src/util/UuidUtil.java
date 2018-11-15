package util;
import java.util.UUID;
public class UuidUtil {
    public static String Uuid() {
           return UUID.randomUUID().toString().replaceAll("-", "");
        }
}
