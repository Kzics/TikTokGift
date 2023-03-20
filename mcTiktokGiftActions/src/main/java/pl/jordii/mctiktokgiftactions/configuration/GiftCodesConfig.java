// 
// Decompiled by Procyon v0.5.36
// 

package pl.jordii.mctiktokgiftactions.configuration;

import java.io.IOException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

public class GiftCodesConfig
{
    private Map<String, Integer> codeMap;
    
    public void loadCodesFromJson(final String jsonFile) throws IOException {
        final byte[] jsonData = Files.readAllBytes(Paths.get(jsonFile, new String[0]));
        final ObjectMapper objectMapper = new ObjectMapper();
        this.codeMap = (Map<String, Integer>)objectMapper.readValue(jsonData, (Class)Map.class);
    }
    
    public Integer getNumberFromCode(final String code) {
        if (this.codeMap.containsKey(code)) {
            return this.codeMap.get(code);
        }
        return -1;
    }
}
