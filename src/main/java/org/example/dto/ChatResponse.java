package org.example.dto;

import java.util.List;

public class ChatResponse {
    private String reply;
    private List<FlowerDTO> flowers;

    public ChatResponse(String reply, List<FlowerDTO> flowers) {
        this.reply = reply;
        this.flowers = flowers;
    }

    public String getReply() {
        return reply;
    }

    public List<FlowerDTO> getFlowers() {
        return flowers;
    }
}
