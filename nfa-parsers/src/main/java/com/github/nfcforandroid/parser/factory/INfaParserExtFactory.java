package com.github.nfcforandroid.parser.factory;

import com.github.nfcforandroid.api.INfaParser;

/**
 * @author jefBinomed
 * 
 *         Factory for getting {@link INfaParser} that are externals types
 * 
 */
public interface INfaParserExtFactory {

	/**
	 * @return a Singleton instance for external parser
	 */
	INfaParser externalParser();

	/**
	 * @return a Singleton instance for external text parser
	 */
	INfaParser externalTextParser();

	/**
	 * @return a Singleton instance for external uri parser
	 */
	INfaParser externalUriParser();

	/**
	 * @return a Singleton instance for android application parser
	 */
	INfaParser androidApplicationParser();

}
