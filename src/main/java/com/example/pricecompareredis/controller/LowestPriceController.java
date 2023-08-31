package com.example.pricecompareredis.controller;

import java.util.Set;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.pricecompareredis.service.LowestPriceService;
import com.example.pricecompareredis.vo.Keyword;
import com.example.pricecompareredis.vo.Product;
import com.example.pricecompareredis.vo.ProductGroup;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class LowestPriceController {

	private final LowestPriceService lowestPriceService;

	@GetMapping("/product/{key}")
	public Set GetZsetValue(@PathVariable String key) {
		return lowestPriceService.getZsetValue(key);
	}

	@PutMapping("/product")
	public int SetNewProduct(@RequestBody Product newProduct) {
		return lowestPriceService.setNewProduct(newProduct);
	}

	@PutMapping("/productGroup")
	public int SetNewProductGroup(@RequestBody ProductGroup productGroup) {
		return lowestPriceService.setNewProductGroup(productGroup);
	}

	@PutMapping("/productGroupToKeyword")
	public int SetNewProductGroupToKeyword (String keyword, String prodGrpId, double score) {
		return lowestPriceService.setNewProductGroupToKeyword(keyword, prodGrpId, score);
	}

	@GetMapping("/productPrice/lowest")
	public Keyword GetLowestPriceProductByKeyword (String keyword) {
		return lowestPriceService.getLowestPriceProductByKeyword(keyword);
	}
}
