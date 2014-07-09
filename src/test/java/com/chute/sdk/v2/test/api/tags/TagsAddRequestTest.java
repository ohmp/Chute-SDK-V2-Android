package com.chute.sdk.v2.test.api.tags;

import junit.framework.TestCase;

import com.chute.sdk.v2.utils.JsonUtil;
import com.chute.sdk.v2.utils.TestUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.json.JSONException;
import org.skyscreamer.jsonassert.JSONAssert;

public class TagsAddRequestTest extends TestCase {

	public void testObjectSerializeToPostBodyString() throws JSONException {
		String[] tags = new String[] { "chute", "office", "HQ" };
		String result = null;
		try {
			result = JsonUtil.getMapper().writer().withRootName("tags")
					.writeValueAsString(tags);
			System.out.println(result);
		} catch (JsonProcessingException e) {
			fail("Serialization threw an exception: " + e.getMessage());
		}
		String expected = TestUtil.readResourceAsString("request/TagsAdd.json");
    JSONAssert.assertEquals(expected, result, false);
	}
}
