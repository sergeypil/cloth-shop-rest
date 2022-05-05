package com.epam.clothshop.controller;

import com.epam.clothshop.dto.*;
import com.epam.clothshop.entity.Vendor;
import com.epam.clothshop.mapper.ProductMapper;
import com.epam.clothshop.mapper.VendorMapper;
import com.epam.clothshop.service.VendorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/vendors")
public class VendorController {
    private VendorService vendorService;
    private VendorMapper vendorMapper;
    private ProductMapper productMapper;

    @Autowired
    public VendorController(VendorService vendorService, VendorMapper vendorMapper, ProductMapper productMapper) {
        this.vendorService = vendorService;
        this.vendorMapper = vendorMapper;
        this.productMapper = productMapper;
    }

    @GetMapping
    public ResponseEntity<List<VendorResponse>> getAllVendors() {
        List<VendorResponse> vendorResponses = vendorService.getAllVendors().stream()
                .map(v -> vendorMapper.mapVendorToVendorResponse(v)).collect(Collectors.toList());
        return new ResponseEntity<>(vendorResponses, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Void> createVendor(@RequestBody VendorRequest vendorRequest) {
        Vendor vendor = vendorMapper.mapVendorRequestToVendor(vendorRequest);
        vendorService.saveVendor(vendor);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VendorResponse> getVendorById(@PathVariable long id) {
        Vendor vendor = vendorService.getVendorById(id);
        return new ResponseEntity<>(vendorMapper.mapVendorToVendorResponse(vendor), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateVendor(@PathVariable long id, @RequestBody VendorRequest vendorRequest) {
        Vendor vendor = vendorMapper.mapVendorRequestToVendor(vendorRequest);
        vendorService.update(vendor, id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVendorById(@PathVariable long id) {
        Vendor vendor = vendorService.getVendorById(id);
        vendorService.deleteVendor(vendor);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{id}/products")
    public ResponseEntity<List<ProductResponse>> getProductsByVendor(@PathVariable long id) {
        Vendor vendor = vendorService.getVendorById(id);
        List<ProductResponse> productResponses = vendor.getProducts().stream()
                .map(p -> productMapper.mapProductToProductResponse(p)).collect(Collectors.toList());
        return new ResponseEntity<>(productResponses, HttpStatus.OK);
    }
}
