package com.vengatx.springai.custom.model;

import com.vengatx.springai.custom.config.CustomChatClientAPI;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.Embedding;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.embedding.EmbeddingRequest;
import org.springframework.ai.embedding.EmbeddingResponse;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class CustomEmbeddingModel implements EmbeddingModel {

    private final CustomChatClientAPI customChatClientAPI;

    public CustomEmbeddingModel(CustomChatClientAPI customChatClientAPI) {
        this.customChatClientAPI = customChatClientAPI;
    }

    /**
     * Primary Spring AI entry point
     */
    @Override
    public EmbeddingResponse call(EmbeddingRequest request) {
        List<Embedding> embeddings = new ArrayList<>();
        int index = 0;
        for (String input : request.getInstructions()) {
            List<Double> vector = customChatClientAPI.invokeEmbeddingAPI(input);
            embeddings.add(new Embedding(toFloatArray(vector), index++));
        }
        return new EmbeddingResponse(embeddings);
    }

    /**
     * Convenience method used by VectorStores
     */
    @Override
    public float[] embed(Document document) {
        List<Double> vector = customChatClientAPI.invokeEmbeddingAPI(document.getText());
        return toFloatArray(vector);
    }

    private float[] toFloatArray(List<Double> values) {
        float[] array = new float[values.size()];
        for (int i = 0; i < values.size(); i++) {
            array[i] = values.get(i).floatValue();
        }
        return array;
    }
}
