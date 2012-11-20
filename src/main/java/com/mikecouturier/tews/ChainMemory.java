package com.mikecouturier.tews;

import java.util.ArrayList;
import java.util.List;

public class ChainMemory {
    private List<ChainLink> chainLinks = new ArrayList<ChainLink>();

    public List<ChainLink> getList() {
        return new ArrayList<ChainLink>(chainLinks);
    }

    public ChainLink advanceChain(String path) {
        ChainLink link = new ChainLink(this, path);
        chainLinks.add(link);
        return link;
    }

    public ChainLink getCurrentLink() {
        return chainLinks.get(chainLinks.size() - 1);
    }
}
