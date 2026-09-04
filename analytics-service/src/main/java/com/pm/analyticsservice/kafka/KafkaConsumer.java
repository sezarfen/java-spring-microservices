package com.pm.analyticsservice.kafka;

import com.google.protobuf.InvalidProtocolBufferException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import patient.events.PatientEvent;

@Service
public class KafkaConsumer {

    private static final Logger log = LoggerFactory.getLogger(KafkaConsumer.class);

    // groupId consumer'ın kim olduğunu söylüyor, biraz daha advanced bir topic şu an için.
    @KafkaListener(topics = "patient", groupId = "analytics-service")
    public void consumeEvent(byte[] event) {
        log.info("BURAYA GELDİ");
        try {
            PatientEvent patientEvent = PatientEvent.parseFrom(event);
            // .. analitik servis için bir business kodu yazabiliriz buraya
            log.info("Received Patient Event: [PatientId={}, PatientName={}, PatientEmail={}]",
                    patientEvent.getPatientId(), patientEvent.getName(), patientEvent.getEmail());
        } catch (InvalidProtocolBufferException e) {

            log.error("Error Deserializing event {}", e.getMessage());
            // Exception fırlatıp analitik servisi durdurmadan loglayalım
        }
    }
}
