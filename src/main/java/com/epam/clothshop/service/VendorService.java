package com.epam.clothshop.service;

import com.epam.clothshop.entity.Vendor;

import java.util.List;

public interface VendorService {
    List<Vendor> getAllVendors();

    void saveVendor(Vendor vendor);

    Vendor getVendorById(long id);

    void update(Vendor vendor, long id);

    void deleteVendor(Vendor vendor);
}
