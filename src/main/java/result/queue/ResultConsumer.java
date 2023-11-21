package result.queue;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import result.apis.dto.MtcInsertRequest;
import result.apis.dto.MtcResultRequest;

@Component
@RequiredArgsConstructor
public class ResultConsumer {
    private static final Logger log = LoggerFactory.getLogger(ResultConsumer.class);
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final WebClient webClient;
    @KafkaListener(topics = "mtc.ncr.result", groupId = "mtcResult")
    public void consumeMessage(@Payload MtcResultRequest resReqInfo ,
                               @Header(name = KafkaHeaders.RECEIVED_KEY , required = false) String key ,
                               @Header(KafkaHeaders.RECEIVED_TOPIC ) String topic ,
                               @Header(KafkaHeaders.RECEIVED_TIMESTAMP) long timestamp ,
                               @Header(KafkaHeaders.OFFSET) long offset
    ) {
        log.info("$$$$$$$$$$$result 큐 읽는다 : {}" , resReqInfo.toString());
        /*
        * 거래가 성공이면 바로 거래 큐에 쌓는다
        * 거래가 실패면 보상 트랜잭션 큐에 넣고 거래내역 큐를 쌓는다.
        * */
        MtcInsertRequest insertRequest = new MtcInsertRequest();
        insertRequest.setGid("resReqInfo.getGid()");
        insertRequest.setAcno(resReqInfo.getAcno());
        insertRequest.setTrxdt(resReqInfo.getTrxdt());
        insertRequest.setCurC(resReqInfo.getCurC());
        insertRequest.setAprvSno(resReqInfo.getAprvSno());
        insertRequest.setNujkJan(resReqInfo.getNujkJan());
        insertRequest.setErrMsg(resReqInfo.getErrMsg());
        insertRequest.setTrxAmt(resReqInfo.getTrxAmt());
        insertRequest.setUpmuG(resReqInfo.getUpmuG());
        insertRequest.setTrxPlace(resReqInfo.getPayinfo().getTrxPlace());

        kafkaTemplate.send("mtc.dbs.insertChlGojeong", "" , insertRequest);
    }
}
