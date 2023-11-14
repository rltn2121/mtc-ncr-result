package result.converters;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.SerializationException;
import result.apis.dto.MtcResultRequest;
import org.apache.kafka.common.serialization.Deserializer;

public class MessageDeserializer implements Deserializer<MtcResultRequest> {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public MtcResultRequest deserialize(String s, byte[] bytes) {
        try {
            return objectMapper.readValue(new String(bytes), MtcResultRequest.class);
        } catch (JsonProcessingException e) {
            System.out.println(e.getOriginalMessage());
            throw new SerializationException(e);
        }
    }
}
