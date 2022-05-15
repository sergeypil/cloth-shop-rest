package com.epam.clothshop.service.impl;

import com.epam.clothshop.entity.Vendor;
import com.epam.clothshop.exception.ResourceNotFoundException;
import com.epam.clothshop.repository.VendorRepository;
import com.epam.clothshop.service.VendorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VendorServiceImpl implements VendorService {
    private final VendorRepository vendorRepository;

    @Autowired
    public VendorServiceImpl(VendorRepository vendorRepository) {
        this.vendorRepository = vendorRepository;
    }

    @Override
    public List<Vendor> getAllVendors() {
        return vendorRepository.findAll();
    }

    @Override
    public void saveVendor(Vendor vendor) {
        vendorRepository.save(vendor);
    }

    @Override
    public Vendor getVendorById(long id) {
        return vendorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found vendor with id " + id));
    }

    @Override
    public void update(Vendor vendor, long id) {
        vendor.setId(id);
        vendorRepository.save(vendor);
    }

    @Override
    public void deleteVendor(Vendor vendor) {
        vendorRepository.delete(vendor);
    }
}
