/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.util.Date;

/**
 * DTO for availabilities
 *
 * @author Oscar
 */
public class AvailabilityDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    private Date fromDate;
    private Date toDate;

    /**
     * Constructor
     */
    public AvailabilityDTO() {
    }

    /**
     * Class Constructor
     *
     * @param fromDate sets the fromDate property
     * @param toDate sets the toDAte property
     */
    public AvailabilityDTO(Date fromDate, Date toDate) {
        this.fromDate = new Date(fromDate.getTime());
        this.toDate = new Date(toDate.getTime());
    }

    /**
     * Gets the value of the fromDate property
     *
     * @return fromDate as Date object
     */
    public Date getFromDate() {
        return fromDate != null ? new Date(fromDate.getTime()) : null;
    }

    /**
     * Sets the fromDate property
     *
     * @param fromDate the fromDate to set
     */
    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate != null ? new Date(fromDate.getTime()) : null;
    }

    /**
     * Gets the value of the toDate property
     *
     * @return toDate as Date object
     */
    public Date getToDate() {
        return toDate != null ? new Date(toDate.getTime()) : null;
    }

    /**
     * Sets the toDate property
     *
     * @param toDate the toDate to set
     */
    public void setToDate(Date toDate) {
        this.toDate = toDate != null ? new Date(toDate.getTime()) : null;
    }

    /**
     * @return returns Object of this class as string
     */
    @Override
    public String toString() {
        return "AvailabilityDTO{" + "fromDate=" + fromDate + ", toDate=" + toDate + '}';
    }

}
