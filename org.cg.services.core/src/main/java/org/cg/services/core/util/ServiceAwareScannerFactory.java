package org.cg.services.core.util;

import java.util.HashMap;
import java.util.Map;

import com.google.common.base.Strings;

import io.swagger.config.Scanner;

/**
 * 
 * @author WZ
 *
 */
public class ServiceAwareScannerFactory {
	private static Map<String, Scanner>  SCANNER = new HashMap<String, Scanner>() ;

    public static Scanner getScanner(String serviceId) {
    	if (Strings.isNullOrEmpty(serviceId) ) return null;
        return SCANNER.get(serviceId.trim().replaceAll("[^a-zA-Z]", "").toLowerCase());
    }

    public static void setScanner(String serviceId, Scanner scanner) {
    	if (!Strings.isNullOrEmpty(serviceId) && scanner!=null) {
    		SCANNER.put(serviceId.trim().replaceAll("[^a-zA-Z]", "").toLowerCase(), scanner);
    	}
    }
}
