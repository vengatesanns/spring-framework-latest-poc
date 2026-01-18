package com.vengatx.springai.custom.controller;

import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/custom-embeddings")
public class CustomEmbeddingController {

    private final EmbeddingModel embeddingModel;

    public CustomEmbeddingController(EmbeddingModel embeddingModel) {
        this.embeddingModel = embeddingModel;
    }

    @GetMapping("/embed")
    public float[] embed(@RequestParam String input) {
        return embeddingModel.embed(new Document(input));
    }
}