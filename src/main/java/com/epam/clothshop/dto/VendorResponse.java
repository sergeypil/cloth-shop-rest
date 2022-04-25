package com.epam.clothshop.dto;

import com.epam.clothshop.entity.Product;

import javax.persistence.OneToMany;
import java.util.List;

public class VendorResponse {
    private long id;
    private String name;
    private List<Product> products;
}
