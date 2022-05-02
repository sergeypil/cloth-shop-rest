package com.epam.clothshop.mapper;

import com.epam.clothshop.dto.ProductRequest;
import com.epam.clothshop.dto.ProductResponse;
import com.epam.clothshop.dto.VendorRequest;
import com.epam.clothshop.dto.VendorResponse;
import com.epam.clothshop.entity.Vendor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class VendorMapper {
    ProductMapper productMapper;

    @Autowired
    public VendorMapper(ProductMapper productMapper) {
        this.productMapper = productMapper;
    }

    public VendorResponse mapVendorToVendorResponse(Vendor vendor) {
        var vendorResponse = new VendorResponse();
        vendorResponse.setId(vendor.getId());
        vendorResponse.setName(vendor.getName());
        List<ProductResponse> productResponses = vendor.getProducts().stream()
                .map(productMapper::mapProductToProductResponse)
                .collect(Collectors.toList());
        vendorResponse.setProductResponses(productResponses);
        return vendorResponse;
    }

    public Vendor mapVendorRequestToVendor(VendorRequest vendorRequest) {
        var vendor = new Vendor();
        vendor.setName(vendorRequest.getName());
        return vendor;
    }
}
