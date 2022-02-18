package com.hs.coursemanagerback.utils;

public class StringDocumentsUtils {

    public static String[] separate(String str, int firstLimit) {
        StringBuilder justifiedText = new StringBuilder();
        StringBuilder justifiedLine = new StringBuilder();

        int limit = firstLimit;

        str = str.replace("\n", System.lineSeparator());

        String[] words = str.split(" ");

        for (int i = 0; i < words.length; i++) {
            justifiedLine.append(words[i]).append(" ");
            if (i + 1 == words.length || justifiedLine.length() + words[i + 1].length() > limit) {
                justifiedLine.deleteCharAt(justifiedLine.length() - 1);
                justifiedText.append(justifiedLine).append(System.lineSeparator());
                justifiedLine = new StringBuilder();
                limit = 100;
            } else if (i + 1 < words.length && words[i+1].contains(System.lineSeparator())) {
                justifiedText.append(justifiedLine);
                justifiedLine = new StringBuilder();
                limit = 100;
            }
        }
        return justifiedText.toString().split(System.lineSeparator());
    }

    public static String[] separate(String str) {
        return separate(str, 100);
    }

    public static String concatLines(String html, String[] strArray, String toReplace) {
        for (int i = 0; i < 3; i++) {
            if (i < strArray.length) {
                html = html.replace(toReplace + (i + 1), strArray[i]);
            } else {
                html = html.replace(toReplace + (i + 1), "");
            }
        }

        return html;
    }

}
