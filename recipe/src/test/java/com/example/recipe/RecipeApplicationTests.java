package com.example.recipe;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;


import org.apache.http.HttpEntity;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Assert;



class RecipeApplicationTests {

	@Test
	public void testGetRecipes() throws Exception {
		HttpClient client = HttpClientBuilder.create().build();
		HttpGet request = new HttpGet("http://localhost:8125/recipes");
		org.apache.http.HttpResponse response = client.execute(request);
		HttpEntity entity = response.getEntity();
		String content = EntityUtils.toString(entity);

		JSONObject jsonObj = new JSONObject(content);
		Assert.assertNotNull(jsonObj.getJSONArray("recipeNames"));
		Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
	}

	@Test
	public void testDetails() throws Exception {
		HttpClient client = HttpClientBuilder.create().build();
		HttpGet request = new HttpGet("http://localhost:8125/recipes");
		org.apache.http.HttpResponse response = client.execute(request);
		HttpEntity entity = response.getEntity();
		String content = EntityUtils.toString(entity);

		JSONObject jsonResultObject = new JSONObject(content);
		String recipeName = jsonResultObject.getJSONArray("recipeNames").get(0).toString();

		HttpClient client2 = HttpClientBuilder.create().build();
		HttpGet request2 = new HttpGet("http://localhost:8125/recipes/details/" + recipeName);
		org.apache.http.HttpResponse response2 = client2.execute(request2);
		HttpEntity entity2 = response2.getEntity();
		String content2 = EntityUtils.toString(entity2);

		JSONObject jsonObj2 = new JSONObject(content2);
		Assert.assertNotNull(jsonObj2.getJSONObject("details"));
		Assert.assertEquals(response2.getStatusLine().getStatusCode(), 200);
	}
	
}
