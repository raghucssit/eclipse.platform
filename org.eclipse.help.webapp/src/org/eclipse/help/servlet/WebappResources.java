package org.eclipse.help.servlet;
/*
 * (c) Copyright IBM Corp. 2000, 2002.
 * All Rights Reserved.
 */

import java.util.*;

import javax.servlet.http.HttpServletRequest;

import org.eclipse.core.boot.*;

/**
 * Uses a resource bundle to load images and strings from
 * a property file in a documentation plugin
 */
public class WebappResources {

	// resource bundles indexed by locale
	private static HashMap resourceBundleTable = new HashMap();
	/**
	 * Resources constructor.
	 */
	protected WebappResources() {
		super();
	}

	/**
	 * Returns a string from a property file.
	 * It uses 'name' as a the key to retrieve from the webapp.properties file.
	 * @param request HttpServletRequest or null; default locale will be used if null passed
	 */
	public static String getString(String name, HttpServletRequest request) {
		Locale locale =
			request == null ? getDefaultLocale() : request.getLocale();

		// check cache
		ResourceBundle bundle =
			(ResourceBundle) resourceBundleTable.get(locale);

		// load bundle
		if (bundle == null) {
			bundle = ResourceBundle.getBundle("webapp", locale);
			if (bundle != null) {
				resourceBundleTable.put(locale, bundle);
			} else {
				return name;
			}
		}
		// get value
		try {
			return bundle.getString(name);
		} catch (MissingResourceException mre) {
			return name;
		}
	}
	
	private static Locale getDefaultLocale() {
		String nl = BootLoader.getNL();
		// sanity test
		if (nl == null)
			return Locale.getDefault();
		
		// break the string into tokens to get the Locale object
		StringTokenizer locales = new StringTokenizer(nl,"_");
		if (locales.countTokens() == 1)
			return new Locale(locales.nextToken(), "");
		else if (locales.countTokens() == 2)
			return new Locale(locales.nextToken(), locales.nextToken());
		else if (locales.countTokens() == 3)
			return new Locale(locales.nextToken(), locales.nextToken(), locales.nextToken());
		else
			return Locale.getDefault();
	}
}