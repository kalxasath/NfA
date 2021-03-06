package com.github.nfcforandroid.writers.ndef.wkt;

import java.io.UnsupportedEncodingException;

import android.nfc.NdefRecord;

import com.github.nfcforandroid.api.INfaWriter;
import com.github.nfcforandroid.records.ndef.wkt.UriRecord;
import com.github.nfcforandroid.records.ndef.wkt.UriSchemeEnum;
import com.github.nfcforandroid.writers.ndef.AbstractNdefWriter;

/**
 * @author jefBinomed
 * 
 * 
 *         {@link INfaWriter} for uri data.
 * 
 */
public class UriWriter extends AbstractNdefWriter<UriRecord> {

	protected UriWriter() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.github.nfcforandroid.api.INfaWriter#getNdefRecord()
	 */
	public NdefRecord getNdefRecord() {
		if (!(record instanceof UriRecord)) {
			throw new IllegalArgumentException("Expected UriRecord but got : " + record.getClass());
		}

		String uri = record.getUri().toString();
		byte[] uriAsBytes = getUriAsBytes(uri);

		int abbreviateIndex = getAbbreviateIndex(uri.toLowerCase());
		int uriCopyOffset = UriSchemeEnum.values()[abbreviateIndex].getScheme().length();
		byte[] payload = new byte[uriAsBytes.length + 1 - uriCopyOffset];
		payload[0] = (byte) abbreviateIndex;
		System.arraycopy(uriAsBytes, uriCopyOffset, payload, 1, uriAsBytes.length - uriCopyOffset);

		NdefRecord ndefRecord = new NdefRecord(NdefRecord.TNF_WELL_KNOWN, NdefRecord.RTD_URI, record.getId(), payload);

		return ndefRecord;

	}

	private byte[] getUriAsBytes(String uri) {
		try {
			return uri.getBytes(UriRecord.DEFAULT_URI_CHARSET.name());
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}

	private int getAbbreviateIndex(String uri) {
		int maxLength = 0;
		int abbreviateIndex = 0;
		for (int x = 1; x < UriSchemeEnum.values().length; x++) {

			String abbreviablePrefix = UriSchemeEnum.values()[x].getScheme();

			if (uri.startsWith(abbreviablePrefix) && abbreviablePrefix.length() > maxLength) {
				abbreviateIndex = x;
				maxLength = abbreviablePrefix.length();
			}
		}
		return abbreviateIndex;
	}

}
