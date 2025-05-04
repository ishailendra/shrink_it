package dev.techsphere.shrinkit.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UrlAiService {

    private final Logger log = LoggerFactory.getLogger(this.getClass());



    @Autowired
    private OpenAiChatModel chatModel;

    public String detectMaliciousUrl(String url) {
        log.info("Checking whether the URL: {} is malicious or not?", url);

        String prompt = String.format("""
                                        Respond only with the following format:
                                    
                                        Site Safety: ✅ Safe or ❌ Malicious
                                        Site Info: <One-line description of the website>
                                    
                                        Is the following URL safe or malicious?
                                        URL: %s
                                       """, url);

        ChatResponse response = chatModel.call(createPrompt(prompt));

        return response.getResult().getOutput().getText();
    }

    public String categorizeUrl(String url) {
        log.info("Checking the category of the URL: {}", url);
        String prompt = String.format("""
                         Respond only with the following format:
                         Category Tags: <Tags like Search Engine, Social Media and any other>
                         Site Info: <One-line description of the website>
                        
                         What is the category of the following URL?
                         URL: %s
                        """, url);

        ChatResponse response = chatModel.call(createPrompt(prompt));

        return response.getResult().getOutput().getText();
    }

    private Prompt createPrompt(String prompt) {
        return new Prompt(
                        prompt,
                        ChatOptions.builder()
                                .temperature(0.4)
                                .model("gpt-4o")
                                .build());
    }
}
