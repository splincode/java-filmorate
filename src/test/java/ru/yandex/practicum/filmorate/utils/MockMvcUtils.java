package ru.yandex.practicum.filmorate.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

public class MockMvcUtils {
    private static ObjectMapper objectMapper;
    private static MockMvc mockMvc;

    public static void setMockMvc(MockMvc mockMvc) {
        MockMvcUtils.mockMvc = mockMvc;
    }

    public static void setObjectMapper(ObjectMapper objectMapper) {
        MockMvcUtils.objectMapper = objectMapper;
    }

    public static MvcResult get(String urlTemplate) {
        try {
            return mockMvc.perform(
                            MockMvcRequestBuilders.get(urlTemplate)
                                    .accept("*/*")
                                    .contentType(MediaType.valueOf("application/json")))
                    .andReturn();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static MvcResult put(String urlTemplate) {
        return put(urlTemplate, null);
    }

    public static MvcResult put(String urlTemplate, Object model) {
        try {
            return mockMvc.perform(
                            MockMvcRequestBuilders.put(urlTemplate)
                                    .content(objectMapper.writeValueAsString(model))
                                    .accept("*/*")
                                    .contentType(MediaType.valueOf("application/json")))
                    .andReturn();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static MvcResult delete(String urlTemplate) {
        return delete(urlTemplate, null);
    }

    public static MvcResult delete(String urlTemplate, Object model) {
        try {
            return mockMvc.perform(
                            MockMvcRequestBuilders.delete(urlTemplate)
                                    .content(objectMapper.writeValueAsString(model))
                                    .accept("*/*")
                                    .contentType(MediaType.valueOf("application/json")))
                    .andReturn();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static MvcResult post(String urlTemplate, Object model) {
        try {
            return mockMvc.perform(
                    MockMvcRequestBuilders.post(urlTemplate)
                            .content(objectMapper.writeValueAsString(model))
                            .accept("*/*")
                            .contentType(MediaType.valueOf("application/json"))).andReturn();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static JsonObject jsonObject(MockHttpServletResponse response) {
        try {
            JsonElement jsonElement = JsonParser.parseString(response.getContentAsString());
            return jsonElement.getAsJsonObject();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static JsonArray jsonArray(MockHttpServletResponse response) {
        try {
            JsonElement jsonElement = JsonParser.parseString(response.getContentAsString());
            return jsonElement.getAsJsonArray();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
