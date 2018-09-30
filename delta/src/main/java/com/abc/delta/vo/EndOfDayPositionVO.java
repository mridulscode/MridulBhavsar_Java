package com.abc.delta.vo;

/**
 * @author mridul.bhavsar
 */
public class EndOfDayPositionVO {

	private String instrument;

	private String eAccount;
	
	private String iAccount;

	private long eQuantity;

	private long iQuantity;

	private long eNetVolume;

	private long iNetVolume;

	/**
	 * @return the instrument
	 */
	public String getInstrument() {
		return instrument;
	}

	/**
	 * @param instrument
	 *            the instrument to set
	 */
	public void setInstrument(String instrument) {
		this.instrument = instrument;
	}

	/**
	 * @return the eAccount
	 */
	public String geteAccount() {
		return eAccount;
	}

	/**
	 * @param eAccount the eAccount to set
	 */
	public void seteAccount(String eAccount) {
		this.eAccount = eAccount;
	}

	/**
	 * @return the iAccount
	 */
	public String getiAccount() {
		return iAccount;
	}

	/**
	 * @param iAccount the iAccount to set
	 */
	public void setiAccount(String iAccount) {
		this.iAccount = iAccount;
	}

	/**
	 * @return the eQuantity
	 */
	public long geteQuantity() {
		return eQuantity;
	}

	/**
	 * @param eQuantity
	 *            the eQuantity to set
	 */
	public void seteQuantity(long eQuantity) {
		this.eQuantity = eQuantity;
	}

	/**
	 * @return the iQuantity
	 */
	public long getiQuantity() {
		return iQuantity;
	}

	/**
	 * @param iQuantity
	 *            the iQuantity to set
	 */
	public void setiQuantity(long iQuantity) {
		this.iQuantity = iQuantity;
	}

	/**
	 * @return the eNetVolume
	 */
	public long geteNetVolume() {
		return eNetVolume;
	}

	/**
	 * @param eNetVolume
	 *            the eNetVolume to set
	 */
	public void seteNetVolume(long eNetVolume) {
		this.eNetVolume = eNetVolume;
	}

	/**
	 * @return the iNetVolume
	 */
	public long getiNetVolume() {
		return iNetVolume;
	}

	/**
	 * @param iNetVolume
	 *            the iNetVolume to set
	 */
	public void setiNetVolume(long iNetVolume) {
		this.iNetVolume = iNetVolume;
	}

	@Override
	public String toString() {

		return "\n" + instrument + "," + eAccount + ",E," + eQuantity + ", " + eNetVolume + "\n" + instrument + ","
				+ iAccount + ",I," + iQuantity + "," + iNetVolume;

	}

}
