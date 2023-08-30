package com.example.pricecompareredis.vo;

import java.util.List;

import lombok.Data;

@Data
public class ProductGroup {
	private String prodGrpId;
	private List<Product> productList;
}
