package com.intuit.bidding.service;

import com.intuit.bidding.exception.ErrorMessages;
import com.intuit.bidding.exception.VendorNotFoundException;
import com.intuit.bidding.model.Vendor;
import com.intuit.bidding.repository.VendorRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class VendorService {

    private final VendorRepository vendorRepository;

    public VendorService(VendorRepository vendorRepository) {
        this.vendorRepository = vendorRepository;
    }

    public Vendor createVendor(Vendor vendor) {
        return vendorRepository.save(vendor);
    }

    public Vendor findById(long id) throws Exception {
        Optional<Vendor> vendor = vendorRepository.findById(id);

        if (!vendor.isPresent()) throw new VendorNotFoundException(ErrorMessages.VENDOR_NOT_FOUND);
        return vendor.get();
    }
}
