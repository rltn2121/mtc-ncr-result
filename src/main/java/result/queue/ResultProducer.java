package result.queue;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import result.apis.dto.MtcResultRequest;

@Component
@RequiredArgsConstructor
public class ResultProducer {
    private static final Logger log = LoggerFactory.getLogger(MtcResultRequest.class);
    private final KafkaTemplate<String, Object> kafkaTemplate;
    public void produceMessage(MtcResultRequest mtcResultRequest)
    {
        log.info("result request info : {} " , mtcResultRequest.toString());
        //topic , key , value
        kafkaTemplate.send("mtc.ncr.request" , mtcResultRequest.getKey() , mtcResultRequest);
    }
}


