package com.example.a0a00uj.asdashoppingassistant.singleton;

import java.util.ArrayList;
import java.util.List;

public class AppData {
    public static final List<String> scannedBarcodeList = new ArrayList<>();
    public static final String LOGIN_URL_PART_1 = "http://172.29.220.48:8080/asda/p13n/v1/recommendations?email=";
    public static final String LOGIN_URL_PART_2 = "&placement=home_page";
    public static final String SCAN_URL = "http://172.29.220.48:8080/asda/p13n/v1/recommendations?email=s0t00p2&placement=item_page.pdp1&currSku=";
    public static final String COMPLETE_URL = "http://172.29.220.48:8080/asda/p13n/v1/recommendations?email=s0t00p2&placement=home_page.beforeyougo1&cartContents=";
}
