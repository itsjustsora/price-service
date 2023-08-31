package com.example.pricecompareredis.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.example.pricecompareredis.vo.Keyword;
import com.example.pricecompareredis.vo.Product;
import com.example.pricecompareredis.vo.ProductGroup;
import com.fasterxml.jackson.databind.ObjectMapper;

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
		redisTemplate.opsForZSet().add(keyword, prodGrpId, score);
		return redisTemplate.opsForZSet().rank(keyword, prodGrpId).intValue();
	}

	@Override
	public Keyword getLowestPriceProductByKeyword(String keyword) {
		// keyword 를 통해 ProductGroup 가져오기 (10개)
		List<ProductGroup> productGroups = getProdGrpUsingKeyword(keyword);

		Keyword returnInfo = new Keyword();
		// 가져온 정보들을 Return 할 Object 에 넣기
		returnInfo.setKeyword(keyword);
		returnInfo.setProductGrpList(productGroups);
		return returnInfo;
	}

	public List<ProductGroup> getProdGrpUsingKeyword(String keyword) {

		List<ProductGroup> returnInfo = new ArrayList<>();

		// 내림차순, hit high
		List<String> prodGrpIdList = List.copyOf(
			Objects.requireNonNull(redisTemplate.opsForZSet().reverseRange(keyword, 0, 9)));

		List<Product> products = new ArrayList<>();

		//10개 prodGrpId로 loop
		for (final String prodGrpId : prodGrpIdList) {
			// Loop 타면서 ProductGrpID 로 Product:price 가져오기 (10개)

			ProductGroup tempProdGrp = new ProductGroup();

			Set prodAndPriceList = redisTemplate.opsForZSet().rangeWithScores(prodGrpId, 0, 9);

			Iterator<Object> prodPriceObj = prodAndPriceList.iterator();

			// loop 타면서 product obj에 bind (10개)
			while (prodPriceObj.hasNext()) {
				ObjectMapper objMapper = new ObjectMapper();
				// {"value":00-10111-}, {"score":11000}
				Map<String, Object> prodPriceMap = objMapper.convertValue(prodPriceObj.next(), Map.class);

				// Product Obj bind
				Product product = new Product();
				product.setProductId(prodPriceMap.get("value").toString()); // prod_id
				product.setPrice(Double.valueOf(prodPriceMap.get("score").toString()).intValue()); //es 검색된 score
				product.setProdGrpId(prodGrpId);

				products.add(product);
			}
			// 10개 product price 입력완료
			tempProdGrp.setProdGrpId(prodGrpId);
			tempProdGrp.setProductList(products);
			returnInfo.add(tempProdGrp);
		}

		return returnInfo;
	}
}
