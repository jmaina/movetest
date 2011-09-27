/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openxdata.modules.moveit.server.model;

import java.io.Serializable;

/**
 *
 * @author jmaina
 */
public class Location implements Serializable
{
    
    private int locationId;
    
    
    private String locationTextName;
    
    
    private String locationCodedName;
    
    

    public void setLocationCodedName(String locationCodedName) {
        this.locationCodedName = locationCodedName;
    }
    
     public String getLocationCodedName() {
        return locationCodedName;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }
    
    public int getLocationId() {
        return locationId;
    }

    public void setLocationTextName(String locationTextName) {
        this.locationTextName = locationTextName;
    }

    public String getLocationTextName() {
        return locationTextName;
    }
    
    
    
    
    
    
    
    
}
