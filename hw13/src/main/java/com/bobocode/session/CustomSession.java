package com.bobocode.session;

import lombok.Data;
import lombok.experimental.Accessors;
import lombok.experimental.Delegate;

import java.util.HashMap;
import java.util.Map;

@Data
@Accessors(chain = true)
public class CustomSession {

    @Delegate
    private Map<String, Object> attributes = new HashMap<>();
    private String id;
    private long creationTimestamp;
    private long ttl;
}
