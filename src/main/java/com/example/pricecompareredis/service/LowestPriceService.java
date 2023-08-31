package com.example.pricecompareredis.service;

import java.util.Set;

import com.example.pricecompareredis.vo.Keyword;
import com.example.pricecompareredis.vo.Product;
import com.example.pricecompareredis.vo.ProductGroup;

public interface LowestPriceService {
	Set getZsetValue(String key);
	int setNewProduct(Product newProduct);
	int setNewProductGroup(ProductGroup productGroup);
	int setNewProductGroupToKeyword(String keyword, String prodGrpId, double score);
	Keyword getLowestPriceProductByKeyword(String keyword);
}
