package com.vk.purchasetime.models;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int profileId;
    private String profileName;
    private String address;
    private String profileType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User user;

    @OneToMany(mappedBy = "profile",fetch = FetchType.LAZY)
    private List<InvoicePrimary> invoicePrimaryList=new ArrayList<>();



    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public Profile() {
    }

    public Profile(String profileName, String address, ProfileType profileType) {
        this.profileName = profileName;
        this.address = address;
        this.profileType = profileType.toString();
    }

    public void setProfileId(int profileId) {
        this.profileId = profileId;
    }

    public int getProfileId() {
        return profileId;
    }

    public String getProfileName() {
        return profileName;
    }

    public void setProfileName(String profileName) {
        this.profileName = profileName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public ProfileType getProfileType() {
        return ProfileType.valueOf(profileType);
    }

    public void setProfileType(ProfileType profileType) {
        this.profileType = profileType.toString();
    }

    public List<InvoicePrimary> getInvoicePrimaryList() {
        return invoicePrimaryList;
    }

    public void setInvoicePrimaryList(List<InvoicePrimary> invoicePrimaryList) {
        this.invoicePrimaryList = invoicePrimaryList;
    }

    public void setProfileType(String profileType) {
        this.profileType = profileType;
    }

    @Override
    public String toString() {
        return "Profile{" +
                "profileId=" + profileId +
                ", profileName='" + profileName + '\'' +
                ", address='" + address + '\'' +
                ", profileType='" + profileType + '\'' +
                '}';
    }
}
