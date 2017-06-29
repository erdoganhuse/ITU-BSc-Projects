package yoneylem;

import java.util.Comparator;

public class Compp implements Comparator<kombinasyon> {
    @Override
    public int compare(kombinasyon o1, kombinasyon o2) {
        return (""+o1.toplamsure).compareTo(""+o2.toplamsure);
    }
}
