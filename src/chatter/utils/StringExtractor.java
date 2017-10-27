package chatter.utils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class StringExtractor {
    public static List<String> extract(String symbol, String string) {
        String str[] = string.split(" ");
        return Arrays.stream(str).filter( x -> x.startsWith(symbol)).collect(Collectors.toList());
    }
}
