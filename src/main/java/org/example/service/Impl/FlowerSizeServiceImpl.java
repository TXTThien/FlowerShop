//package org.example.service.Impl;
//
//import lombok.RequiredArgsConstructor;
//import org.example.entity.FlowerSize;
//import org.example.repository.FlowerRepository;
//import org.example.repository.FlowerSizeRepository;
//import org.example.service.IFlowerSizeService;
//import org.springframework.stereotype.Service;
//
//@Service
//@RequiredArgsConstructor
//public class FlowerSizeServiceImpl implements IFlowerSizeService {
//    private final FlowerSizeRepository flowerSizeRepository;
//    private final FlowerRepository flowerRepository;
//
//    @Override
//    public FlowerSize updateProductSize(int id, FlowerSize productSize) {
//        FlowerSize existingProductSize = flowerSizeRepository.findById(id).orElse(null);
//        if (existingProductSize != null) {
//            existingProductSize.setStock(productSize.getStock());
//            existingProductSize.setStatus(productSize.getStatus());
//
//            if (productSize.getProductID() != null && productSize.getProductID().getProductID() != null) {
//                Product product = productRepository.findById(productSize.getProductID().getProductID()).orElse(null);
//                if (product != null) {
//                    existingProductSize.setProductID(product);
//                } else {
//                    throw new EntityNotFoundException("Product not found with ID: " + productSize.getProductID().getProductID());
//                }
//            }
//
//            if (productSize.getSizeID() != null && productSize.getSizeID().getSizeID() != null) {
//                Size size = sizeRepository.findById(productSize.getSizeID().getSizeID()).orElse(null);
//                if (size != null) {
//                    existingProductSize.setSizeID(size);
//                } else {
//                    throw new EntityNotFoundException("Size not found with ID: " + productSize.getSizeID().getSizeID());
//                }
//            }
//
//            return productSizeRepository.save(existingProductSize);
//        }
//        return null;
//    }
//
//
//    @Override
//    public void deleteProductSize(int id) {
//        ProductSize productSize = productSizeRepository.findById(id).orElse(null);
//        assert productSize != null;
//        productSize.setStatus(Status.Disable);
//        productSizeRepository.save(productSize);
//    }
//
//    @Override
//    public List<ProductSize> findProductSizeByProductID(int id) {
//        return productSizeRepository.findProductSizesByProductIDProductIDAndStatus(id,Status.Enable);
//    }
//
//    @Override
//    public ProductSize findProductSizeByProductIDAndSize(Integer id, String size) {
//        return productSizeRepository.findProductSizeByProductID_ProductIDAndAndSizeID_SizeNameAndStatus(id, size, Status.Enable);
//    }
//
//    @Override
//    public void updateStock(int pz, int number) {
//        productSizeRepository.UpdateStock(pz,number);
//    }
//
//    @Override
//    public ProductSize findProductSizeByID(int productSizeID) {
//        return productSizeRepository.findProductSizeByProductSizeIDAndStatus(productSizeID,Status.Enable);
//    }
//}
