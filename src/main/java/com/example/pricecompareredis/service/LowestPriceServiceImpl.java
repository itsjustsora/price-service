package com.example.pricecompareredis.service;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.example.pricecompareredis.vo.Product;
import com.example.pricecompareredis.vo.ProductGroup;

@Service
public class LowestPriceServiceImpl implements LowestPriceService{

	@Autowired
	private RedisTemplate<String, String> redisTemplate;

	public Set getZsetValue(String key) {
		return redisTemplate.opsForZSet().rangeWithScores(key, 0, 9);
	}

	@Override
	public int setNewProduct(Product product) {
		redisTemplate.opsForZSet().add(product.getProdGrpId(), product.getProductId(), product.getPrice());

		return redisTemplate
				.opsForZSet()
				.rank(product.getProdGrpId(), product.getProductId())
				.intValue();
	}

	@Override
	public int setNewProductGroup(ProductGroup productGroup) {
		Product product = productGroup.getProductList().get(0);
		redisTemplate.opsForZSet().add(productGroup.getProdGrpId(), product.getProductId(), product.getPrice());

		return redisTemplate.opsForZSet()
			.zCard(productGroup.getProdGrpId())
			.intValue();
	}

	@Override
	public int setNewProductGroupToKeyword(String keyword, String prodGrpId, double score) {
		myProdPriceRedis.opsForZSet().add(keyword, prodGrpId, score);
		return myProdPriceRedis.opsForZSet().rank(keyword, prodGrpId).intValue();
	}
}
