package com.hs.coursemanagerback.utils;

public class StringDocumentsUtils {

    private static final int CHAR_LIMIT = 100;

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
                limit = CHAR_LIMIT;
            } else if (i + 1 < words.length && words[i + 1].contains(System.lineSeparator())) {
                justifiedText.append(justifiedLine);
                justifiedLine = new StringBuilder();
                limit = CHAR_LIMIT;
            }
        }

        return justifiedText.toString().split(System.lineSeparator());
    }

    public static String[] separate(String str) {
        return separate(str, CHAR_LIMIT);
    }

    public static String replace(String html, String toReplace, String text, int... firstLimitParam) {
        int firstLimit = CHAR_LIMIT;
        if (firstLimitParam.length == 1) {
            firstLimit = firstLimitParam[0];
        }
        String[] strArray = StringDocumentsUtils.separate(text, firstLimit);
        if (strArray.length > 1) {
            html = html.replace(toReplace + "1", strArray[0]);
            StringBuilder sb = new StringBuilder();
            for (int i = 1; i < strArray.length; i++) {
                sb.append("<div>\n" +
                        "        <u>\n" +
                        "            <pre class=\"line font-set\">&nbsp;&nbsp;&nbsp;&nbsp; " + strArray[i] + "                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            </pre>\n" +
                        "        </u>&nbsp;\n" +
                        "    </div>");
            }
            html = html.replace(toReplace + "2", sb + "<div class=\"space\"></div>");
        } else {
            html = html.replace(toReplace + "1", text)
                       .replace(toReplace + "2", "");
        }

        return html;
    }

}
