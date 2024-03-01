package it.poste.patrimonio.bl.util;

import java.util.Arrays;
import java.util.List;

public class Constants {
    public static final String Y = "S";
    public static final String N = "N";

    //institutes
    public static final String GPM_INST = "1";
    public static final String FOE_INST = "5";

    public static final String STATUS_ESE = "ese";
    public static final String STATUS_INS = "ins";

    public static final String REASON_RIMB = "rimb";
    public static final String REASON_SOTT = "sott";

    public static final String ACTIVE_CHECK = "0";
    public static final List<String> BLOCKED_CHECK= Arrays.asList("8", "9", "10", "11", "12", "13", "14");
    public static final List<String> CLOSED_CHECK= Arrays.asList("1", "2", "3", "4", "5", "6", "7", "15");
}